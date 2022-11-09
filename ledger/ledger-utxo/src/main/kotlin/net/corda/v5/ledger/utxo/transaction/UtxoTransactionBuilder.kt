package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.common.Party
import net.corda.v5.ledger.utxo.Attachment
import net.corda.v5.ledger.utxo.Command
import net.corda.v5.ledger.utxo.ContractState
import net.corda.v5.ledger.utxo.StateAndRef
import java.security.PublicKey
import java.time.Instant

/**
 * Defines a builder for UTXO transactions.
 *
 * @property notary The transaction notary.
 */
@DoNotImplement
@CordaSerializable
@Suppress("TooManyFunctions")
interface UtxoTransactionBuilder {

    val notary: Party?

    /**
     * Adds an [Attachment] to the current [UtxoTransactionBuilder].
     *
     * @param attachmentId The ID of the [Attachment] to add to the current [UtxoTransactionBuilder].
     * @return Returns a new [UtxoTransactionBuilder] with an added [Attachment].
     */
    fun addAttachment(attachmentId: SecureHash): UtxoTransactionBuilder

    /**
     * Adds a command to the current [UtxoTransactionBuilder].
     *
     * @param command The command to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional command.
     */
    fun addCommand(command: Command): UtxoTransactionBuilder

    /**
     * Adds signatories to the current [UtxoTransactionBuilder].
     *
     * @param signatories The signatories to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional signatories.
     */
    fun addSignatories(signatories: Iterable<PublicKey>): UtxoTransactionBuilder

    /**
     * Adds a command and associated signatories to the current [UtxoTransactionBuilder].
     *
     * @param command The command to add to the current [UtxoTransactionBuilder].
     * @param signatories The associated signatories to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional command and associated signatories.
     */
    fun addCommandAndSignatories(command: Command, signatories: Iterable<PublicKey>): UtxoTransactionBuilder

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
    fun addReferenceInputState(stateAndRef: StateAndRef<*>): UtxoTransactionBuilder

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState): UtxoTransactionBuilder

    /**
     * Adds an output state to the current [UtxoTransactionBuilder].
     *
     * @param contractState The [ContractState] to add to the current [UtxoTransactionBuilder].
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     * @return Returns a [UtxoTransactionBuilder] including the additional output state.
     */
    fun addOutputState(contractState: ContractState, encumbrance: Int?): UtxoTransactionBuilder

    /**
     * Sets the [Party] as a notary to the current [UtxoTransactionBuilder].
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
     * Signs the transaction with any required signatories that belong to the current node.
     *
     * @return Returns a [UtxoSignedTransaction] with signatures for any required signatories that belong to the current node.
     *
     * @throws IllegalStateException when called a second time on the same object to prevent
     *      unintentional duplicate transactions.
     */
    @Suspendable
    fun sign(): UtxoSignedTransaction

    /**
     * Signs the transaction with the specified signatory keys.
     *
     * @param signatories The signatories expected to sign the current transaction.
     * @return Returns a [UtxoSignedTransaction] with signatures for the specified signatory keys.
     *
     * @throws IllegalStateException when called a second time on the same object to prevent
     *      unintentional duplicate transactions.
     */
    @Suspendable
    fun sign(signatories: Iterable<PublicKey>): UtxoSignedTransaction

    /**
     * Signs the transaction with the specified signatory keys.
     *
     * @param signatories The signatories expected to sign the current transaction.
     * @return Returns a [UtxoSignedTransaction] with signatures for the specified signatory keys.
     *
     * @throws IllegalStateException when called a second time on the same object to prevent
     *      unintentional duplicate transactions.
     */
    @Suspendable
    fun sign(vararg signatories: PublicKey): UtxoSignedTransaction
}
