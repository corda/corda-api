package net.corda.v5.base.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Base interface for exceptions that are serializabel in Corda. Do not use directly, use [CordaRuntimeException]
 * instead.
 *
 * @property originalExceptionClassName Name of an exception that isn't serializable and therefore has been caugt and
 * converted to an exception in the Corda hierarchy.
 * @property originalMessage Message of the original exception
 */
@CordaSerializable
interface CordaThrowable {
    var originalExceptionClassName: String?
    val originalMessage: String?

    /**
     * Allows to set the message after constructing the exception object
     */
    fun setMessage(message: String?)

    /**
     * Allows to set a Throwable as cause after constructing the exception object
     */
    fun setCause(cause: Throwable?)
    fun addSuppressed(suppressed: Array<Throwable>)
}