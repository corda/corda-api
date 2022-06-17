package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that a key scheme is not supported.
 *
 * @property codeName the name of the scheme which is not supported.
 */
@CordaSerializable
class CryptoKeySchemeUnsupportedException : CryptoUnsupportedException {
    val codeName: String

    constructor(codeName: String, message: String) : super(message) {
        this.codeName = codeName
    }

    constructor(codeName: String, message: String, cause: Throwable?) : super(message, cause) {
        this.codeName = codeName
    }
}