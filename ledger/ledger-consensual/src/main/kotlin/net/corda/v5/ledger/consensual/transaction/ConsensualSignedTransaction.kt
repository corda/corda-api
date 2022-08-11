package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import java.security.PublicKey

/**
 * Defines a signed Consensual transaction.
 *
 *  Comparing with [ConsensualLedgerTransaction]:
 *  - It does not have access to the deserialized details.
 *  - It has direct access to the signatures.
 *  - It does not require a serializer.
 *
 * //TODO(below docs are from obsolete part, review them.)
 * [ConsensualSignedTransaction] wraps the wire representation of the transaction. It contains one or more signatures,
 * each one for a public key (including composite keys) that is mentioned inside a transaction state.
 * [ConsensualSignedTransaction] is frequently passed around the network and stored. The identity of a transaction is
 * the hash of Merkle root of the wrapped wire representation, therefore if you are storing data keyed by wire
 * representations hash be aware that multiple different [ConsensualSignedTransaction]s may map to the same key (and
 * they could be different in important ways, like validity!). The signatures on a ConsensualSignedTransaction
 * might be invalid or missing: the type does not imply validity.
 * A transaction ID should be the hash of the wrapped wire representation's Merkle tree root.
 * Thus adding or removing a signature does not change it.
 */
@DoNotImplement
@CordaSerializable
interface ConsensualSignedTransaction {
    /**
     * @property id The ID of the transaction.
     */
    val id: SecureHash

    /**
     * @property signatures The signatures that have been applied to the transaction.
     */
    val signatures: List<DigitalSignatureAndMetadata>

    /**
     * Converts the current [ConsensualSignedTransaction] into a [ConsensualLedgerTransaction].
     *
     * @param serializer The [SerializationService] required to convert the current transaction.
     * @return Returns a [ConsensualLedgerTransaction] from the current signed transaction.
     */
    fun toLedgerTransaction(serializer: SerializationService): ConsensualLedgerTransaction

    /**
     * Adds the specified signature to the current [ConsensualSignedTransaction].
     *
     * @param signature The signature and metadata to add to the current [ConsensualSignedTransaction].
     * @return Returns a new [ConsensualSignedTransaction] containing the applied signature.
     */
    fun addSignature(signature: DigitalSignatureAndMetadata): ConsensualSignedTransaction

    /**
     * Adds the specified signatures to the current [ConsensualSignedTransaction].
     *
     * @param signatures The signatures and metadata to add to the current [ConsensualSignedTransaction].
     * @return Returns a new [ConsensualSignedTransaction] containing the applied signatures.
     */
    fun addSignatures(signatures: Iterable<DigitalSignatureAndMetadata>): ConsensualSignedTransaction

    /**
     * Gets the signing keys for any missing transaction signatures.
     *
     * @param serializer The [SerializationService] required to obtain the missing keys.
     * @return Returns a [Set] of [PublicKey] representing the signing keys for any missing transaction signatures.
     */
    fun getMissingSigningKeys(serializer: SerializationService): Set<PublicKey>
}

/** Alias for [ConsensualSignedTransaction.addSignature] to let you use Kotlin operator overloading. */
operator fun ConsensualSignedTransaction.plus(sig: DigitalSignatureAndMetadata) = addSignature(sig)

/** Alias for [ConsensualSignedTransaction.addSignatures] to let you use Kotlin operator overloading. */
operator fun ConsensualSignedTransaction.plus(sigList: Collection<DigitalSignatureAndMetadata>) = addSignatures(sigList)
