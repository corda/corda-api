package net.corda.v5.ledger.utxo.uniqueness.data

import net.corda.v5.application.uniqueness.model.UniquenessCheckError
import net.corda.v5.ledger.utxo.StateRef
import java.time.Instant

/** Occurs when one or more input states have already been consumed by another transaction. */
interface UniquenessCheckErrorInputStateConflict : UniquenessCheckError {
    /** Specifies which states have already been consumed in another transaction. */
    val conflictingStates: List<UniquenessCheckStateDetails>
}

/** Occurs when one or more input states are not known to the uniqueness checker. */
interface UniquenessCheckErrorInputStateUnknown : UniquenessCheckError {
    /** Specifies which states are not known to the uniqueness checker. */
    val unknownStates: List<StateRef>
}

/** Occurs when one or more reference states have already been consumed by another transaction. */
interface UniquenessCheckErrorReferenceStateConflict : UniquenessCheckError {
    /** Specifies which reference states have already been consumed in another transaction. */
    val conflictingStates: List<UniquenessCheckStateDetails>
}

/** Occurs when one or more reference states are not known to the uniqueness checker. */
interface UniquenessCheckErrorReferenceStateUnknown : UniquenessCheckError {
    /** Specifies which states are not known to the uniqueness checker. */
    val unknownStates: List<StateRef>
}

/** Occurs when the specified time is outside the allowed tolerance. */
interface UniquenessCheckErrorTimeWindowOutOfBounds : UniquenessCheckError {
    val evaluationTimestamp: Instant
    val timeWindowLowerBound: Instant?
    val timeWindowUpperBound: Instant
}

/** Occurs when data in the received request is invalid. */
interface UniquenessCheckErrorMalformedRequest : UniquenessCheckError {
    val errorText: String
}
