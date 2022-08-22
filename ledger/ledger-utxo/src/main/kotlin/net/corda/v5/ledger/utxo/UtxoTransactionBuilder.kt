package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

/**
 * Defines a builder for UTXO transactions.
 */
@DoNotImplement
@CordaSerializable
interface UtxoTransactionBuilder {

    /**
     * Adds the specified input to the UTXO transaction.
     *
     * @param stateRef The state reference of the input to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified input state.
     */
    fun withInput(stateRef: StateRef): UtxoTransactionBuilder

    /**
     * Adds the specified inputs to the UTXO transaction.
     *
     * @param stateRefs The state references of the inputs to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified input states.
     */
    fun withInputs(stateRefs: Iterable<StateRef>): UtxoTransactionBuilder

    /**
     * Adds the specified reference input to the UTXO transaction.
     *
     * @param stateRef The state reference of the reference input to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified reference input state.
     */
    fun withReferenceInput(stateRef: StateRef): UtxoTransactionBuilder

    /**
     * Adds the specified reference inputs to the UTXO transaction.
     *
     * @param stateRefs The state references of the reference inputs to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified reference input states.
     */
    fun withReferenceInputs(stateRefs: Iterable<StateRef>): UtxoTransactionBuilder

    /**
     * Adds the specified output to the UTXO transaction.
     *
     * @param contractState The contract state output to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified output state.
     */
    fun withOutput(contractState: ContractState): UtxoTransactionBuilder

    /**
     * Adds the specified outputs to the UTXO transaction.
     *
     * @param contractStates The contract state outputs to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified outputs state.
     */
    fun withOutputs(contractStates: Iterable<ContractState>): UtxoTransactionBuilder

    /**
     * Adds the specified command to the UTXO transaction.
     *
     * @param commandAndSigningKeys The command and signing keys to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified command and signing keys.
     */
    fun withCommand(commandAndSigningKeys: CommandAndSigningKeys<*>): UtxoTransactionBuilder

    /**
     * Adds the specified commands to the UTXO transaction.
     *
     * @param commandAndSigningKeys The commands and signing keys to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified commands and signing keys.
     */
    fun withCommands(commandAndSigningKeys: Iterable<CommandAndSigningKeys<*>>): UtxoTransactionBuilder
}
