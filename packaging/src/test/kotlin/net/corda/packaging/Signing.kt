@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

package net.corda.packaging

import jdk.security.jarsigner.JarSigner
import sun.security.x509.AlgorithmId
import sun.security.x509.CertificateAlgorithmId
import sun.security.x509.CertificateExtensions
import sun.security.x509.CertificateSerialNumber
import sun.security.x509.CertificateValidity
import sun.security.x509.CertificateVersion
import sun.security.x509.CertificateX509Key
import sun.security.x509.KeyUsageExtension
import sun.security.x509.X500Name
import sun.security.x509.X509CertImpl
import sun.security.x509.X509CertInfo
import java.nio.file.Files
import java.nio.file.Path
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.time.Duration
import java.time.Instant
import java.util.Date
import java.util.zip.ZipFile
import javax.security.auth.x500.X500Principal

object Signing {
    private val certFactory = CertificateFactory.getInstance("X.509")

    @Suppress("LongParameterList")
    fun createCertificate(subject : X500Principal,
                          subjectKey : PublicKey,
                          issuer : X500Principal,
                          issuerKey : PrivateKey,
                          duration : Duration = Duration.ofDays(365),
                          signAlgo : String = "SHA512withECDSA") : X509Certificate {
        val info = X509CertInfo().also {
            val from = Date.from(Instant.now())
            val to = Date.from(from.toInstant().plus(duration))
            it[X509CertInfo.VALIDITY] = CertificateValidity(from, to)
            it[X509CertInfo.SERIAL_NUMBER] = CertificateSerialNumber(1)
            it[X509CertInfo.SUBJECT] = X500Name.asX500Name(subject)
            it[X509CertInfo.ISSUER] = X500Name.asX500Name(issuer)
            it[X509CertInfo.KEY] = CertificateX509Key(subjectKey)
            it[X509CertInfo.VERSION] = CertificateVersion(CertificateVersion.V3)
            it[X509CertInfo.ALGORITHM_ID] = CertificateAlgorithmId(AlgorithmId(AlgorithmId.sha512WithECDSA_oid))
            val keyUsage = KeyUsageExtension().apply {
                set(KeyUsageExtension.DIGITAL_SIGNATURE, true)
                set(KeyUsageExtension.KEY_CERTSIGN, true)
                set(KeyUsageExtension.CRL_SIGN, true)
            }
            it[X509CertInfo.EXTENSIONS] = CertificateExtensions().apply {
                this.set(KeyUsageExtension.NAME, keyUsage)
            }

        }
        var cert = X509CertImpl(info)
        cert.sign(issuerKey, signAlgo)

        val algo = cert[X509CertImpl.SIG_ALG] as AlgorithmId
        info[CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM] = algo
        cert = X509CertImpl(info)
        cert.sign(issuerKey, signAlgo)
        return cert
    }

    fun signJar(src: Path, dst : Path, key : PrivateKey, signatureFileName : String, vararg certs : Certificate) {
        val jarSignerBuilder = JarSigner.Builder(key, certFactory.generateCertPath(certs.toList()))
            .signerName(signatureFileName)
        Files.newOutputStream(dst).use { outputStream ->
            jarSignerBuilder.build().sign(ZipFile(src.toFile()), outputStream)
        }
    }
}