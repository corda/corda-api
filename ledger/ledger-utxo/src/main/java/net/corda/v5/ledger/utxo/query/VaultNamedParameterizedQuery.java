package net.corda.v5.ledger.utxo.query;

import net.corda.v5.application.persistence.ParameterizedQuery;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Representation of a named ledger query.
 *
 * @param <R> The type of results this query will fetch.
 */
public interface VaultNamedParameterizedQuery<R> extends ParameterizedQuery<R> {

    /**
     * Sets the timestamp limit for the query. This will influence which results are returned.
     * @param timestampLimit The timestamp limit the query should enforce.
     * @return A {@link VaultNamedParameterizedQuery} object with the timestamp limit set.
     */
    @NotNull
    VaultNamedParameterizedQuery<R> setCreatedTimestampLimit(@NotNull Instant timestampLimit);
}
