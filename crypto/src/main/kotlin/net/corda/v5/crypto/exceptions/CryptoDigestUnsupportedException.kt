package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that a digest algorithm is not supported.
 *
 * @property digest the name of the algorithm which is not supported.
 */
@CordaSerializable
class CryptoDigestUnsupportedException : CryptoUnsupportedException {
    val digest: String

    constructor(digest: String, message: String) : super(message) {
        this.digest = digest
    }

    constructor(digest: String, message: String, cause: Throwable?) : super(message, cause) {
        this.digest = digest
    }
}