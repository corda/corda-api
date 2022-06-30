package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SecureHash

interface MerkleTreeFactory {
    fun createTree(
        leaves: List<ByteArray>,
        digestProvider: MerkleTreeHashDigestProvider
    ) : MerkleTree

    fun createProof(
        treeSize: Int,
        leaves: List<IndexedMerkleLeaf>,
        hashes: List<SecureHash>
    ) : MerkleProof

    fun createHashDigestProvider(
        merkleTreeHashDigestProviderName: String,
        digestAlgorithmName: DigestAlgorithmName,
        options: HashMap<String, Any>? = null,
    ) : MerkleTreeHashDigestProvider
}