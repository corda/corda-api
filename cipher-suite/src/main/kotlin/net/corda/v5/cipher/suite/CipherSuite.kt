package net.corda.v5.cipher.suite

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.cipher.suite.handlers.encoding.KeyEncodingHandler
import net.corda.v5.cipher.suite.handlers.verification.VerifySignatureHandler

/**
 * Represents a root class for the cipher suite running in the crypto worker.
 * When custom implementations of [net.corda.v5.cipher.suite.handlers.CipherSuiteRegistrar] are called
 * they will have to invoke the [register] methods to register handlers which they provide.
 */
@DoNotImplement
interface CipherSuite : CipherSuiteBase {
    /**
     * Registers handlers and supported signature schemes for the given [KeyScheme].
     */
    fun register(
        keyScheme: KeySchemeInfo,
        encodingHandler: KeyEncodingHandler?,
        verifyHandler: VerifySignatureHandler?
    )
}

