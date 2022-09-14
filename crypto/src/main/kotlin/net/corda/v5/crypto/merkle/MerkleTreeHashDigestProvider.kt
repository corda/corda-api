package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SecureHash

/**
 * Different use cases require different [MerkleTree] calculations.
 * [MerkleTreeHashDigestProvider]s let us specify the
 *  - Leaf Nonce
 *  - Leaf Hash
 *  - Node Hash calculation methods
 *  - Base Digest Algorithm.
 */

interface MerkleTreeHashDigestProvider {
    /**
     * @property digestAlgorithmName Specifies the digest algorithm.
     */
    val digestAlgorithmName: DigestAlgorithmName

    /**
     * Calculates the nonce for a leaf.
     * @param index the leaf's index.
     */
    fun leafNonce(index: Int): ByteArray?

    /**
     * Calculates the hash of a leaf.
     * @param index the leaf's index.
     * @param nonce the leaf's nonce.
     * @param bytes the leaf's content bytes.
     */
    fun leafHash(index: Int, nonce: ByteArray?, bytes: ByteArray): SecureHash

    /**
     * Calculates the hash of a node.
     * @param depth Depth of the node.
     * @param left [SecureHash] of the left child of the node.
     * @param right [SecureHash] of the right child of the node.
     */
    fun nodeHash(depth: Int, left: SecureHash, right: SecureHash): SecureHash
}

/**
 * Special Digest provider for supporting size proofs.
 */
interface MerkleTreeHashDigestProviderWithSizeProofSupport : MerkleTreeHashDigestProvider {
    /**
     * Returns a size proof.
     * @param leaves the tree's leaves.
     */
    fun getSizeProof(leaves: List<ByteArray>): MerkleProof
}