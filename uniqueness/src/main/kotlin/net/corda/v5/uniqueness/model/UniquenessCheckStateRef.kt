package net.corda.v5.uniqueness.model

import net.corda.v5.crypto.SecureHash

/**
 * Representation of a state reference. This type might also be attached to some of the error types
 * and returned through the client service. This representation does not depend on any specific ledger
 * model and is agnostic to both the message bus API and any DB schema that may be used to persist data
 * by the backing store.
 */
data class UniquenessCheckStateRef(
    val txHash: SecureHash,
    val stateIndex: Int
) {
    companion object {
        fun fromString(stateRefString: String): UniquenessCheckStateRef {
            return UniquenessCheckStateRef(
                SecureHash.create(stateRefString.substringBefore(":")),
                stateRefString.substringAfter(":").toInt()
            )
        }
    }
    override fun toString() = "${txHash}:${stateIndex}"
}
