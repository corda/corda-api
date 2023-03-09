package net.corda.v5.ledger.utxo.query;

import org.jetbrains.annotations.NotNull;

/**
 * A builder that is used to create and build a {@link VaultNamedQuery}.
 * <p>
 * The register method is called when the build is finished so that the query is stored and can be fetched and executed
 * later on.
 */
public interface VaultNamedQueryBuilder {
    /**
     * Sets the where clause of the named query
     * @param json The json query representation
     * @return A builder instance with the where clause set
     */
    @NotNull
    VaultNamedQueryBuilder whereJson(@NotNull String json);

    /**
     * Sets the filter function of the named query
     * @param filter A filter object
     * @return A builder instance with the filter function set
     */
    @NotNull VaultNamedQueryBuilder filter(@NotNull VaultNamedQueryFilter<?> filter);

    /**
     * Sets the mapper function of the named query
     * @param mapper A transformer object
     * @return A builder instance with the mapper function set
     */
    @NotNull VaultNamedQueryBuilder map(@NotNull VaultNamedQueryTransformer<?, ?> mapper);

    /**
     * Sets the collector function of the named query
     * @param collector A collector object
     * @return A builder instance with the collector function set
     */
    @NotNull VaultNamedQueryBuilder collect(@NotNull VaultNamedQueryCollector<?, ?> collector);

    /**
     * Registers the named query object to the named query registry.
     * This always needs to be called in order to "finalize" the query creation.
     */
    void register();
}
