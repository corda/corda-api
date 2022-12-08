package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.ledger.utxo.Command
import net.corda.v5.ledger.utxo.ContractState
import net.corda.v5.ledger.utxo.StateRef
import java.security.PublicKey
import java.util.function.Predicate

/**
 * [UtxoFilteredTransactionBuilder] specifies what component groups to include in a [UtxoFilteredTransaction]. Any component groups not
 * specified by the builder are filtered out, so that their content or proof of existence is not included in the resulting
 * [UtxoFilteredTransaction].
 */
interface UtxoFilteredTransactionBuilder {

    /**
     * Includes an audit proof of [UtxoSignedTransaction.notary] and [UtxoSignedTransaction.timeWindow] in the [UtxoFilteredTransaction].
     * 
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withNotaryAndTimeWindow(): UtxoFilteredTransactionBuilder

    /**
     * Includes a size proof of [UtxoSignedTransaction.signatories] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withSignatoriesSize(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.signatories] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withSignatories(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.signatories] in the [UtxoFilteredTransaction].
     *
     * @param predicate Filtering function that is applied to each deserialized component with the group. A component is included in the
     * filtered component group when [predicate] returns `true` and is filtered out when `false` is returned.
     * 
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withSignatories(predicate: Predicate<PublicKey>): UtxoFilteredTransactionBuilder

    /**
     * Includes a size proof of [UtxoSignedTransaction.inputStateRefs] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withInputStatesSize(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.inputStateRefs] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withInputStates(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.inputStateRefs] in the [UtxoFilteredTransaction].
     *
     * @param predicate Filtering function that is applied to each deserialized component with the group. A component is included in the
     * filtered component group when [predicate] returns `true` and is filtered out when `false` is returned.
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withInputStates(predicate: Predicate<StateRef>): UtxoFilteredTransactionBuilder

    /**
     * Includes a size proof of [UtxoSignedTransaction.referenceStateRefs] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withReferenceInputStatesSize(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.inputStateRefs] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withReferenceInputStates(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.referenceStateRefs] in the [UtxoFilteredTransaction].
     *
     * @param predicate Filtering function that is applied to each deserialized component with the group. A component is included in the
     * filtered component group when [predicate] returns `true` and is filtered out when `false` is returned.
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withReferenceInputStates(predicate: Predicate<StateRef>): UtxoFilteredTransactionBuilder

    /**
     * Includes a size proof of [UtxoSignedTransaction.outputStateAndRefs] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withOutputStatesSize(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.outputStateAndRefs] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withOutputStates(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.outputStateAndRefs] in the [UtxoFilteredTransaction].
     *
     * @param predicate Filtering function that is applied to each deserialized component with the group. A component is included in the
     * filtered component group when [predicate] returns `true` and is filtered out when `false` is returned.
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withOutputStates(predicate: Predicate<ContractState>): UtxoFilteredTransactionBuilder

    /**
     * Includes a size proof of [UtxoSignedTransaction.commands] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withCommandsSize(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.commands] in the [UtxoFilteredTransaction].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withCommands(): UtxoFilteredTransactionBuilder

    /**
     * Includes an audit proof of [UtxoSignedTransaction.commands] in the [UtxoFilteredTransaction].
     *
     * @param predicate Filtering function that is applied to each deserialized component with the group. A component is included in the
     * filtered component group when [predicate] returns `true` and is filtered out when `false` is returned.
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun withCommands(predicate: Predicate<Command>): UtxoFilteredTransactionBuilder

    /**
     * Creates a [UtxoFilteredTransaction] that contains the component groups and components specified by [UtxoFilteredTransactionBuilder].
     *
     * @return An updated copy of the [UtxoFilteredTransactionBuilder].
     */
    @Suspendable
    fun toFilteredTransaction(): UtxoFilteredTransaction
}