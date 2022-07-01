package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SecureHash

/**
 * [MerkleTreeFactory] creates [MerkleTree]s, [MerkleProof]s and [MerkleTreeHashDigestProvider].
 */
interface MerkleTreeFactory {
    /**
     * Creates a [MerkleTree]
     *
     * @param leaves The leaves of the tree.
     * @param digestProvider Merkle Tree Hash digest provider used to construct the tree's node and leaf hashes.
     *
     * @return A new [MerkleTree] instance.
     */
    fun createTree(
        leaves: List<ByteArray>,
        digestProvider: MerkleTreeHashDigestProvider
    ) : MerkleTree

    /**
     * Recreates a [MerkleProof] from its data content.
     * Use [net.corda.v5.crypto.merkle.MerkleTree.createAuditProof] to create a proof for a set of leaves for an
     * existing [MerkleTree].
     * Use this function to recreate the [MerkleProof] when the [MerkleTree] is not available.
     *
     * @param treeSize Number of leaves in the whole tree
     * @param leaves Leaf items whose inclusion is proved by the proof.
     * @param hashes The helper hashes needed to reconstruct the whole tree.
     *
     * @return A new [MerkleProof] instance.
     */
    fun createProof(
        treeSize: Int,
        leaves: List<IndexedMerkleLeaf>,
        hashes: List<SecureHash>
    ) : MerkleProof

    /**
     * Creates a [MerkleTreeHashDigestProvider].
     *
     * @param merkleTreeHashDigestProviderName name of the Hash Digest Provider class
     * @param digestAlgorithmName name of the base Hash algorithm
     * @param options Hash Digest provider specific options
     *
     * @return A new [MerkleTreeHashDigestProvider] instance.
     */
    fun createHashDigestProvider(
        merkleTreeHashDigestProviderName: String,
        digestAlgorithmName: DigestAlgorithmName,
        options: HashMap<String, Any>? = null,
    ) : MerkleTreeHashDigestProvider
}