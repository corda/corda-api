package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.ledger.consensual.ConsensualState
import java.security.PublicKey
import net.corda.v5.base.annotations.Suspendable

/**
 * Defines a builder for [ConsensualSignedTransaction]s.
 *
 * It is a transaction class that's mutable (unlike the others which are all immutable). It is
 * intended to be passed around parties that may edit it by adding new states. Then, once the states
 * are right, this class can be used as a holding bucket to gather signatures from multiple parties.
 */
@DoNotImplement
interface ConsensualTransactionBuilder {
    /**
     * @property states The output [ConsensualState]s of the [ConsensualTransactionBuilder].
     */
    val states: List<ConsensualState>

    /**
     * Adds the specified [ConsensualState]s to the [ConsensualTransactionBuilder].
     *
     * @param states The states of the output to add to the current [ConsensualTransactionBuilder].
     * @return Returns a new [ConsensualTransactionBuilder] with the specified output states.
     */
    fun withStates(vararg states: ConsensualState) : ConsensualTransactionBuilder

    /**
     * Verifies the content of the [ConsensualTransactionBuilder] and
     * signs the transaction with any required signatories that belong to the current node.
     *
     * Calling this function once consumes the [ConsensualTransactionBuilder], so it cannot be used again.
     * Therefore, if you want to build two transactions you need two builders.
     *
     * @return Returns a [ConsensualSignedTransaction] with signatures for any required signatories that belong to the current node.
     *
     * @throws [UnsupportedOperationException] when called second time on the same object to prevent duplicate
     *      transactions accidentally.
     */
    @Suspendable
    fun sign(): ConsensualSignedTransaction

    /**
     * Verifies the content of the [ConsensualTransactionBuilder] and
     * signs the transaction with the specified signatory keys.
     *
     * Calling this function once consumes the [ConsensualTransactionBuilder], so it cannot be used again.
     * Therefore, if you want to build two transactions you need two builders.
     *
     * @param signatories The signatories expected to sign the current transaction.
     * @return Returns a [ConsensualSignedTransaction] with signatures for the specified signatory keys.
     *
     * @throws [UnsupportedOperationException] when called second time on the same object to prevent duplicate
     *      transactions accidentally.
     */
    @Suspendable
    fun sign(signatories: Iterable<PublicKey>): ConsensualSignedTransaction

    /**
     * Verifies the content of the [ConsensualTransactionBuilder] and
     * signs the transaction with the specified signatory keys.
     *
     * Calling this function once consumes the [ConsensualTransactionBuilder], so it cannot be used again.
     * Therefore, if you want to build two transactions you need two builders.
     *
     * @param signatories The signatories expected to sign the current transaction.
     * @return Returns a [ConsensualSignedTransaction] with signatures for the specified signatory keys.
     *
     * @throws [UnsupportedOperationException] when called second time on the same object to prevent duplicate
     *      transactions accidentally.
     */
    @Suspendable
    fun sign(vararg signatories: PublicKey): ConsensualSignedTransaction
}