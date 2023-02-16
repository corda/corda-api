package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.ledger.consensual.ConsensualState
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.ledger.common.transaction.TransactionNoAvailableKeysException

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
}
