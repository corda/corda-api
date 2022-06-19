package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Base exception to signal that some kind of resource was not found, key, tenant, etc.
 */
@CordaSerializable
open class CryptoResourceNotFoundException : CryptoException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable?) : super(message, cause)
}