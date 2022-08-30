package net.corda.v5.application.uniqueness.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import net.corda.v5.base.annotations.CordaSerializable
import java.time.Instant

/**
 * Representation of errors that can be raised by the uniqueness checker. These errors are returned
 * through the uniqueness client service as well. This representation is agnostic to both the message
 * bus API and any DB schema that may be used to persist data by the backing store.
 *
 * Unfortunately since this is a sealed class with multiple implementations we need to tell Jackson
 * what types to look for. Hence the annotations. This will be removed when we start using standard
 * Corda serialization.
 */
@CordaSerializable
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = UniquenessCheckError.InputStateConflict::class),
    JsonSubTypes.Type(value = UniquenessCheckError.InputStateUnknown::class),
    JsonSubTypes.Type(value = UniquenessCheckError.ReferenceStateConflict::class),
    JsonSubTypes.Type(value = UniquenessCheckError.ReferenceStateUnknown::class),
    JsonSubTypes.Type(value = UniquenessCheckError.TimeWindowOutOfBounds::class),
)
sealed class UniquenessCheckError {
    /** Occurs when one or more input states have already been consumed by another transaction. */
    data class InputStateConflict(
        /** Specifies which states have already been consumed in another transaction. */
        val conflictingStates: List<UniquenessCheckStateDetails>
    ) : UniquenessCheckError()

    /** Occurs when one or more input states are not known to the uniqueness checker. */
    data class InputStateUnknown(
        /** Specifies which states are not known to the uniqueness checker. */
        val unknownStates: List<UniquenessCheckStateRef>
    ) : UniquenessCheckError()

    /** Occurs when one or more reference states have already been consumed by another transaction. */
    data class ReferenceStateConflict(
        /** Specifies which reference states have already been consumed in another transaction. */
        val conflictingStates: List<UniquenessCheckStateDetails>
    ) : UniquenessCheckError()

    /** Occurs when one or more reference states are not known to the uniqueness checker. */
    data class ReferenceStateUnknown(
        /** Specifies which states are not known to the uniqueness checker. */
        val unknownStates: List<UniquenessCheckStateRef>
    ) : UniquenessCheckError()

    /** Occurs when the specified time is outside the allowed tolerance. */
    data class TimeWindowOutOfBounds(
        val evaluationTimestamp: Instant,
        val timeWindowLowerBound: Instant?,
        val timeWindowUpperBound: Instant
    ) : UniquenessCheckError()
}
