package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

/**
 * [MerkleTreeFactory] creates [MerkleTree]s, [MerkleProof]s and [MerkleTreeHashDigestProvider].
 */
@DoNotImplement
interface MerkleTreeFactory {
    /**
     * Creates a [MerkleTree]
     *
     * @param leaves The leaves of the tree.
     * @param digestProvider Merkle Tree Hash digest provider used to construct the tree's node and leaf hashes.
     *
     * @return A new [MerkleTree] instance.
     */
    @Suspendable
    fun createTree(
        leaves: List<ByteArray>,
        digestProvider: MerkleTreeHashDigestProvider
    ) : MerkleTree

    /**
     * Creates a [MerkleTreeHashDigestProvider].
     *
     * @param merkleTreeHashDigestProviderName name of the Hash Digest Provider class
     * @param digestAlgorithmName name of the base Hash algorithm
     * @param options Hash Digest provider specific options
     *
     * @return A new [MerkleTreeHashDigestProvider] instance.
     */
    @Suspendable
    fun createHashDigestProvider(
        merkleTreeHashDigestProviderName: MerkleTreeHashDigestProviderName,
        digestAlgorithmName: DigestAlgorithmName,
        options: Map<MerkleTreeHashDigestProviderOption, Any> = emptyMap(),
    ) : MerkleTreeHashDigestProvider
}
