package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Base class to signal that a key scheme, signature spec or digest is not supported.
 */
@CordaSerializable
open class CryptoUnsupportedException : CryptoException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable?) : super(message, cause)
}