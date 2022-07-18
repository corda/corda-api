package net.corda.v5.crypto.merkle

import net.corda.v5.base.annotations.CordaSerializable

/**
 * The hash digest provider name.
 *
 * @property name The name of the hash digest provider to be used for the instance.
 */
@CordaSerializable
class MerkleTreeHashDigestProviderName(val name: String) {
    init {
        require(name.isNotBlank()) { "Hash digest provider name unavailable or not specified" }
    }

    companion object {
        @JvmField
        val DEFAULT: MerkleTreeHashDigestProviderName = MerkleTreeHashDigestProviderName("DefaultHashDigestProvider")

        @JvmField
        val NONCE: MerkleTreeHashDigestProviderName = MerkleTreeHashDigestProviderName("NonceHashDigestProvider")

        @JvmField
        val NONCE_VERIFY: MerkleTreeHashDigestProviderName = MerkleTreeHashDigestProviderName("NonceHashDigestProviderVerify")

        @JvmField
        val NONCE_SIZE_ONLY_VERIFY: MerkleTreeHashDigestProviderName = MerkleTreeHashDigestProviderName("NonceHashDigestProviderSizeOnlyVerify")

        @JvmField
        val TWEAKABLE: MerkleTreeHashDigestProviderName = MerkleTreeHashDigestProviderName("TweakableHashDigestProvider")
    }

    override fun toString(): String = name

    override fun hashCode(): Int = name.uppercase().hashCode()

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(this === other) return true
        val otherName = (other as? MerkleTreeHashDigestProviderName)?.name ?: return false
        return name.equals(otherName, true)
    }
}