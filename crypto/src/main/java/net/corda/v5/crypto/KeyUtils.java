package net.corda.v5.crypto;

import java.security.PublicKey;
import java.util.Collections;
import java.util.Set;

public class KeyUtils {

    private KeyUtils() {
        // this class is never constructed; it exists for the static methods and data only
    }

    /**
     * Constant specifying the maximum number of key lookup by id items.
     */
    public static int KEY_LOOKUP_INPUT_ITEMS_LIMIT = 20;


    /**
     * Checks whether any of the given [keys] match a leaf on the [CompositeKey] tree or a single [PublicKey].
     * <p>
     * <i>Note that this function checks against leaves, which cannot be of type [CompositeKey]. Due to that, if any of the
     * [otherKeys] is a [CompositeKey], this function will not find a match.</i>
     */
    public static boolean isKeyInSet(PublicKey key, Iterable<PublicKey> otherKeys) {
        if (key instanceof CompositeKey) {
            CompositeKey compositeKey = (CompositeKey) key;
            Set<PublicKey> currentKeys = compositeKey == null ? Collections.singleton(key) : compositeKey.getLeafKeys();
            for (PublicKey myKey : otherKeys) {
                for (PublicKey otherKey : currentKeys) {
                    if (otherKey == myKey) return true;
                }
            }
        } else {
            for (PublicKey otherKey : otherKeys) {
                if (otherKey == key) return true;
            }
        }
        return false;
    }

    /**
     * Return true if [otherKey] fulfils the requirements of [firstKey]
     */
    public static boolean isKeyFulfilledBy(PublicKey firstKey, PublicKey otherKey) {
        return isKeyFulfilledBy(firstKey,
                Collections.singleton(otherKey));
    }

    /**
     * Return true if [otherKeys] fulfil the requirements of [firstKey].
     */
    public static boolean isKeyFulfilledBy(PublicKey firstKey, Iterable<PublicKey> otherKeys) {
        if (firstKey instanceof CompositeKey) {
            CompositeKey firstKeyComposite = (CompositeKey) firstKey;
            return firstKeyComposite.isFulfilledBy(otherKeys);
        }
        for (PublicKey otherKey : otherKeys) {
            if (otherKey.equals(firstKey)) return true;
        }
        return false;
    }
}
