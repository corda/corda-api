package net.corda.v5.ledger.common.transaction

import net.corda.v5.base.exceptions.CordaRuntimeException
import net.corda.v5.crypto.SecureHash

/**
 * Indicates a problem related to signatures of the transaction named by [transactionId].
 *
 * @property transactionId the Merkle root hash (identifier) of the transaction that failed verification.
 */
open class TransactionSignatureException(val transactionId: SecureHash, message: String, cause: Throwable?) :
    CordaRuntimeException(message, cause)