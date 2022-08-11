package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.ledger.consensual.ConsensualState
import java.security.PublicKey
import java.time.Instant

/**
 * Defines a builder for [ConsensualSignedTransaction]s.
 *
 * //TODO(below docs are from obsolete part, review them.)
 * It is a transaction class that's mutable (unlike the others which are all immutable). It is
 * intended to be passed around parties that may edit it by adding new states. Then, once the states
 * are right, this class can be used as a holding bucket to gather signatures from multiple parties.
 */
@DoNotImplement
interface ConsensualTransactionBuilder {
    /**
     * @property timestamp The timestamp of the [ConsensualTransactionBuilder].
     */
    val timestamp: Instant?

    /**
     * Sets the specific timestamp to the [ConsensualTransactionBuilder].
     *
     * @param timestamp The timestamp to be set to the current [ConsensualTransactionBuilder].
     * @return Returns a new [ConsensualTransactionBuilder] with the specified timestamp.
     */
    fun withTimestamp(timestamp: Instant): ConsensualTransactionBuilder

    /**
     * @property states The output [ConsensualState]s of the [ConsensualTransactionBuilder].
     */
    val states: List<ConsensualState>

    /**
     * Adds the specified [ConsensualState] to the [ConsensualTransactionBuilder].
     *
     * @param state The state of the output to add to the current [ConsensualTransactionBuilder].
     * @return Returns a new [ConsensualTransactionBuilder] with the specified output state.
     */
    fun withState(state: ConsensualState) : ConsensualTransactionBuilder

    /**
     * 1. Verifies the content of the [ConsensualTransactionBuilder]
     * 2.a Creates
     * 2.b signs
     * 2.c and returns a [ConsensualSignedTransaction]
     *
     * @param publicKey The key for signing the [ConsensualSignedTransaction].
     * @return Returns a new [ConsensualSignedTransaction] with the specified details.
     */
    fun signInitial(publicKey: PublicKey): ConsensualSignedTransaction
}