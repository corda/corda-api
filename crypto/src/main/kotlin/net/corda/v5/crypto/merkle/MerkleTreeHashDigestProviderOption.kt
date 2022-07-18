package net.corda.v5.crypto.merkle

import net.corda.v5.base.annotations.CordaSerializable

/**
 * The hash digest provider option names.
 *
 * @property name The hash digest provider option key string to be used for the instance.
 */
@CordaSerializable
class MerkleTreeHashDigestProviderOption(val name: String) {
    init {
        require(name.isNotBlank()) { "Hash digest provider option key name unavailable or not specified" }
    }

    companion object {
        @JvmField
        val LEAF_PREFIX: MerkleTreeHashDigestProviderOption = MerkleTreeHashDigestProviderOption("leafPrefix")

        @JvmField
        val NODE_PRFIX: MerkleTreeHashDigestProviderOption = MerkleTreeHashDigestProviderOption("nodePrefix")

        @JvmField
        val ENTROPY: MerkleTreeHashDigestProviderOption = MerkleTreeHashDigestProviderOption("entropy")
    }

    override fun toString(): String = name

    override fun hashCode(): Int = name.uppercase().hashCode()

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(this === other) return true
        val otherName = (other as? MerkleTreeHashDigestProviderOption)?.name ?: return false
        return name.equals(otherName, true)
    }
}