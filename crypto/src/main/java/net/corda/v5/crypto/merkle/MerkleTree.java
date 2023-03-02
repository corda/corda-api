package net.corda.v5.crypto.merkle;

import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * [MerkleTree]s are cryptographic data structures which can be used to create a short fingerprint of a larger
 * structured dataset.
 * From [MerkleTree]s, we can create [MerkleProof]s which let us prove that some particular data is part of the whole
 * tree without revealing the remaining data.
 */
public interface MerkleTree {

    /**
     * Return the input data elements. Usually something deterministically serialized.
     *
     * @return The input data elements.
     */
    List<byte[]> getLeaves();

    /**
     * Return the [MerkleTreeHashDigest] used to construct the tree's node and leaf hashes.
     *
     * @return The digest
     */
    MerkleTreeHashDigest getDigest();

    /**
     * Return the root element of the tree which is essentially the fingerprint of the whole tree/data set.
     *
     * @return The root element hash
     */
    SecureHash getRoot();

    /**
     * Creates a [MerkleProof] for a set of leaves.
     *
     * @param leafIndices whose leaf's inclusion is to be proven by the proof.
     * @return [MerkleProof] for the input leaves.
     */
    MerkleProof createAuditProof(@NotNull List<Integer> leafIndices);
}
