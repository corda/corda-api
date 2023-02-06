package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.common.Party
import net.corda.v5.ledger.common.transaction.TransactionNoAvailableKeysException
import net.corda.v5.ledger.utxo.Attachment
import net.corda.v5.ledger.utxo.Command
import net.corda.v5.ledger.utxo.ContractState
import net.corda.v5.ledger.utxo.StateRef
import java.security.PublicKey
import java.time.Instant

/**
 * Defines a builder for UTXO transactions.
 *
 * @property notary The transaction notary.
 */
@DoNotImplement
@Suppress("TooManyFunctions")
interface UtxoTransactionBuilder {

    val notary: Party?

    /**
     * Adds the specified [Attachment] to the current [UtxoTransactionBuilder].
     *
     * @param attachmentId The ID of the [Attachment] to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the specified [Attachment].
     */
    fun addAttachment(attachmentId: SecureHash): UtxoTransactionBuilder

    /**
     * Adds the specified command to the current [UtxoTransactionBuilder].
     *
     * @param command The command to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified command.
     */
    fun addCommand(command: Command): UtxoTransactionBuilder

    /**
     * Adds the specified signatories to the current [UtxoTransactionBuilder].
     *
     * @param signatories The signatories to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified signatories.
     */
    fun addSignatories(signatories: Iterable<PublicKey>): UtxoTransactionBuilder

    /**
     * Adds the specified input state to the current [UtxoTransactionBuilder].
     *
     * @param stateRef The [StateRef] instance of the input state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified input state.
     */
    fun addInputState(stateRef: StateRef): UtxoTransactionBuilder

    /**
     * Adds the specified input states to the current [UtxoTransactionBuilder].
     *
     * @param stateRefs The [StateRef] instances of the input state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified input states.
     */
    fun addInputStates(stateRefs: Iterable<StateRef>): UtxoTransactionBuilder

    /**
     * Adds the specified input states to the current [UtxoTransactionBuilder].
     *
     * @param stateRefs The [StateRef] instances of the input state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified input states.
     */
    fun addInputStates(vararg stateRefs: StateRef): UtxoTransactionBuilder

    /**
     * Adds the specified reference state to the current [UtxoTransactionBuilder].
     *
     * @param stateRef The [StateRef] instance of the reference state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified reference states.
     */
    fun addReferenceState(stateRef: StateRef): UtxoTransactionBuilder

    /**
     * Adds the specified reference states to the current [UtxoTransactionBuilder].
     *
     * @param stateRefs The [StateRef] instances of the reference state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified reference states.
     */
    fun addReferenceStates(stateRefs: Iterable<StateRef>): UtxoTransactionBuilder

    /**
     * Adds the specified reference states to the current [UtxoTransactionBuilder].
     *
     * @param stateRefs The [StateRef] instances of the reference state to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified reference states.
     */
    fun addReferenceStates(vararg stateRefs: StateRef): UtxoTransactionBuilder

    /**
     * Adds the specified output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] instance to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified output states.
     */
    fun addOutputState(contractState: ContractState): UtxoTransactionBuilder

    /**
     * Adds the specified output states to the current [UtxoTransactionBuilder].
     *
     * @param contractStates The [ContractState] instances to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified output states.
     */
    fun addOutputStates(contractStates: Iterable<ContractState>): UtxoTransactionBuilder

    /**
     * Adds the specified output states to the current [UtxoTransactionBuilder].
     *
     * @param contractStates The [ContractState] instances to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified output states.
     */
    fun addOutputStates(vararg contractStates: ContractState): UtxoTransactionBuilder

    /**
     * Adds the specified output states to the current [UtxoTransactionBuilder] as a tagged encumbrance group.
     *
     * @param tag The tag of the encumbrance group which the specified [ContractState] instances will belong to.
     * @param contractStates The [ContractState] instances to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified encumbered output states.
     */
    fun addEncumberedOutputStates(tag: String, contractStates: Iterable<ContractState>): UtxoTransactionBuilder

    /**
     * Adds the specified output states to the current [UtxoTransactionBuilder] as a tagged encumbrance group.
     *
     * @param tag The tag of the encumbrance group which the specified [ContractState] instances will belong to.
     * @param contractStates The [ContractState] instances to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the specified encumbered output states.
     */
    fun addEncumberedOutputStates(tag: String, vararg contractStates: ContractState): UtxoTransactionBuilder

    /**
     * Gets a list of encumbered [ContractState] instances from the specified encumbrance group tag.
     *
     * @param tag The encumbrance group tag for which to obtain the associated list of [ContractState] instances.
     * @return Returns a list of encumbered [ContractState] instances from the specified encumbrance group tag.
     * @throws IllegalArgumentException if the encumbrance group tag does not exist.
     */
    fun getEncumbranceGroup(tag: String): List<ContractState>

    /**
     * Gets a map of encumbrance group tags and the associated encumbered [ContractState] instances.
     *
     * @return Returns map of encumbrance group tags and the associated encumbered [ContractState] instances.
     */
    fun getEncumbranceGroups(): Map<String, List<ContractState>>

    /**
     * Sets the specified [Party] as a notary to the current [UtxoTransactionBuilder].
     *
     * @param notary The [Party] to set as a notary to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with the new notary.
     */
    fun setNotary(notary: Party): UtxoTransactionBuilder

    /**
     * Sets the transaction time window to be valid until the specified [Instant], tending towards negative infinity.
     *
     * @param until The [Instant] until which the transaction time window is valid.
     * @return Returns a [UtxoTransactionBuilder] including the specified time window.
     */
    fun setTimeWindowUntil(until: Instant): UtxoTransactionBuilder

    /**
     * Sets the transaction time window to be valid between the specified [Instant] values.
     *
     * @param from The [Instant] from which the transaction time window is valid.
     * @param until The [Instant] until which the transaction time window is valid.
     * @return Returns a [UtxoTransactionBuilder] including the specified time window.
     */
    fun setTimeWindowBetween(from: Instant, until: Instant): UtxoTransactionBuilder

    /**
     * Verifies the content of the [UtxoTransactionBuilder] and
     * signs the transaction with any required signatories that belong to the current node.
     *
     * Calling this function once consumes the [UtxoTransactionBuilder], so it cannot be used again.
     * Therefore, if you want to build two transactions you need two builders.
     *
     * @return Returns a [UtxoSignedTransaction] with signatures for any required signatories that belong to the current node.
     *
     * @throws IllegalStateException when called a second time on the same object to prevent
     *      unintentional duplicate transactions.
     * @throws TransactionNoAvailableKeysException if none of the required keys are available to sign the transaction.
     */
    @Suspendable
    fun toSignedTransaction(): UtxoSignedTransaction
}
