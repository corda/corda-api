package net.corda.v5.ledger.notary.plugin.core

import net.corda.v5.application.uniqueness.model.UniquenessCheckStateDetails
import net.corda.v5.application.uniqueness.model.UniquenessCheckStateRef
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import java.time.Instant

/**
 * Representation of errors that can be returned by the notary (plugin). Some of these errors have a counterpart in
 * [UniquenessCheckError][net.corda.v5.application.uniqueness.model.UniquenessCheckError] since some of the notary
 * errors might come from the uniqueness checker.
 */
@CordaSerializable
@DoNotImplement
interface NotaryError

/** Occurs when one or more input states have already been consumed by another transaction. */
interface NotaryErrorInputStateConflict : NotaryError {
    /** Specifies which states have already been consumed in another transaction. */
    val conflictingStates: List<UniquenessCheckStateDetails>
}

/** Occurs when one or more input states are not known to the uniqueness checker. */
interface NotaryErrorInputStateUnknown : NotaryError {
    /** Specifies which states are not known to the uniqueness checker. */
    val unknownStates: List<UniquenessCheckStateRef>
}

/** Occurs when one or more reference states have already been consumed by another transaction. */
interface NotaryErrorReferenceStateConflict : NotaryError {
    /** Specifies which reference states have already been consumed in another transaction. */
    val conflictingStates: List<UniquenessCheckStateDetails>
}

/** Occurs when one or more reference states are not known to the uniqueness checker. */
interface NotaryErrorReferenceStateUnknown : NotaryError {
    /** Specifies which states are not known to the uniqueness checker. */
    val unknownStates: List<UniquenessCheckStateRef>
}

/** Occurs when the specified time is outside the allowed tolerance. */
interface NotaryErrorTimeWindowOutOfBounds : NotaryError {
    val evaluationTimestamp: Instant
    val timeWindowLowerBound: Instant?
    val timeWindowUpperBound: Instant
}

/** Occurs when data in the received request is considered invalid by the uniqueness checker. */
interface NotaryErrorMalformedRequest : NotaryError {
    val errorText: String
}

/** Error type used for scenarios that were unexpected, or couldn't be mapped. */
interface NotaryErrorGeneral : NotaryError {
    val errorText: String
}

// TODO CORE-7249 Extend with more errors once FilteredTransaction support has been added