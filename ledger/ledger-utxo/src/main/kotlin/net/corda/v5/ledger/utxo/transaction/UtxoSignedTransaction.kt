package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash
import java.security.PublicKey

/**
 * Defines a signed UTXO transaction.
 *
 *  Comparing with [UtxoLedgerTransaction]:
 *  - It does not have access to the deserialized details.
 *  - It has direct access to the signatures.
 *  - It does not require a serializer.
 *
 * [UtxoSignedTransaction] wraps the wire representation of the transaction. It contains one or more signatures,
 * each one for a public key (including composite keys) that is mentioned inside a transaction state.
 * [UtxoSignedTransaction] is frequently passed around the network and stored. The identity of a transaction is
 * the hash of Merkle root of the wrapped wire representation, therefore if you are storing data keyed by wire
 * representations hash be aware that multiple different [UtxoSignedTransaction]s may map to the same key (and
 * they could be different in important ways, like validity!). The signatures on a UtxoSignedTransaction
 * might be invalid or missing: the type does not imply validity.
 * A transaction ID should be the hash of the wrapped wire representation's Merkle tree root.
 * Thus adding or removing a signature does not change it.
 */
@DoNotImplement
interface UtxoSignedTransaction {
    /**
     * @property id The ID of the transaction.
     */
    val id: SecureHash

    /**
     * @property signatures The signatures that have been applied to the transaction.
     */
    val signatures: List<DigitalSignatureAndMetadata>

    /**
     * Converts the current [UtxoSignedTransaction] into a [UtxoLedgerTransaction].
     *
     * @return Returns a [UtxoLedgerTransaction] from the current signed transaction.
     */
    fun toLedgerTransaction(): UtxoLedgerTransaction

    /**
     * Gets the missing signatories from the current [UtxoSignedTransaction].
     *
     * @return Returns a [Set] of [PublicKey] representing the missing signatories from the current [UtxoSignedTransaction].
     */
    @Suspendable
    fun getMissingSignatories(): Set<PublicKey>

    /**
     * Verify all available signatures and whether there are any missing ones.
     *
     * @throws TransactionVerificationException if any signatures are invalid or missing.
     */
    @Suspendable
    fun verifySignatures()
}