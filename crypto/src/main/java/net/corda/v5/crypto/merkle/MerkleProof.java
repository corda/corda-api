package net.corda.v5.crypto.merkle;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * {@link MerkleProof}s can be used to verify if some specific data is part of a {@link MerkleTree}.
 * <p>
 * Use {@link MerkleTree#createAuditProof} to create a proof for a set of leaves for an
 * existing {@link MerkleTree}.
 * Construct a {@link MerkleProof} from its (<code>treeSize</code>, <code>leaves</code>, <code>hashes</code>) 
 * when you want to <code>verify</code>} if the leaves to be checked are part of a {@link MerkleTree} with the specific root.
 */
@CordaSerializable
@DoNotImplement
public interface MerkleProof {

    /**
     * Returns the type of the proof.
     */
    @NotNull
    MerkleProofType getProofType();

    /**
     * Returns the size of the {@link MerkleTree} out of which this proof was created.
     */
    int getTreeSize();

    /**
     * Returns disclosed plain data of a {@link MerkleTree} which are to be verified that
     * they are indeed part of a {@code MerkleTree}.
     */
    List<IndexedMerkleLeaf> getLeaves();

    /**
     * Returns the hashed intermediate node data supplementing the plain data.
     */
    List<SecureHash> getHashes();

    /**
     * Checks if the {@link MerkleProof} has been generated from a {@link MerkleTree} with the given root.
     *
     * @param root   The root of the tree to be verified.
     * @param digest The tree's digest.
     * @return Result of the verification.
     */
    boolean verify(@NotNull SecureHash root, @NotNull MerkleTreeHashDigest digest);

    /**
     * Rebuilds the {@link MerkleTree} from the {@link MerkleProof} and returns its root {@link SecureHash}.
     *
     * @param digest The tree's digest.
     * @return Root hash of the tree.
     * @throws MerkleProofRebuildFailureException if the calculation of the root hash failed.
     */
    SecureHash calculateRoot(@NotNull MerkleTreeHashDigest digest);

    List<LeveledHash> calculateLeveledHashes(@NotNull MerkleTreeHashDigest digest);

    class LeveledHash {

        private final int level;
        private final int index;
        private final SecureHash hash;

        public LeveledHash(int level, int index, SecureHash hash) {
            this.level = level;
            this.index = index;
            this.hash = hash;
        }

        public int getLevel() {
            return level;
        }

        public int getIndex() {
            return index;
        }

        public SecureHash getHash() {
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LeveledHash that = (LeveledHash) o;
            return level == that.level && index == that.index && Objects.equals(hash, that.hash);
        }

        @Override
        public int hashCode() {
            return Objects.hash(level, index, hash);
        }

        @Override
        public String toString() {
            return "LeveledHash{" +
                    "level=" + level +
                    ", index=" + index +
                    ", hash=" + hash +
                    '}';
        }
    }
}
