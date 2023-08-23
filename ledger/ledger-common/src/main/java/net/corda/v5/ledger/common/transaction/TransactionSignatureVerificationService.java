package net.corda.v5.ledger.common.transaction;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.application.crypto.DigitalSignatureVerificationService;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.crypto.merkle.MerkleProof;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

/**
 * TransactionSignatureVerificationService can be used to verify transaction signatures.
 * It supports both single and batch signatures.
 * It can be used in both flows and contracts.
 */
@DoNotImplement
public interface TransactionSignatureVerificationService {
    /**
     * Verifies a signature against a transaction.
     * The underlying verification service signals the verification failures with different exceptions.
     * {@link DigitalSignatureVerificationService}
     *
     * @param transaction           The original transaction.
     * @param signatureWithMetadata The signature to be verified.
     * @param publicKey             The public key to verify against. It must match with signatureWithMetadata's keyId.
     * @throws RuntimeException if the signature could not be verified.
     */
    void verifySignature(
            @NotNull final TransactionWithMetadata transaction,
            @NotNull final DigitalSignatureAndMetadata signatureWithMetadata,
            @NotNull final PublicKey publicKey
    );

    /**
     * Verifies a signature against a SecureHash.
     * The underlying verification service signals the verification failures with different exceptions.
     * {@link DigitalSignatureVerificationService}
     *
     * @param secureHash            The original secureHash.
     * @param signatureWithMetadata The signature to be verified.
     * @param publicKey             The public key to verify against. It must match with signatureWithMetadata's keyId.
     * @throws RuntimeException if the signature could not be verified.
     */
    void verifySignature(
            @NotNull final SecureHash secureHash,
            @NotNull final DigitalSignatureAndMetadata signatureWithMetadata,
            @NotNull final PublicKey publicKey
    );

}

