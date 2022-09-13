package net.corda.v5.application.uniqueness.model

import java.time.Instant

/**
 * Representation of errors that can be raised by the uniqueness checker. These errors are returned
 * through the uniqueness client service.
 */
interface UniquenessCheckError

/** Occurs when one or more input states have already been consumed by another transaction. */
interface UniquenessCheckErrorInputStateConflict : UniquenessCheckError {
    /** Specifies which states have already been consumed in another transaction. */
    val conflictingStates: List<UniquenessCheckStateDetails>
}

/** Occurs when one or more input states are not known to the uniqueness checker. */
interface UniquenessCheckErrorInputStateUnknown : UniquenessCheckError {
    /** Specifies which states are not known to the uniqueness checker. */
    val unknownStates: List<UniquenessCheckStateRef>
}

/** Occurs when one or more reference states have already been consumed by another transaction. */
interface UniquenessCheckErrorReferenceStateConflict : UniquenessCheckError {
    /** Specifies which reference states have already been consumed in another transaction. */
    val conflictingStates: List<UniquenessCheckStateDetails>
}

/** Occurs when one or more reference states are not known to the uniqueness checker. */
interface UniquenessCheckErrorReferenceStateUnknown : UniquenessCheckError {
    /** Specifies which states are not known to the uniqueness checker. */
    val unknownStates: List<UniquenessCheckStateRef>
}

/** Occurs when the specified time is outside the allowed tolerance. */
interface UniquenessCheckErrorTimeWindowOutOfBounds : UniquenessCheckError {
    val evaluationTimestamp: Instant
    val timeWindowLowerBound: Instant?
    val timeWindowUpperBound: Instant
}

/**
 * Occurs when the uniqueness check encountered an error that cannot be categorised.
 * For example, unknown response, malformed request etc.
 */
interface UniquenessCheckErrorGeneral : UniquenessCheckError {
    val errorText: String
}