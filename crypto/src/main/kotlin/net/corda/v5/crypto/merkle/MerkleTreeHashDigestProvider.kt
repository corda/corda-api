package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.SecureHash

interface MerkleTreeHashDigestProvider {
    fun leafNonce(index: Int): ByteArray?
    fun leafHash(index: Int, nonce: ByteArray?, bytes: ByteArray): SecureHash
    fun nodeHash(depth: Int, left: SecureHash, right: SecureHash): SecureHash
}