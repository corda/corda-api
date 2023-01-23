package net.corda.v5.ledger.common.transaction

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.crypto.DigitalSignatureVerificationService
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash
import net.corda.v5.crypto.merkle.MerkleProof
import java.security.PublicKey

/**
 * TransactionSignatureService can be used to sign and verify transaction signatures.
 * It supports both single and batch signatures.
 */

interface TransactionSignatureService {
    /**
     * Signs a transaction id with all the available keys.
     *
     * @param transactionId The transaction id to be signed.
     * @param publicKeys The keys which should be tried to sign with.
     *
     * @returns Resulting signatures.
     *
     * @throws TransactionNoAvailableKeysException If none of the keys are available.
     */
    @Suspendable
    fun sign(transactionId: SecureHash, publicKeys: Iterable<PublicKey>): List<DigitalSignatureAndMetadata>

    /**
     * Signs a list of transactions with each the available keys.
     * It creates one batch signature for each available keys.
     * Then returns the signatures for each transaction with a [MerkleProof] to prove that they are included in the batch.
     *
     * @param transactions The transactions to be signed.
     * @param publicKeys The keys which should be tried to sign with.
     *
     * @returns Resulting signatures.
     *
     * @throws TransactionNoAvailableKeysException If none of the keys are available.
     */
    @Suspendable
    fun sign(
        transactions: List<TransactionWithMetadata>,
        publicKeys: Iterable<PublicKey>
    ): List<List<DigitalSignatureAndMetadata>>

    /**
     * Verifies a signature against a transaction.
     * The underlying verification service signals the verification failures with different exceptions.
     * [DigitalSignatureVerificationService]
     *
     * @param transaction The original transaction.
     * @param signatureWithMetadata The signature to be verified.
     *
     */
    fun verifySignature(transaction: TransactionWithMetadata, signatureWithMetadata: DigitalSignatureAndMetadata)
}