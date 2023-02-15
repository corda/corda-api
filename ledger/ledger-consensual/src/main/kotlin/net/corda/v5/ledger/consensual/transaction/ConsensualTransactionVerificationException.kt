package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.exceptions.CordaRuntimeException
import net.corda.v5.crypto.SecureHash

/**
 * Indicates that some aspect of the transaction named by [transactionId] violates some rules.
 *
 * @property transactionId the Merkle root hash (identifier) of the transaction that failed verification.
 */
open class ConsensualTransactionVerificationException(
    val transactionId: SecureHash,
    message: String,
    cause: Throwable?
) :
    CordaRuntimeException(message, cause)
