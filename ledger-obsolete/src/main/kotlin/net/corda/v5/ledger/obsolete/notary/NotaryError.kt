package net.corda.v5.ledger.obsolete.notary

import java.time.Instant
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.exceptions.CordaRuntimeException
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.obsolete.contracts.StateRef
import net.corda.v5.ledger.obsolete.contracts.TimeWindow

/**
 * Exception thrown by the notary service if any issues are encountered while trying to commit a transaction. The
 * underlying [error] specifies the cause of failure.
 */
class NotaryException(
    /** Cause of notarisation failure. */
    val error: NotaryError,
    /** Id of the transaction to be notarised. Can be _null_ if an error occurred before the id could be resolved. */
    val txId: SecureHash?
) : CordaRuntimeException("Unable to notarise transaction ${txId ?: "<Unknown>"} : $error") {

    constructor(error: NotaryError) : this(error, txId = null)
}

/** Specifies the cause for notarisation request failure. */
@CordaSerializable
sealed class NotaryError {
    companion object {
        const val NUM_STATES = 5
    }

    /** Occurs when one or more input states have already been consumed by another transaction. */
    data class Conflict(
            /** Id of the transaction that was attempted to be notarised. */
            val txId: SecureHash,
            /** Specifies which states have already been consumed in another transaction. */
            val consumedStates: Map<StateRef, StateConsumptionDetails>
    ) : NotaryError() {
        override fun toString() = "One or more input states or referenced states have already been used as input states in other transactions. " +
                "Conflicting state count: ${consumedStates.size}, consumption details:\n" +
                "${consumedStates.asSequence().joinToString(",\n", limit = NUM_STATES) { it.key.toString() + " -> " + it.value }}.\n"
    }

    /** Occurs when time specified in the [TimeWindow] command is outside the allowed tolerance. */
    data class TimeWindowInvalid(val currentTime: Instant, val txTimeWindow: TimeWindow) : NotaryError() {
        override fun toString() = "Current time $currentTime is outside the time bounds specified by the transaction: $txTimeWindow"
    }

    /** Occurs when the provided transaction fails to verify. */
    data class TransactionInvalid(val cause: Throwable) : NotaryError() {
        override fun toString() = cause.toString()
    }

    /** Occurs when the notarisation request signature does not verify for the provided transaction. */
    data class RequestSignatureInvalid(val cause: Throwable) : NotaryError() {
        override fun toString() = "Request signature invalid: $cause"
    }

    /** Occurs when the notary service encounters an unexpected issue or becomes temporarily unavailable. */
    data class General(val cause: Throwable) : NotaryError() {
        override fun toString() = cause.toString()
    }
}

/**
 * Contains information about the consuming transaction for a particular state.
 *
 * @property hashOfTransactionId Hash of the consuming transaction id.
 *
 *
 * Note that this is NOT the transaction id itself – revealing it could lead to privacy leaks.
 *
 * @property type The type of consumed state: either a reference input state or a regular input state.
 */
@CordaSerializable
data class StateConsumptionDetails(
        val hashOfTransactionId: SecureHash,
        val type: ConsumedStateType
) {

    @CordaSerializable
    enum class ConsumedStateType { INPUT_STATE, REFERENCE_INPUT_STATE }

    fun copy(hashOfTransactionId: SecureHash): StateConsumptionDetails = StateConsumptionDetails(hashOfTransactionId, type)
}
