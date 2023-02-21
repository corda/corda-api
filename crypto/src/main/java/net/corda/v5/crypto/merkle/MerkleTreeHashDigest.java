package net.corda.v5.crypto.merkle;

import net.corda.v5.crypto.DigestAlgorithmName;
import org.jetbrains.annotations.NotNull;

/**
 * Different use cases require different [MerkleTree] calculations.
 * [MerkleTreeHashDigest]s let us specify such implementations.
 *
 *  @property digestAlgorithmName Specifies the digest algorithm.
 */

public interface MerkleTreeHashDigest {
    @NotNull
    DigestAlgorithmName getDigestAlgorithmName();
}
