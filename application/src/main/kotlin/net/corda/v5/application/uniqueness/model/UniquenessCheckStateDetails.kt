package net.corda.v5.application.uniqueness.model

import net.corda.v5.crypto.SecureHash

/**
 * TODO Rewrite KDocs
 */
data class UniquenessCheckStateDetails(
    val stateRef: UniquenessCheckStateRef,
    val consumingTxId: SecureHash?
)
