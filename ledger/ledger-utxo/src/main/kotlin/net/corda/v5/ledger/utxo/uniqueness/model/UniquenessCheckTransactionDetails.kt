package net.corda.v5.ledger.utxo.uniqueness.model

import net.corda.v5.crypto.SecureHash

/**
 * Representation of the transaction details that must be persisted by the uniqueness checker,
 * used by the uniqueness checker and backing store.
 */
interface UniquenessCheckTransactionDetails {
    val txId: SecureHash
    val result: UniquenessCheckResult
}