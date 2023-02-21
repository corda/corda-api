package net.corda.v5.crypto;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;


/**
 * A simple data class for passing keys and weights into CompositeKeyGenerator
 *
 * @param node A public key
 * @param weight The weight for that key, must be greater than zero.
 */
public final class CompositeKeyNodeAndWeight {
    @NotNull
    private final PublicKey node;
    private final int weight;
    
    public CompositeKeyNodeAndWeight(@NotNull PublicKey node, @NotNull int weight) {
        super();
        this.node = node;
        this.weight=weight;
        if (this.weight <= 0) throw new IllegalArgumentException("A non-positive weight was detected. Member info: " + this);
    }

    public CompositeKeyNodeAndWeight(@NotNull PublicKey node) { this(node, 1); }
    
    @NotNull public final PublicKey getNode() { return this.node; }
    public final int getWeight() { return this.weight; }
}
