package net.corda.v5.application.uniqueness.model

import net.corda.v5.crypto.SecureHash

/**
 * TODO Rewrite KDocs
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
