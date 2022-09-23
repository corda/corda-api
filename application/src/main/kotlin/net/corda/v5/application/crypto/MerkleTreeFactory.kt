package net.corda.v5.application.crypto

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.merkle.MerkleTree
import net.corda.v5.crypto.merkle.MerkleTreeHashDigestFactory

/**
 * [MerkleTreeFactory] creates [MerkleTree]s and [MerkleTreeHashDigestFactory]s.
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
        digestProvider: MerkleTreeHashDigestFactory
    ) : MerkleTree

    /**
     * Creates a [MerkleTreeHashDigestFactory].
     *
     * @param merkleTreeHashDigestProviderName name of the Hash Digest Provider class
     * @param digestAlgorithmName name of the base Hash algorithm
     * @param options Hash Digest provider specific options
     *
     * @return A new [MerkleTreeHashDigestFactory] instance.
     */
    @Suspendable
    fun createHashDigestProvider(
        merkleTreeHashDigestProviderName: String,
        digestAlgorithmName: DigestAlgorithmName,
        options: Map<String, Any> = emptyMap(),
    ) : MerkleTreeHashDigestFactory
}
