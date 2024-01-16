package net.corda.v5.ledger.utxo.query;

import java.time.Instant;

/**
 * Represents the UTXO consumption, or not, of another object.
 *
 * @param <R> The type of the object that might be consumed. Typically, this would
 *            be a {@link net.corda.v5.ledger.utxo.StateAndRef}.
 */
public interface Consumable<R> {
    /**
     * The consumed-ness of the other object.
     *
     * @return true if consumed.
     */
    boolean isConsumed();

    /**
     * @return The timestamp at which the other object was consumed, or null if not
     * consumed.
     */
    Instant getConsumptionTimestamp();

    /**
     * @return The other object they may be consumed, or not.
     */
    R getConsumable();
}
