package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import java.util.function.Predicate

/**
 *
 */
@DoNotImplement
interface UtxoLedgerTransaction {

    val outputs: List<TransactionState<*>>
    val outputStates: List<ContractState> get() = outputs.map { it.contractState }
    val inputStates: List<ContractState>
    val referenceStates: List<ContractState>
    val commands: List<CommandAndSignatories<*>>
    val attachments: List<Attachment>
    val timeWindow: TimeWindow?

    fun verify()

    // region Input

    fun getInputState(): ContractState
    fun getInputState(index: Int): ContractState
    fun getInputState(predicate: Predicate<ContractState>): ContractState

    fun getInputStateAndRef(): StateAndRef<ContractState>
    fun getInputStateAndRef(index: Int): StateAndRef<ContractState>
    fun getInputStateAndRef(predicate: Predicate<ContractState>): StateAndRef<ContractState>

    fun <T : ContractState> getInputState(type: Class<T>): T
    fun <T : ContractState> getInputState(type: Class<T>, index: Int): T
    fun <T : ContractState> getInputState(type: Class<T>, predicate: Predicate<T>): T

    fun <T : ContractState> getInputStateAndRef(type: Class<T>): StateAndRef<T>
    fun <T : ContractState> getInputStateAndRef(type: Class<T>, index: Int): StateAndRef<T>
    fun <T : ContractState> getInputStateAndRef(type: Class<T>, predicate: Predicate<T>): StateAndRef<T>

    fun <T : ContractState> getInputStates(type: Class<T>): List<T>
    fun <T : ContractState> getInputStates(type: Class<T>, indexes: Iterable<Int>): List<T>
    fun <T : ContractState> getInputStates(type: Class<T>, predicate: Predicate<T>): List<T>

    fun <T : ContractState> getInputStateAndRefs(type: Class<T>): List<StateAndRef<T>>
    fun <T : ContractState> getInputStateAndRefs(type: Class<T>, indexes: Iterable<Int>): List<StateAndRef<T>>
    fun <T : ContractState> getInputStateAndRefs(type: Class<T>, predicate: Predicate<T>): List<StateAndRef<T>>

    // endregion

    // region Reference Input

    fun getReferenceInputState(): ContractState
    fun getReferenceInputState(index: Int): ContractState
    fun getReferenceInputState(predicate: Predicate<ContractState>): ContractState

    fun getReferenceInputStateAndRef(): StateAndRef<ContractState>
    fun getReferenceInputStateAndRef(index: Int): StateAndRef<ContractState>
    fun getReferenceInputStateAndRef(predicate: Predicate<ContractState>): StateAndRef<ContractState>

    fun <T : ContractState> getReferenceInputState(type: Class<T>): T
    fun <T : ContractState> getReferenceInputState(type: Class<T>, index: Int): T
    fun <T : ContractState> getReferenceInputState(type: Class<T>, predicate: Predicate<T>): T

    fun <T : ContractState> getReferenceInputStateAndRef(type: Class<T>): StateAndRef<T>
    fun <T : ContractState> getReferenceInputStateAndRef(type: Class<T>, index: Int): StateAndRef<T>
    fun <T : ContractState> getReferenceInputStateAndRef(type: Class<T>, predicate: Predicate<T>): StateAndRef<T>

    fun <T : ContractState> getReferenceInputStates(type: Class<T>): List<T>
    fun <T : ContractState> getReferenceInputStates(type: Class<T>, indexes: Iterable<Int>): List<T>
    fun <T : ContractState> getReferenceInputStates(type: Class<T>, predicate: Predicate<T>): List<T>

    fun <T : ContractState> getReferenceInputStateAndRefs(type: Class<T>): List<StateAndRef<T>>
    fun <T : ContractState> getReferenceInputStateAndRefs(type: Class<T>, indexes: Iterable<Int>): List<StateAndRef<T>>
    fun <T : ContractState> getReferenceInputStateAndRefs(type: Class<T>, predicate: Predicate<T>): List<StateAndRef<T>>

    // endregion

    // region Output

    fun getOutputState(): ContractState
    fun getOutputState(index: Int): ContractState
    fun getOutputState(predicate: Predicate<ContractState>): ContractState

    fun getOutputStateAndRef(): StateAndRef<ContractState>
    fun getOutputStateAndRef(index: Int): StateAndRef<ContractState>
    fun getOutputStateAndRef(predicate: Predicate<ContractState>): StateAndRef<ContractState>

    fun <T : ContractState> getOutputState(type: Class<T>): T
    fun <T : ContractState> getOutputState(type: Class<T>, index: Int): T
    fun <T : ContractState> getOutputState(type: Class<T>, predicate: Predicate<T>): T

    fun <T : ContractState> getOutputStateAndRef(type: Class<T>): StateAndRef<T>
    fun <T : ContractState> getOutputStateAndRef(type: Class<T>, index: Int): StateAndRef<T>
    fun <T : ContractState> getOutputStateAndRef(type: Class<T>, predicate: Predicate<T>): StateAndRef<T>

    fun <T : ContractState> getOutputStates(type: Class<T>): List<T>
    fun <T : ContractState> getOutputStates(type: Class<T>, indexes: Iterable<Int>): List<T>
    fun <T : ContractState> getOutputStates(type: Class<T>, predicate: Predicate<T>): List<T>

    fun <T : ContractState> getOutputStateAndRefs(type: Class<T>): List<StateAndRef<T>>
    fun <T : ContractState> getOutputStateAndRefs(type: Class<T>, indexes: Iterable<Int>): List<StateAndRef<T>>
    fun <T : ContractState> getOutputStateAndRefs(type: Class<T>, predicate: Predicate<T>): List<StateAndRef<T>>

    // endregion

    // region Commands

    fun <T : Command> getCommandAndSignatories(type: Class<T>): CommandAndSignatories<T>
    fun <T : Command> getCommandAndSignatories(type: Class<T>, index: Int): CommandAndSignatories<T>
    fun <T : Command> getCommandAndSignatories(type: Class<T>, predicate: Predicate<T>): CommandAndSignatories<T>

    fun <T : Command> getCommandsAndSignatories(type: Class<T>): List<CommandAndSignatories<T>>
    fun <T : Command> getCommandsAndSignatories(type: Class<T>, index: Int): List<CommandAndSignatories<T>>
    fun <T : Command> getCommandsAndSignatories(type: Class<T>, predicate: Predicate<T>): List<CommandAndSignatories<T>>

    // endregion

    // region Attachments

    fun getAttachment(index: Int): Attachment
    fun getAttachment(id: SecureHash): Attachment

    // endregion
}
