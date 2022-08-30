package net.corda.v5.uniqueness.model

import net.corda.data.uniqueness.UniquenessCheckResponseAvro
import net.corda.data.uniqueness.UniquenessCheckResultInputStateConflictAvro
import net.corda.data.uniqueness.UniquenessCheckResultInputStateUnknownAvro
import net.corda.data.uniqueness.UniquenessCheckResultMalformedRequestAvro
import net.corda.data.uniqueness.UniquenessCheckResultReferenceStateConflictAvro
import net.corda.data.uniqueness.UniquenessCheckResultReferenceStateUnknownAvro
import net.corda.data.uniqueness.UniquenessCheckResultSuccessAvro
import net.corda.data.uniqueness.UniquenessCheckResultTimeWindowOutOfBoundsAvro
import net.corda.v5.base.exceptions.CordaRuntimeException
import org.apache.avro.specific.SpecificRecord
import java.time.Instant

/**
 * Representation of the result of a uniqueness check request. This representation
 * is agnostic to both the message bus API and any DB schema that may be used to
 * persist data by the backing store.
 */
sealed class UniquenessCheckResult(val commitTimestamp: Instant) {
    companion object {
        const val RESULT_ACCEPTED_REPRESENTATION = 'A'
        const val RESULT_REJECTED_REPRESENTATION = 'R'

        /**
         * Converts an Avro response to a [UniquenessCheckResult].
         */
        fun fromAvroResponse(avroResponse: UniquenessCheckResponseAvro): UniquenessCheckResult {
            return when (val avroResult = avroResponse.result) {
                is UniquenessCheckResultInputStateConflictAvro -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.InputStateConflict(avroResult.conflictingStates.map {
                            // FIXME Consuming tx hash is populated as [null] for now
                            UniquenessCheckStateDetails(UniquenessCheckStateRef.fromString(it), null)
                        })
                    )
                }
                is UniquenessCheckResultInputStateUnknownAvro -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.InputStateUnknown(avroResult.unknownStates.map {
                            UniquenessCheckStateRef.fromString(it)
                        })
                    )
                }
                is UniquenessCheckResultReferenceStateConflictAvro -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.ReferenceStateConflict(avroResult.conflictingStates.map {
                            // FIXME Consuming tx hash is populated as [null] for now
                            UniquenessCheckStateDetails(UniquenessCheckStateRef.fromString(it), null)
                        })
                    )
                }
                is UniquenessCheckResultReferenceStateUnknownAvro -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.ReferenceStateUnknown(avroResult.unknownStates.map {
                            UniquenessCheckStateRef.fromString(it)
                        })
                    )
                }
                is UniquenessCheckResultTimeWindowOutOfBoundsAvro -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.TimeWindowOutOfBounds(
                            avroResult.evaluationTimestamp,
                            avroResult.timeWindowLowerBound,
                            avroResult.timeWindowUpperBound
                        )
                    )
                }
                is UniquenessCheckResultMalformedRequestAvro -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.MalformedRequest(
                            avroResult.errorText
                        )
                    )
                }
                is UniquenessCheckResultSuccessAvro -> {
                    Success(
                        Instant.now()
                    )
                }
                else -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.UnknownAvroResponse(
                            avroResult.javaClass.typeName
                        )
                    )
                }
            }
        }
    }

    class Success(
        commitTimestamp: Instant
    ) : UniquenessCheckResult(commitTimestamp) {
        override fun toCharacterRepresentation(): Char = RESULT_ACCEPTED_REPRESENTATION
    }

    class Failure(
        commitTimestamp: Instant,
        val error: UniquenessCheckError
    ) : UniquenessCheckResult(commitTimestamp) {
        override fun toCharacterRepresentation(): Char = RESULT_REJECTED_REPRESENTATION

        /**
         * Converts the failure to the external Avro error
         */
        fun toExternalError(): SpecificRecord {
            return when (error) {
                is UniquenessCheckError.InputStateConflict ->
                    UniquenessCheckResultInputStateConflictAvro(
                        error.conflictingStates.map { it.stateRef.toString() }
                    )
                is UniquenessCheckError.InputStateUnknown ->
                    UniquenessCheckResultInputStateUnknownAvro(
                        error.unknownStates.map { it.toString() }
                    )
                is UniquenessCheckError.ReferenceStateConflict ->
                    UniquenessCheckResultReferenceStateConflictAvro(
                        error.conflictingStates.map { it.stateRef.toString() }
                    )
                is UniquenessCheckError.ReferenceStateUnknown ->
                    UniquenessCheckResultReferenceStateUnknownAvro(
                        error.unknownStates.map { it.toString() }
                    )
                is UniquenessCheckError.TimeWindowOutOfBounds ->
                    with(error) {
                        UniquenessCheckResultTimeWindowOutOfBoundsAvro(
                            evaluationTimestamp,
                            timeWindowLowerBound,
                            timeWindowUpperBound
                        )
                    }
                is UniquenessCheckError.MalformedRequest ->
                    UniquenessCheckResultMalformedRequestAvro(
                        error.errorText
                    )
                is UniquenessCheckError.UnknownAvroResponse ->
                    // TODO This scenario should never happen because `UnknownAvroResponse` is only
                    //  created when reading from the message bus, however, toExternalError will not
                    //  be called after that point. However, there might be a better way to handle this.
                    throw CordaRuntimeException(
                        "Unknown avro response error cannot be mapped as external error."
                    )
            }
        }
    }

    abstract fun toCharacterRepresentation(): Char
}
