package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.common.transaction.TransactionVerificationException
import net.corda.v5.ledger.utxo.TimeWindow
import net.corda.v5.ledger.utxo.StateAndRef
import net.corda.v5.ledger.utxo.ContractState
import net.corda.v5.ledger.utxo.TransactionState
import net.corda.v5.ledger.utxo.Attachment
import net.corda.v5.ledger.utxo.Command
import java.security.PublicKey

/**
 * Represents a UTXO ledger transaction.
 *
 * @property timeWindow The time window associated with the current [UtxoLedgerTransaction].
 * @property attachments The attachments associated with the current [UtxoLedgerTransaction].
 * @property commands The commands and signatories associated with the current [UtxoLedgerTransaction].
 * @property inputStateAndRefs The input states and state references to be consumed by the current [UtxoLedgerTransaction].
 * @property inputTransactionStates The input transaction states to be consumed by the current [UtxoLedgerTransaction].
 * @property inputContractStates The input contract states to be consumed by the current [UtxoLedgerTransaction].
 * @property referenceInputStateAndRefs The input states and state referenced to be referenced by the current [UtxoLedgerTransaction].
 * @property referenceInputTransactionStates The input transaction states to be referenced by the current [UtxoLedgerTransaction].
 * @property referenceInputContractStates The input contract states to be referenced by the current [UtxoLedgerTransaction].
 * @property outputStateAndRefs The output states and state references to be created by the current [UtxoLedgerTransaction].
 * @property outputTransactionStates The output transaction states to be created by the current [UtxoLedgerTransaction].
 * @property outputContractStates The output contract states to be created by the current [UtxoLedgerTransaction].
 */
@DoNotImplement
@Suppress("TooManyFunctions")
interface UtxoLedgerTransaction {

    /**
     * @property id The ID of the transaction.
     */
    val id: SecureHash

    val timeWindow: TimeWindow
    val attachments: List<Attachment>
    val commands: List<Command>
    val signatories: List<PublicKey>

    val inputStateAndRefs: List<StateAndRef<*>>
    val inputTransactionStates: List<TransactionState<*>> get() = inputStateAndRefs.map { it.state }
    val inputContractStates: List<ContractState> get() = inputStateAndRefs.map { it.state.contractState }

    val referenceInputStateAndRefs: List<StateAndRef<*>>
    val referenceInputTransactionStates: List<TransactionState<*>> get() = referenceInputStateAndRefs.map { it.state }
    val referenceInputContractStates: List<ContractState> get() = referenceInputTransactionStates.map { it.contractState }

    val outputStateAndRefs: List<StateAndRef<*>>
    val outputTransactionStates: List<TransactionState<*>> get() = outputStateAndRefs.map { it.state }
    val outputContractStates: List<ContractState> get() = outputTransactionStates.map { it.contractState }

    /**
     * Verifies the current [UtxoLedgerTransaction].
     *
     * @throws TransactionVerificationException if the current [UtxoLedgerTransaction] fails to verify correctly.
     */
    fun verify()

    /**
     * Obtains the ledger transaction [Attachment] with the specified id.
     *
     * @param id The id of the ledger transaction [Attachment] to obtain.
     * @return Returns the ledger transaction [Attachment] with the specified id.
     * @throws IllegalArgumentException if the ledger transaction [Attachment] with the specified id cannot be found.
     */
    fun getAttachment(id: SecureHash): Attachment

    /**
     * Obtains all ledger transaction [Command] instances that match the specified type.
     *
     * @param T The underlying type of the [Command].
     * @param type The type of the [Command].
     * @return Returns all ledger transaction [Command] instances that match the specified type.
     */
    fun <T : Command> getCommands(type: Class<T>): List<T>

    /**
     * Obtains all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getInputStateAndRefs(type: Class<T>): List<StateAndRef<T>>

    /**
     * Obtains all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getInputStates(type: Class<T>): List<T>

    /**
     * Obtains all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getReferenceInputStateAndRefs(type: Class<T>): List<StateAndRef<T>>

    /**
     * Obtains all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getReferenceInputStates(type: Class<T>): List<T>

    /**
     * Obtains all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getOutputStateAndRefs(type: Class<T>): List<StateAndRef<T>>

    /**
     * Obtains all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getOutputStates(type: Class<T>): List<T>
}

/**
 * Obtains all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getInputStateAndRefs(): List<StateAndRef<T>> {
    return getInputStateAndRefs(T::class.java)
}

/**
 * Obtains all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getInputStates(): List<T> {
    return getInputStates(T::class.java)
}

/**
 * Obtains all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getReferenceInputStateAndRefs(): List<StateAndRef<T>> {
    return getReferenceInputStateAndRefs(T::class.java)
}

/**
 * Obtains all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getReferenceInputStates(): List<T> {
    return getReferenceInputStates(T::class.java)
}

/**
 * Obtains all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getOutputStateAndRefs(): List<StateAndRef<T>> {
    return getOutputStateAndRefs(T::class.java)
}

/**
 * Obtains all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getOutputStates(): List<T> {
    return getOutputStates(T::class.java)
}
