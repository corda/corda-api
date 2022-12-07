package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.ledger.utxo.Command
import net.corda.v5.ledger.utxo.ContractState
import net.corda.v5.ledger.utxo.StateRef
import java.security.PublicKey
import java.util.function.Predicate

interface UtxoFilteredTransactionBuilder {

    // and time window
    // no benefit of predicates for inputs
    @Suspendable
    fun withNotary(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withSignatoriesSize(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withSignatories(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withSignatories(predicate: Predicate<PublicKey>): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withInputStatesSize(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withInputStates(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withInputStates(predicate: Predicate<StateRef>): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withReferenceInputStatesSize(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withReferenceInputStates(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withReferenceInputStates(predicate: Predicate<StateRef>): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withOutputStatesSize(): UtxoFilteredTransactionBuilder

    // add with output states infos (for type info)
    // flag for with all output info or only kept info
    // TODO Think about emc
    @Suspendable
    fun withOutputStates(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withOutputStates(predicate: Predicate<ContractState>): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withCommandsSize(): UtxoFilteredTransactionBuilder

    // add with command infos (for type info)
    @Suspendable
    fun withCommands(): UtxoFilteredTransactionBuilder

    @Suspendable
    fun withCommands(predicate: Predicate<Command>): UtxoFilteredTransactionBuilder

    @Suspendable
    fun toFilteredTransaction(): UtxoFilteredTransaction
}