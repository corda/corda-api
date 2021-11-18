package net.corda.packaging.signature.demo.command

import net.corda.packaging.CPI
import net.corda.packaging.CPK
import net.corda.packaging.signature.demo.EnvVarConverter
import net.corda.packaging.signature.demo.ExitCodes
import net.corda.packaging.signature.demo.FileContentConverter
import net.corda.packaging.signature.demo.PathConverter
import net.corda.packaging.signature.demo.StandardCommand
import net.corda.packaging.signature.demo.takeNotNull
import picocli.CommandLine
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyStore
import java.security.cert.CertPathValidator
import java.security.cert.PKIXParameters
import java.security.cert.PKIXRevocationChecker
import java.security.cert.TrustAnchor
import java.security.cert.X509Certificate
import java.util.EnumSet

@CommandLine.Command(
    name = "verify",
    description = ["verify a corda package"])
object Verify : StandardCommand() {

    class Options {
        @CommandLine.Option(names = ["-i", "--input-file"], required = true, paramLabel = "PACKAGE_FILE", converter = [PathConverter::class],
            description = ["Path to a corda package file *.{cpk,cpi,cpb}"])
        var inputFile : Path? = null

        @CommandLine.Option(names = ["-k", "--keystore"], paramLabel = "KEYSTORE_FILE", converter = [PathConverter::class],
            description = ["Path to a Java keystore containing the key that will be used to sign the archive"], required = true)
        var keyStore : Path? = null

        @CommandLine.Option(names = ["-p", "--keystore-password"],
            description = ["Passphrase to access the java keystore containing the signing key (if any)"])
        var keyStorePassword : String? = null

        @CommandLine.Option(names = ["-p:env", "--keystore-password:env"], converter = [EnvVarConverter::class],
            description = ["Environmental variable containing the passphrase to access the java keystore containing the signing key (if any)"])
        var keyStorePasswordEnv : String? = null

        @CommandLine.Option(names = ["-p:file", "--keystore-password:file"], converter = [FileContentConverter::class],
            description = ["File containing thr passphrase to access the java keystore containing the signing key (if any)"])
        var keyStorePasswordFile : String? = null
    }

    @CommandLine.Mixin
    var options = Options()

    private val certPathValidator = CertPathValidator.getInstance("PKIX").apply {
        val rc = revocationChecker as PKIXRevocationChecker
        rc.options = EnumSet.of(
            PKIXRevocationChecker.Option.NO_FALLBACK,
            PKIXRevocationChecker.Option.SOFT_FAIL,
            PKIXRevocationChecker.Option.PREFER_CRLS)
    }


    override fun call(): Int {
        val trustedCertificates = options.keyStore.let { keyStorePath ->
            val keyStorePasswordErrorMessage = "One (and one only) between --keystore-password, --keystore-password:file, --keystore-password:env must be specified"
            val keyStorePassword = takeNotNull(
                keyStorePasswordErrorMessage,
                options.keyStorePassword,
                options.keyStorePasswordEnv,
                options.keyStorePasswordFile
            ).toCharArray()
            val keyStore = KeyStore.getInstance(keyStorePath!!.toFile(), keyStorePassword)
            keyStore.aliases().asSequence()
                .filter(keyStore::isCertificateEntry)
                .map(keyStore::getCertificate)
                .toList()
        }

        val params = trustedCertificates
            .asSequence()
            .map { TrustAnchor(it as X509Certificate, null) }
            .toSet()
            .let(::PKIXParameters)
        params.isRevocationEnabled = false

        val fileName = options.inputFile!!.fileName.toString().toLowerCase()
        when {
            fileName.endsWith(".cpk") -> {
                    val meta = CPK.Metadata.from(Files.newInputStream(options.inputFile))
                meta.signers.forEach { certPath ->
                    certPathValidator.validate(certPath, params)
                }
            }
            fileName.endsWith(".cpi") || fileName.endsWith(".cpb") -> {
                val meta = CPI.Metadata.from(Files.newInputStream(options.inputFile))
                meta.signers.forEach { certPath ->
                    certPathValidator.validate(certPath, params)
                }
                meta.cpks.asSequence().flatMap(CPK.Metadata::signers).forEach { certPath ->
                    certPathValidator.validate(certPath, params)
                }
            }
        }
        return ExitCodes.SUCCESS.code
    }
}