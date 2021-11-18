package net.corda.packaging.signature.demo.command

import net.corda.packaging.SigningParameters
import net.corda.packaging.signature.demo.Destination
import net.corda.packaging.signature.demo.DestinationConverter
import net.corda.packaging.signature.demo.EnvVarConverter
import net.corda.packaging.signature.demo.ExitCodes
import net.corda.packaging.signature.demo.FileContentConverter
import net.corda.packaging.signature.demo.PathConverter
import net.corda.packaging.signature.demo.Signing
import net.corda.packaging.signature.demo.Source
import net.corda.packaging.signature.demo.SourceConverter
import net.corda.packaging.signature.demo.StandardCommand
import net.corda.packaging.signature.demo.takeNotNull
import net.corda.packaging.util.ZipTweaker
import picocli.CommandLine
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyStore
import java.security.PrivateKey

@CommandLine.Command(
    name = "sign",
    description = ["Sign a corda package"])
object Sign : StandardCommand() {

    class Options {
        @CommandLine.Option(names = ["-o", "--output-file"], paramLabel = "PATH",
            converter = [DestinationConverter::class], description = ["Path to the generated CorDapp file"])
        var destination : Destination = Destination.Stream(System.out, "stdout")

        @CommandLine.Option(names = ["-i", "--input-file"], required = true, paramLabel = "INPUT_FILE", converter = [SourceConverter::class],
            description = ["Path to the file to be signed"])
        var source : Source = Source.Stream(System.`in`, "stdin")

        @CommandLine.Option(names = ["-k", "--keystore"], paramLabel = "KEYSTORE_FILE", converter = [PathConverter::class],
            description = ["Path to a Java keystore containing the key that will be used to sign the archive"], required = true)
        var keyStore : Path? = null

        @CommandLine.Option(names = ["-s", "--signature-filename"], paramLabel = "NAME",
            converter = [DestinationConverter::class], description = ["Name of the generated .SF file used for jar file signature"])
        var signatureFileName : String = "SIGN"

        @CommandLine.Option(names = ["-a", "--key-alias"], paramLabel = "KEY_ALIAS", required = true,
            description = ["Alias of the keystore entry containing the private key to be used for signing"])
        var keyAlias : String? = null

        @CommandLine.Option(names = ["-p", "--keystore-password"],
            description = ["Passphrase to access the java keystore containing the signing key (if any)"])
        var keyStorePassword : String? = null

        @CommandLine.Option(names = ["-p:env", "--keystore-password:env"], converter = [EnvVarConverter::class],
            description = ["Environmental variable containing the passphrase to access the java keystore containing the signing key (if any)"])
        var keyStorePasswordEnv : String? = null

        @CommandLine.Option(names = ["-p:file", "--keystore-password:file"], converter = [FileContentConverter::class],
            description = ["File containing thr passphrase to access the java keystore containing the signing key (if any)"])
        var keyStorePasswordFile : String? = null

        @CommandLine.Option(names = ["-P", "--key-password"],
            description = ["Passphrase to access the private key to be used for signing (if any)"])
        var keyPassword : String? = null

        @CommandLine.Option(names = ["-P:file", "--key-password:file"], converter = [FileContentConverter::class],
            description = ["File containing thr passphrase to access the private key to be used for signing (if any)"])
        var keyPasswordFile : String? = null

        @CommandLine.Option(names = ["-P:env", "--key-password:env"], converter = [EnvVarConverter::class],
            description = ["Environmental variable containing thr passphrase to access the private key to be used for signing (if any)"])
        var keyPasswordEnv : String? = null
    }

    @CommandLine.Mixin
    var options = Options()

    @Suppress("ComplexMethod")
    override fun call(): Int {
        val params = options.keyStore.let { keyStore ->
            require(options.keyAlias != null) {
                "Key alias must be set with -a or --key-alias"
            }
            val keyStorePasswordErrorMessage = "One (and one only) between --keystore-password, --keystore-password:file, --keystore-password:env must be specified"
            val keyStorePassword = takeNotNull(keyStorePasswordErrorMessage,
                options.keyStorePassword,
                options.keyStorePasswordEnv,
                options.keyStorePasswordFile).toCharArray()
            val keyPasswordErrorMessage = "One (and one only) between --key-password, --key-password:file, --key-password:env must be specified"
            val keyPassword = takeNotNull(keyPasswordErrorMessage,
                options.keyPassword,
                options.keyPasswordEnv,
                options.keyPasswordFile)
            SigningParameters(
                keyStore = KeyStore.getInstance(keyStore!!.toFile(), keyStorePassword),
                keyAlias = options.keyAlias!!,
                keyPassword
            )
        }

        val tmpFile = Files.createTempFile(null, null)
        Files.newOutputStream(tmpFile).use { outputStream ->
            when(val source = options.source) {
                is Source.File -> Files.newInputStream(source.inputFile)
                is Source.Stream -> source.inputStream
            }.copyTo(outputStream)
        }

        ZipTweaker.removeJarSignature(tmpFile)
        val signingCertificates = params.keyStore.getCertificateChain(params.keyAlias)
            ?: throw java.lang.IllegalArgumentException("The specified keystore does not contain a key with alias '${params.keyAlias}'")
        val key = params.keyStore.getKey(params.keyAlias, params.keyPassword?.toCharArray()) as PrivateKey
        @Suppress("TooGenericExceptionCaught", "SpreadOperator")
        when(val destination = options.destination) {
            is Destination.File -> try {
                Files.newOutputStream(destination.outputFile).use {
                    Signing.signJar(tmpFile, it, key, options.signatureFileName, *signingCertificates)
                }
            } catch (ex : Exception) {
                Files.delete(destination.outputFile)
                throw ex
            }
            is Destination.Stream -> Signing.signJar(tmpFile, destination.outputStream, key, "SIGN", *signingCertificates)
        }
        return ExitCodes.SUCCESS.code
    }
}
