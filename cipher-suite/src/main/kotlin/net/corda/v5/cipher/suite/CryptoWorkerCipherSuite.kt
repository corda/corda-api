package net.corda.v5.cipher.suite

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.cipher.suite.handlers.encoding.KeyEncodingHandler
import net.corda.v5.cipher.suite.handlers.generation.GenerateKeyHandler
import net.corda.v5.cipher.suite.handlers.signing.SignDataHandler
import net.corda.v5.cipher.suite.handlers.verification.VerifySignatureHandler

/**
 * Represents a root class for the cipher suite running in the crypto worker.
 * When custom implementations of [net.corda.v5.cipher.suite.handlers.CryptoWorkerCipherSuiteRegistrar] are called
 * they wil have to invoke the [register] methods to register handlers which they provide.
 */
@DoNotImplement
interface CryptoWorkerCipherSuite : CipherSuiteBase {
    /**
     * Registers handlers and supported signature schemes for the given [KeyScheme].
     */
    fun register(
        keyScheme: KeySchemeInfo,
        encodingHandler: KeyEncodingHandler?,
        verifyHandler: VerifySignatureHandler?,
        generateHandler: GenerateKeyHandler?,
        signHandler: SignDataHandler?
    )

    /**
     * Returns a registered [GenerateKeyHandler] for the given key scheme or null if none is registered.
     */
    fun findGenerateKeyHandler(schemeCodeName: String): GenerateKeyHandler?

    /**
     * Returns a registered [SignDataHandler] for the given key scheme or null if none is registered.
     */
    fun findSignDataHandler(schemeCodeName: String): SignDataHandler?
}