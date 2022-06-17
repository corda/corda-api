package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that a signature spec is not supported.
 *
 * @property specInfo the spec info such as algorithm name plus optional custom digests, and parameters.
 */
@CordaSerializable
class CryptoSignatureSpecUnsupportedException : CryptoUnsupportedException {
    val specInfo: String

    constructor(specInfo: String, message: String) : super(message) {
        this.specInfo = specInfo
    }

    constructor(specInfo: String, message: String, cause: Throwable?) : super(message, cause) {
        this.specInfo = specInfo
    }
}