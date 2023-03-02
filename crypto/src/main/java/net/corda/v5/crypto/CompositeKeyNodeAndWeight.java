package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;


/**
 * A simple data class for passing keys and weights into CompositeKeyGenerator
 */
public final class CompositeKeyNodeAndWeight {
    @NotNull
    private final PublicKey node;
    private final int weight;

    /**
     * Construct a key
     *
     * @param node   A public key
     * @param weight The weight for that key, must be greater than zero.
     */
    public CompositeKeyNodeAndWeight(@NotNull PublicKey node, int weight) {
        super();
        this.node = node;
        this.weight = weight;
        if (this.weight <= 0)
            throw new IllegalArgumentException("A non-positive weight was detected. Member info: " + this);
    }
    
    public CompositeKeyNodeAndWeight(@NotNull PublicKey node) {
        this(node, 1);
    }

    @NotNull
    public PublicKey getNode() {
        return this.node;
    }

    public int getWeight() {
        return this.weight;
    }

    @NotNull
    public String toString() {
        return String.format("[%s, %d]", this.node, weight);
    }

    public int hashCode() {
        return node.hashCode() + 31 * weight;
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CompositeKeyNodeAndWeight)) return false;
        CompositeKeyNodeAndWeight otherKey = (CompositeKeyNodeAndWeight) other;
        if (!otherKey.node.equals(node)) return false;
        return otherKey.weight == weight;
    }
}
