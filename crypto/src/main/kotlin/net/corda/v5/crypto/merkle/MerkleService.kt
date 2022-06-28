package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SecureHash

interface MerkleService {
    fun createMerkleTree(
        leaves: List<ByteArray>,
        digestProvider: MerkleTreeHashDigestProvider
    ) : MerkleTree

    fun createMerkleProof(
        treeSize: Int,
        leaves: List<IndexedMerkleLeaf>,
        hashes: List<SecureHash>
    ) : MerkleProof

    fun createMerkleTreeHashDigestProvider(
        merkleTreeHashDigestProviderName: String,
        digestAlgorithmName: DigestAlgorithmName,
        options: HashMap<String, Any>? = null,
    ) : MerkleTreeHashDigestProvider
}