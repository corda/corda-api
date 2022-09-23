package net.corda.v5.crypto.merkle

import net.corda.v5.crypto.DigestAlgorithmName

/**
 * Different use cases require different [MerkleTree] calculations.
 * [MerkleTreeHashDigestFactory]s let us specify such implementations.
 *
 *  @property digestAlgorithmName Specifies the digest algorithm.
 */

interface MerkleTreeHashDigestFactory {
    val digestAlgorithmName: DigestAlgorithmName
}
