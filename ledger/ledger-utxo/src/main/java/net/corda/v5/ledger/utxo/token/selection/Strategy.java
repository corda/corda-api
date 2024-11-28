package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.annotations.CordaSerializable;

/**
 * Token selection strategy. RANDOM is the default.
 */
@CordaSerializable
public enum Strategy {
    /**
     * Choose tokens in transaction ID order. As this field is not a sequential value, it has the effect of a pseudo
     * random order.
     * <p>
     * This is the default selection strategy.
     */
    RANDOM,

    /**
     * Choose tokens in priority, then transaction ID order.
     * <p>
     * Lower priority values are treated as higher priority. Equal priority values fall back to transaction ID ordering,
     * behaving the same as RANDOM strategy. Null priority values are sorted to the end, making them the lowest possible
     * priority.
     */
    PRIORITY
}
