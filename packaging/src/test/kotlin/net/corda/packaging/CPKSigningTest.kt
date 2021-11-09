package net.corda.packaging

import net.corda.packaging.Signing.createCertificate
import net.corda.packaging.Signing.signJar
import net.corda.packaging.util.ZipTweaker
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.cert.CertPath
import java.security.cert.CertPathValidator
import java.security.cert.CertPathValidatorException
import java.security.cert.CertificateFactory
import java.security.cert.PKIXParameters
import java.security.cert.PKIXRevocationChecker
import java.security.cert.TrustAnchor
import java.security.cert.X509Certificate
import java.util.EnumSet
import javax.security.auth.x500.X500Principal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CPKSigningTest {
    private val secureRandom = SecureRandom()

    private val keyPairGenerator = KeyPairGenerator.getInstance("EC").apply {
        initialize(256, secureRandom)
    }

    private val certPathValidator = CertPathValidator.getInstance("PKIX").apply {
        val rc = revocationChecker as PKIXRevocationChecker
        rc.options = EnumSet.of(
            PKIXRevocationChecker.Option.NO_FALLBACK,
            PKIXRevocationChecker.Option.SOFT_FAIL,
            PKIXRevocationChecker.Option.PREFER_CRLS)
    }

    private val cordaDevCert = CertificateFactory.getInstance("X.509").let { crtFactory ->
        javaClass.getResourceAsStream("/META-INF/security/corda_dev.crt.pem").use(crtFactory::generateCertificate) as X509Certificate
    }

    private lateinit var testDir : Path
    private lateinit var cacheDir : Path
    private lateinit var workflowCPKPath : Path
    private lateinit var doubleSignedWorkflowCPKPath : Path
    private lateinit var unsignedWorkflowCPKPath : Path
    private lateinit var workflowCPKPathUntrustedSignature : Path

    private lateinit var workflowCPK : CPK
    private lateinit var untrustedCertificate : X509Certificate

    @BeforeAll
    fun setup(@TempDir junitTestDir : Path) {
        testDir = junitTestDir
        workflowCPKPath = Path.of(URI(System.getProperty("net.corda.packaging.test.workflow.cpk")))
        cacheDir = testDir.resolve("cache")
        unsignedWorkflowCPKPath = testDir.resolve("unsigned.cpk")
        workflowCPKPathUntrustedSignature = testDir.resolve("untrustedSigned.cpk")
        doubleSignedWorkflowCPKPath = testDir.resolve("doubleSigned.cpk")
        workflowCPK = CPK.from(Files.newInputStream(workflowCPKPath), cacheDir, workflowCPKPath.toString())

        val keyPair = keyPairGenerator.genKeyPair()
        val owner = X500Principal("C=IE, L=Dublin, CN=Signature test")
        untrustedCertificate = createCertificate(owner, keyPair.public, owner, keyPair.private)
        signJar(workflowCPKPath, doubleSignedWorkflowCPKPath, keyPair.private, "ASDF", untrustedCertificate)
        ZipTweaker.removeJarSignature(workflowCPKPath, unsignedWorkflowCPKPath)
        signJar(unsignedWorkflowCPKPath, workflowCPKPathUntrustedSignature, keyPair.private, "ASDF", untrustedCertificate)
    }

    private fun verify(trustedCertificates : Iterable<X509Certificate>, certPaths : Iterable<CertPath>) {
        val params = trustedCertificates
            .asSequence()
            .map { TrustAnchor(it, null) }
            .toSet()
            .let(::PKIXParameters)
        params.isRevocationEnabled = false
        certPaths.forEach { certPath ->
            certPathValidator.validate(certPath, params)
        }
    }

    @Test
    fun `jar signed with trusted CA passes validation`() {
        assertDoesNotThrow {
            verify(listOf(cordaDevCert), workflowCPK.metadata.signers)
        }
    }

    @Test
    fun `jar signed with untrusted CA does not pass validation`() {
        assertThrows<CertPathValidatorException> {
            verify(listOf(cordaDevCert),
                Files.newInputStream(workflowCPKPathUntrustedSignature).use(CPK.Metadata::from).signers)
        }
    }

    @Test
    fun `double signed jar signed with untrusted CA does not pass validation`() {
        assertThrows<CertPathValidatorException> {
            verify(listOf(cordaDevCert),
                Files.newInputStream(doubleSignedWorkflowCPKPath).use(CPK.Metadata::from).signers)
        }
    }
}