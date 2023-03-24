package net.corda.v5.ledger.utxo.query;

import org.jetbrains.annotations.NotNull;

/**
 * A builder that is used to create and build a vault named query.
 * <p>
 * The register method is called when the build is finished so that the query is stored and can be fetched and executed
 * later on.
 */
public interface VaultNamedQueryBuilder extends VaultNamedQueryBuilderBase {
    /**
     * Sets the where clause of the named query.
     * @param json The JSON query representation.
     *
     * @return A builder instance with the where clause set.
     */
    @NotNull
    VaultNamedQueryBuilder whereJson(@NotNull String json);

    /**
     * Sets the filter function of the named query.
     * Please note that filtering function will always be applied before mapping.
     *
     * @param filter A filter object.
     *
     * @return A builder instance with the filter function set.
     */
    @NotNull VaultNamedQueryBuilder filter(@NotNull VaultNamedQueryFilter<?> filter);

    /**
     * Sets the mapper function of the named query.
     * Please note that mapping function will always be applied after filtering.
     *
     * @param mapper A transformer object.
     *
     * @return A builder instance with the mapper function set.
     */
    @NotNull VaultNamedQueryBuilder map(@NotNull VaultNamedQueryTransformer<?, ?> mapper);

    /**
     * Sets the collector function of the named query.
     * @param collector A collector object.
     *
     * @return A builder instance with the collector function set.
     */
    @NotNull
    VaultNamedQueryBuilderCollected collect(@NotNull VaultNamedQueryCollector<?, ?> collector);
}
