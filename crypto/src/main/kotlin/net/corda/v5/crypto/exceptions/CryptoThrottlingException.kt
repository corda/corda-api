package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that there is a throttling by a downstream service, such as HSM or any other.
 */
@CordaSerializable
abstract class CryptoThrottlingException : CryptoException {
    constructor(message: String) : super(message, true)

    constructor(message: String, cause: Throwable?) : super(message, true, cause)

    /**
     * Returns the next wait period in milliseconds for the given attempt and current waiting period.
     * The return value of -1 would mean that the operations is deemed unrecoverable so no further attempts to retry.
     */
    abstract fun getBackoff(attempt: Int, currentBackoffMillis: Long): Long
}