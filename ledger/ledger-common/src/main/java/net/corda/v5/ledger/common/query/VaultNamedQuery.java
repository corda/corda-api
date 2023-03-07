package net.corda.v5.ledger.common.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface representing a named ledger query. A named ledger query must have a name associated with it,
 * the rest of the fields are optional.
 */
public interface VaultNamedQuery {

    /**
     * @return The name of the named ledger query
     */
    @NotNull
    String getName();

    /**
     * @return The query string that is associated with this named ledger query. This field is optional.
     */
    @Nullable
    String getJsonString();

    /**
     * @return The extra filter that is applied to the result set when executing this query. This field is optional.
     */
    @Nullable
    VaultNamedQueryFilter<?> getFilter();

    /**
     * @return The extra mapping function that is applied to the result set when executing this query. This field is optional.
     */
    @Nullable
    VaultNamedQueryTransformer<?, ?> getMapper();

    /**
     * @return The extra collector function that is applied to the result set when executing this query. This field is optional.
     */
    @Nullable
    VaultNamedQueryCollector<?, ?> getCollector();
}
