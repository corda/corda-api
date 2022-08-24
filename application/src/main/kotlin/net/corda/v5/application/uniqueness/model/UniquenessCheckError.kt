package net.corda.v5.application.uniqueness.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import net.corda.v5.base.annotations.CordaSerializable
import java.time.Instant

/**
 * TODO Rewrite KDocs
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
