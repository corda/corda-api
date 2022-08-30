package net.corda.v5.uniqueness.model

import net.corda.v5.crypto.SecureHash

/**
 * Representation of the state details that must be persisted by the uniqueness checker.
 * This type might also be attached to some of the error types and returned through the
 * client service. This representation is agnostic to both the message bus API and any
 * DB schema that may be used to persist data by the backing store.
 */
data class UniquenessCheckStateDetails(
    val stateRef: UniquenessCheckStateRef,
    val consumingTxId: SecureHash?
)
