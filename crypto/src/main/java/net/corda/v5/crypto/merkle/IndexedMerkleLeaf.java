package net.corda.v5.crypto.merkle;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * [IndexedMerkleLeaf]s are building blocks of [MerkleProof]s.
 * They contain the required information about a particular leaf which is needed for the verification.
 */

@CordaSerializable
public final class IndexedMerkleLeaf {
    private final int index;
    @Nullable
    private final byte[] nonce;
    @NotNull
    private final byte[] leafData;


    public IndexedMerkleLeaf(int index, @Nullable byte[] nonce, @NotNull byte[] leafData) {
        super();
        this.index = index;
        this.nonce = nonce;
        this.leafData = leafData;
    }

    @NotNull
    public String toString() {
        return "Leaf(" + this.index + ")[" + this.leafData.length + " bytes]";
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof IndexedMerkleLeaf)) return false;

        IndexedMerkleLeaf otherLeaf = (IndexedMerkleLeaf) other;

        if (index != otherLeaf.index) return false;
        if (nonce != otherLeaf.nonce) {
            if (otherLeaf.nonce == null) return false;
            if (!Arrays.equals(nonce, otherLeaf.nonce)) return false;
        } else if (otherLeaf.nonce != null) return false;
        if (!Arrays.equals(leafData, otherLeaf.leafData)) return false;

        return true;
    }

    public int hashCode() {
        int result = index;
        if (nonce != null) result = 31 * result + Arrays.hashCode(nonce);
        result = 31 * result + Arrays.hashCode(leafData);
        return result;
    }

}