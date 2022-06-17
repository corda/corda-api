package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that there is a problem with configuration, either it's missing or misconfigured.
 */
@CordaSerializable
class CryptoConfigurationException : CryptoException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable?) : super(message, cause)
}