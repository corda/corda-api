package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
open class CSLThrottlingException : CryptoServiceLibraryException {
    companion object {
        const val DEFAULT_THROTTLE_INITIAL_BACKOFF = 1_000L
        const val DEFAULT_THROTTLE_BACKOFF_MULTIPLIER = 2
    }

    constructor(message: String) : super(message, isRecoverable = true)

    constructor(message: String, cause: Throwable?) : super(message, cause, isRecoverable = true)

    open fun getBackoff(attempt: Int, currentBackoffMillis: Long): Long =
        if(currentBackoffMillis < DEFAULT_THROTTLE_INITIAL_BACKOFF) {
            DEFAULT_THROTTLE_INITIAL_BACKOFF
        } else {
            currentBackoffMillis * DEFAULT_THROTTLE_BACKOFF_MULTIPLIER
        }
}