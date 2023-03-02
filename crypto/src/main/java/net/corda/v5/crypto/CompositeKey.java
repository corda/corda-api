package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.Set;

/**
 * A tree data structure that enables the representation of composite public keys, which are used to represent
 * the signing requirements for multi-signature scenarios. A composite key is a list
 * of leaf keys and their contributing weight, and each leaf can be a conventional single key or a composite key.
 * Keys contribute their weight to the total if they are matched by the signature.
 * <p>
 * For complex scenarios, such as *"Both Alice and Bob need to sign to consume a state S"*, we can represent
 * the requirement by creating a tree with a root [CompositeKey], and Alice and Bob as children.
 * The root node would specify *weights* for each of its children and a *threshold* – the minimum total weight required
 * (e.g. the minimum number of child signatures required) to satisfy the tree signature requirement.
 * <p>
 * Using these constructs we can express e.g. 1 of N (OR) or N of N (AND) signature requirements. By nesting we can
 * create multi-level requirements such as *"either the CEO or 3 of 5 of his assistants need to sign"*.
 * <p>
 * Composite key implementations will track the minimum total weight required (in the simple case – the minimum number of child
 * signatures required) to satisfy the subtree rooted at this node.
 */
public interface CompositeKey extends PublicKey {

    /**
     * Constant specifying the maximum number of children keys in the [CompositeKey].
     */
    int COMPOSITE_KEY_CHILDREN_LIMIT = 10;

    /**
     * This method will detect graph cycles in the full composite key structure to protect against infinite loops when
     * traversing the graph and key duplicates in each layer. It also checks if the threshold and weight constraint
     * requirements are met, while it tests for aggregated-weight integer overflow.
     * In practice, this method should be always invoked on the root [CompositeKey], as it inherently
     * validates the child nodes (all the way till the leaves).
     */
    void checkValidity();

    /**
     * Takes single [PublicKey] and checks if [CompositeKey] requirements hold for that key.
     *
     * @param key the public key
     * @return true if the public key is a composite key, false otherwise
     */
    boolean isFulfilledBy(PublicKey key);

    /**
     * Checks if the public keys corresponding to the signatures are matched against the leaves of the composite
     * key tree in question, and the total combined weight of all children is calculated for every intermediary node.
     * If all thresholds are satisfied, the composite key requirement is considered to be met.
     */
    boolean isFulfilledBy(Iterable<PublicKey> keysToCheck);

    /**
     * Set of all leaf keys of that [CompositeKey].
     */
    @NotNull
    Set<PublicKey> getLeafKeys();
}
