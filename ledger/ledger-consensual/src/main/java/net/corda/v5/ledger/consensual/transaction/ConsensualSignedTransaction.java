package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;
import java.util.Set;

/**
 * Defines a signed Consensual transaction.
 *
 *  Comparing with {@link ConsensualLedgerTransaction}:
 *  <ul>
 *  <li>It does not have access to the deserialized details.</li>
 *  <li>It has direct access to the signatures.</li>
 *  <li>It does not require a serializer.</li>
 *  </ul>
 *
 * {@link ConsensualSignedTransaction} wraps the wire representation of the transaction. It contains one or more signatures,
 * each one for a public key (including composite keys) that is mentioned inside a transaction state.
 * {@link ConsensualSignedTransaction} is frequently passed around the network and stored. The identity of a transaction is
 * the hash of Merkle root of the wrapped wire representation, therefore if you are storing data keyed by wire
 * representations hash be aware that multiple different {@link ConsensualSignedTransaction}s may map to the same key (and
 * they could be different in important ways, like validity!). The signatures on a {@link ConsensualSignedTransaction}
 * might be invalid or missing: the type does not imply validity.
 * A transaction ID should be the hash of the wrapped wire representation's Merkle tree root.
 * Thus adding or removing a signature does not change it.
 */
@DoNotImplement
@CordaSerializable
public interface ConsensualSignedTransaction {
    /**
     * @return id The ID of the transaction.
     */
    @NotNull
    SecureHash getId();

    /**
     * @return signatures The signatures that have been applied to the transaction.
     */
    @NotNull
    List<DigitalSignatureAndMetadata> getSignatures();

    /**
     * Converts the current {@link ConsensualSignedTransaction} into a {@link ConsensualLedgerTransaction}.
     *
     * @return Returns a {@link ConsensualLedgerTransaction} from the current signed transaction.
     */
    @NotNull
    ConsensualLedgerTransaction toLedgerTransaction();

    /**
     * Sign the current {@link ConsensualSignedTransaction} with the specified key.
     *
     * @param publicKey The private counterpart of the specified public key will be used for signing the
     *      {@link ConsensualSignedTransaction}.
     * @return Returns a new {@link ConsensualSignedTransaction} containing the applied signature.
     */
    @NotNull
    ConsensualSignedTransaction addSignature(@NotNull PublicKey publicKey);

    /**
     * Gets the signing keys for any missing transaction signatures.
     *
     * @return Returns a {@link Set} of {@link PublicKey} representing the signing keys for any missing transaction signatures.
     */
    @NotNull
    Set<PublicKey> getMissingSigningKeys();
}
