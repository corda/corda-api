package net.corda.v5.crypto.merkle;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * [MerkleProof]s can be used to verify if some specific data is part of a [MerkleTree].
 * <p>
 * Use [net.corda.v5.crypto.merkle.MerkleTree.createAuditProof] to create a proof for a set of leaves for an
 * existing [MerkleTree].
 * Construct a [MerkleProof] from its ([treeSize], [leaves], [hashes]) when you want to [verify] if the leaves
 * to be checked are part of a [MerkleTree] with the specific root.
 */
@CordaSerializable
public interface MerkleProof {

    /**
     * Get the type of the proof
     *
     * @return Type of the proof
     */
    @NotNull
    MerkleProofType getProofType();

    int getTreeSize();

    List<IndexedMerkleLeaf> getLeaves();

    List<SecureHash> getHashes();

    /**
     * Checks if the [MerkleProof] has been generated from a [MerkleTree] with the given [root].
     *
     * @param root   The root of the tree to be verified.
     * @param digest The tree's digest.
     * @return Result of the verification.
     */
    boolean verify(@NotNull SecureHash root, @NotNull MerkleTreeHashDigest digest);

    /**
     * Rebuilds the [MerkleTree] from the [MerkleProof] and returns its root [SecureHash].
     *
     * @param digest The tree's digest.
     * @return Root hash of the tree.
     * @throws MerkleProofRebuildFailureException if the calculation of the root hash failed.
     */
    SecureHash calculateRoot(@NotNull MerkleTreeHashDigest digest);
}
