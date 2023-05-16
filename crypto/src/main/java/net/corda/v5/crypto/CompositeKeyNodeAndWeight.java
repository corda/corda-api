package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;

/**
 * A simple data class for passing keys and weights into <code>CompositeKeyGenerator</code>.
 */
public final class CompositeKeyNodeAndWeight {
    private final PublicKey node;
    private final int weight;

    /**
     * Creates a new {@code CompositeKeyNodeAndWeight} for
     * the specified key and weight for the key.
     *
     * @param node   A public key.
     * @param weight The weight for that key, must be greater than zero.
     */
    public CompositeKeyNodeAndWeight(@NotNull PublicKey node, int weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("A non-positive weight was detected. Member info: " + this);
        this.node = node;
        this.weight = weight;
    }

    /**
     * Creates a new {@code CompositeKeyNodeAndWeight} for
     * the specified key, defaulting the key's weight to 1.
     *
     * @param node  A public key.
     */
    public CompositeKeyNodeAndWeight(@NotNull PublicKey node) {
        this(node, 1);
    }

    /**
     * @return The key of the {@code CompositeKeyNodeAndWeight}.
     */
    @NotNull
    public PublicKey getNode() {
        return this.node;
    }

    /**
     * @return The weight of the key.
     */
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
