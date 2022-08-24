package net.corda.v5.application.uniqueness.model

import net.corda.data.uniqueness.UniquenessCheckExternalResponse
import net.corda.data.uniqueness.UniquenessCheckExternalResultInputStateConflict
import net.corda.data.uniqueness.UniquenessCheckExternalResultInputStateUnknown
import net.corda.data.uniqueness.UniquenessCheckExternalResultMalformedRequest
import net.corda.data.uniqueness.UniquenessCheckExternalResultReferenceStateConflict
import net.corda.data.uniqueness.UniquenessCheckExternalResultReferenceStateUnknown
import net.corda.data.uniqueness.UniquenessCheckExternalResultTimeWindowOutOfBounds
import net.corda.v5.base.exceptions.CordaRuntimeException
import org.apache.avro.specific.SpecificRecord
import java.time.Instant

/**
 * TODO Rewrite KDocs
 */
sealed class UniquenessCheckResult(val commitTimestamp: Instant) {
    companion object {
        const val RESULT_ACCEPTED_REPRESENTATION = 'A'
        const val RESULT_REJECTED_REPRESENTATION = 'R'

        /**
         * Converts an Avro error to a [Failure]. Since there's no common superclass
         * for avro classes, the parameter can be anything, hence the [Any] type.
         */
        fun fromExternalError(avroResponse: UniquenessCheckExternalResponse): Failure {
            return when (val avroResult = avroResponse.result) {
                is UniquenessCheckExternalResultInputStateConflict -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.InputStateConflict(avroResult.conflictingStates.map {
                            // FIXME Consuming tx hash is populated as [null] for now
                            UniquenessCheckStateDetails(UniquenessCheckStateRef.fromString(it), null)
                        })
                    )
                }
                is UniquenessCheckExternalResultInputStateUnknown -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.InputStateUnknown(avroResult.unknownStates.map {
                            UniquenessCheckStateRef.fromString(it)
                        })
                    )
                }
                is UniquenessCheckExternalResultReferenceStateConflict -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.ReferenceStateConflict(avroResult.conflictingStates.map {
                            // FIXME Consuming tx hash is populated as [null] for now
                            UniquenessCheckStateDetails(UniquenessCheckStateRef.fromString(it), null)
                        })
                    )
                }
                is UniquenessCheckExternalResultReferenceStateUnknown -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.ReferenceStateUnknown(avroResult.unknownStates.map {
                            UniquenessCheckStateRef.fromString(it)
                        })
                    )
                }
                is UniquenessCheckExternalResultTimeWindowOutOfBounds -> {
                    Failure(
                        Instant.now(),
                        UniquenessCheckError.TimeWindowOutOfBounds(
                            avroResult.evaluationTimestamp,
                            avroResult.timeWindowLowerBound,
                            avroResult.timeWindowUpperBound
                        )
                    )
                }
                is UniquenessCheckExternalResultMalformedRequest -> {
                    // FIXME This should be handled differently
                    throw CordaRuntimeException("")
                }
                else -> {
                    // TODO should this be handled differently?
                    throw CordaRuntimeException("")
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
                    UniquenessCheckExternalResultInputStateConflict(
                        error.conflictingStates.map { it.stateRef.toString() }
                    )
                is UniquenessCheckError.InputStateUnknown ->
                    UniquenessCheckExternalResultInputStateUnknown(
                        error.unknownStates.map { it.toString() }
                    )
                is UniquenessCheckError.ReferenceStateConflict ->
                    UniquenessCheckExternalResultReferenceStateConflict(
                        error.conflictingStates.map { it.stateRef.toString() }
                    )
                is UniquenessCheckError.ReferenceStateUnknown ->
                    UniquenessCheckExternalResultReferenceStateUnknown(
                        error.unknownStates.map { it.toString() }
                    )
                is UniquenessCheckError.TimeWindowOutOfBounds ->
                    with(error) {
                        UniquenessCheckExternalResultTimeWindowOutOfBounds(
                            evaluationTimestamp,
                            timeWindowLowerBound,
                            timeWindowUpperBound
                        )
                    }
            }
        }
    }

    abstract fun toCharacterRepresentation(): Char
}
