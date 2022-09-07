package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.common.Party
import net.corda.v5.ledger.common.transaction.PrivacySalt
import java.security.PublicKey
import java.util.*

/**
 * Defines a builder for UTXO transactions.
 *
 * @property lockId The transaction lock ID.
 * @property notary The transaction notary.
 * @property timeWindow The transaction time window.
 * @property privacySalt The transaction privacy salt.
 * @property attachments The transaction attachments.
 * @property commands The transaction commands.
 * @property inputStateAndRefs The transaction input state and refs.
 * @property referenceInputStateAndRefs The transaction referenced input state and refs.
 * @property outputTransactionStates The transaction output states.
 * @property requiredSignatories The signatories required to sign the current transaction.
 */
@DoNotImplement
@CordaSerializable
@Suppress("TooManyFunctions")
interface UtxoTransactionBuilder {

    val lockId: UUID
    val notary: Party?
    val timeWindow: TimeWindow?
    val privacySalt: PrivacySalt
    val attachments: List<SecureHash>
    val commands: List<CommandAndSignatories<*>>
    val inputStateAndRefs: List<StateAndRef<*>>
    val referenceInputStateAndRefs: List<StateAndRef<*>>
    val outputTransactionStates: List<TransactionState<*>>
    val requiredSignatories: List<PublicKey> get() = commands.flatMap { it.signatories }.distinct()

    /**
     * Adds an [Attachment] to the current [UtxoTransactionBuilder].
     *
     * @param attachmentId The ID of the [Attachment] to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with an added [Attachment].
     */
    fun addAttachment(attachmentId: SecureHash): UtxoTransactionBuilder

    /**
     * Adds a command and associated signatories to the current [UtxoTransactionBuilder].
     *
     * @param commandAndSignatories The command and associated signatories to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional command and associated signatories.
     */
    fun addCommandAndSignatories(commandAndSignatories: CommandAndSignatories<*>): UtxoTransactionBuilder

    /**
     * Adds a command and associated signatories to the current [UtxoTransactionBuilder].
     *
     * @param command The command to add to the current [UtxoTransactionBuilder].
     * @param signatories The associated signatories to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional command and associated signatories.
     */
    fun addCommandAndSignatories(command: Command, signatories: Iterable<PublicKey>): UtxoTransactionBuilder {
        return addCommandAndSignatories(CommandAndSignatories(command, signatories.toSet()))
    }

    /**
     * Adds a command and associated signatories to the current [UtxoTransactionBuilder].
     *
     * @param command The command to add to the current [UtxoTransactionBuilder].
     * @param signatories The associated signatories to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional command and associated signatories.
     */
    fun addCommandAndSignatories(command: Command, vararg signatories: PublicKey): UtxoTransactionBuilder {
        return addCommandAndSignatories(command, signatories.toSet())
    }

    /**
     * Adds an input state to the current [UtxoTransactionBuilder].
     *
     * @param stateAndRef The [StateAndRef] of the input state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional input state.
     */
    fun addInputState(stateAndRef: StateAndRef<*>): UtxoTransactionBuilder

    /**
     * Adds a reference input state to the current [UtxoTransactionBuilder].
     *
     * @param stateAndRef The [StateAndRef] of the reference input state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional reference input state.
     */
    fun addReferenceInputState(stateAndRef: StateAndRef<*>): UtxoTransactionBuilder {
        return addReferenceInputState(stateAndRef.toReferencedStateAndRef())
    }

    /**
     * Adds a reference input state to the current [UtxoTransactionBuilder].
     *
     * @param referencedStateAndRef The [ReferencedStateAndRef] to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional reference input state.
     */
    fun addReferenceInputState(referencedStateAndRef: ReferencedStateAndRef<*>): UtxoTransactionBuilder

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param transactionState The [TransactionState] to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(transactionState: TransactionState<*>): UtxoTransactionBuilder


    fun addOutputState(contractState: ContractState, transactionStateInformation: TransactionStateInformation): UtxoTransactionBuilder {
        return addOutputState(TransactionState(contractState, transactionStateInformation))
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState): UtxoTransactionBuilder {
        return addOutputState(contractState, contractState.getContractClassNameOrThrow(), CpkConstraint.AUTOMATIC_PLACEHOLDER)
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param notary The notary that will be used to notarise the specified [ContractState].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, notary: Party): UtxoTransactionBuilder {
        return addOutputState(contractState, contractState.getContractClassNameOrThrow(), notary)
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param constraint The [CpkConstraint] that determines which contract-code-containing CPK can be used with a [Contract].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, constraint: CpkConstraint): UtxoTransactionBuilder {
        return addOutputState(contractState, contractState.getContractClassNameOrThrow(), constraint)
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, contractId: String): UtxoTransactionBuilder {
        return addOutputState(contractState, contractId, CpkConstraint.AUTOMATIC_PLACEHOLDER)
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param constraint The [CpkConstraint] that determines which contract-code-containing CPK can be used with a [Contract].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, contractId: String, constraint: CpkConstraint): UtxoTransactionBuilder

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param notary The notary that will be used to notarise the specified [ContractState].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, contractId: String, notary: Party): UtxoTransactionBuilder {
        return addOutputState(contractState, contractId, notary, null, CpkConstraint.AUTOMATIC_PLACEHOLDER)
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param notary The notary that will be used to notarise the specified [ContractState].
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, contractId: String, notary: Party, encumbrance: Int?): UtxoTransactionBuilder {
        return addOutputState(contractState, contractId, notary, encumbrance, CpkConstraint.AUTOMATIC_PLACEHOLDER)
    }

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param notary The notary that will be used to notarise the specified [ContractState].
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     *
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(
        contractState: ContractState,
        contractId: String,
        notary: Party,
        encumbrance: Int?,
        constraint: CpkConstraint
    ): UtxoTransactionBuilder {
        return addOutputState(contractState, TransactionStateInformation(contractId, notary, encumbrance, constraint))
    }

    fun setNotary(notary: Party?): UtxoTransactionBuilder
    fun setTimeWindow(timeWindow: TimeWindow): UtxoTransactionBuilder
    fun setPrivacySalt(privacySalt: PrivacySalt): UtxoTransactionBuilder

    fun sign(): UtxoSignedTransaction // TODO Review?
    fun sign(signatory: PublicKey): UtxoSignedTransaction
    fun sign(signatories: Iterable<PublicKey>): UtxoSignedTransaction

    fun verify()
    fun verifyAndSign(): UtxoSignedTransaction
    fun verifyAndSign(signatory: PublicKey): UtxoSignedTransaction
    fun verifyAndSign(signatories: Iterable<PublicKey>): UtxoSignedTransaction

    fun toWireTransaction(): UtxoWireTransaction
}
