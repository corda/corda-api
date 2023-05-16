package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.Collections;
import java.util.Set;

/**
 * Helper functions for key look up in a set of keys. These functions also work when the key to look up in the
 * set of keys is {@link CompositeKey}.
 */
public final class KeyUtils {
    private KeyUtils() {}

    /**
     * Checks whether <code>key</code> has any intersection with the keys in <code>otherKeys</code>, 
     * recursing into <code>key</code> (the first argument) if it is a composite key. Does not match
     * any composite keys in <code>otherKeys</code>.
     * <p/>
     * For simple non-compound public keys, this operation simply checks if the first argument occurs in the
     * second argument. If <code>key</code> is a compound key, the outcome is whether any of its leaf keys
     * are in <code>otherKeys</code>.
     * {@link PublicKey}.
     * <p/>
     * This function checks against compound key tree leaves, which by definition are not {@link CompositeKey}.
     * That is why if any of the <code>otherKeys</code> is a {@link CompositeKey}, this function will not 
     * find a match, though composite keys in the <code>otherKeys</code> set is not regarded as an error; they
     * are silently ignored.
     * <p/>
     * The notion of a key being in a set is about equality, which is not the same as whether one key is 
     * fulfilled by another key. For example, a {@link CompositeKey} C could be defined to have threshold 2 and:
     * <p/>
     * <ul>
     *     <li> Public key X with weight 1 </li>
     *     <li> Public key Y with weight 1 </li>
     *     <li> Public key Z with weight 2 </li>
     * </ul>
     * Then we would find that <code>isKeyInSet(C, X)</code> is true, but X would not fulfill C since C is fulfilled by
     * X and Y together but not X on its. However, <code>isKeyInSet(C, Z)</code> is true, and Z fulfills C by itself.
     * 
     * @param key       The key being looked for
     * @param otherKeys The keys to look the {@code key} in
     * @return True if <code>key</code> is in otherKeys
     */
    public static boolean isKeyInSet(@NotNull PublicKey key, @NotNull Set<PublicKey> otherKeys) {
        if (key instanceof CompositeKey) {
            CompositeKey compositeKey = (CompositeKey) key;
            Set<PublicKey> leafKeys = compositeKey.getLeafKeys();
            leafKeys.retainAll(otherKeys);
            return !leafKeys.isEmpty();
        } else {
            return otherKeys.contains(key);
        }
    }

    /**
     * Return true if a set of keys fulfil the requirements of a specific key.
     * <p/>
     * Fulfilment of a {@link CompositeKey} as <code>firstKey</code> key is checked by delegating to the <code>isFulfilledBy</code> method of that
     * compound key. It is a question of whether all the keys which match the compound keys in total have enough weight
     * to reach the threshold of the primary key. 
     * <p/>
     * In contrast, if this is called with <code>firstKey</code> being a simple public key, the test is whether
     * <code>firstKey</code> is equal to any of the keys in <code>otherKeys</code>. Since a simple public key
     * is never considered equal to a {@link CompositeKey} we know if <code>firstKey</code> is not composite, then
     * it will not be considered fulfilled by any {@link CompositeKey} in <code>otherKeys</code>. Such cases are
     * not considered errors, so we silently ignore {@link CompositeKey}s in <code>otherKeys</code>.
     *<p/>
     * If you know you have a {@link CompositeKey} in your hand, it would be simpler to call its <code>isFulfilledBy()</code>
     * method directly. This function is intended as a utility for when you have some kind of public key, and which to 
     * check fulfilment against a set of keys, without having to handle simple and composite keys separately (that is, this is
     * polymorphic).
     * 
     * @param key  The key to be checked whether it is being fulfilled by {@code otherKeys}
     * @param otherKeys The keys against which the {@code key} is being checked for fulfillment
     */
    public static boolean isKeyFulfilledBy(@NotNull PublicKey key, @NotNull Set<PublicKey> otherKeys) {
        if (key instanceof CompositeKey) {
            CompositeKey firstKeyComposite = (CompositeKey) key;
            return firstKeyComposite.isFulfilledBy(otherKeys);
        }
        return otherKeys.contains(key);
    }

    /**
     * Return true if one key fulfills the requirements of another key. See the previous variant; this overload
     * is the same as calling as the variant that takes an iterable set of other keys with <code>otherKey<code>
     * as a single element iterable. 
     * <p>
     * Since we do not define composite keys as acceptable on the second argument of this function, this relation
     * is not reflexive, not symmetric and not transitive. 
     *
     * @param key The key to be checked whether it is being fulfilled by {@code otherKey}.
     * @param otherKey The key against which the {@code key} is being checked for fulfilment.
     */
    public static boolean isKeyFulfilledBy(@NotNull PublicKey key, @NotNull PublicKey otherKey) {
        return isKeyFulfilledBy(key,
                Collections.singleton(otherKey));
    }
}
