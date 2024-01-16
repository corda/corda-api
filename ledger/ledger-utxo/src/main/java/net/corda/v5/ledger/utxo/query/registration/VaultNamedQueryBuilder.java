package net.corda.v5.ledger.utxo.query.registration;

import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.query.*;
import org.jetbrains.annotations.NotNull;

/**
 * A builder that is used to create and build a vault named query.
 * <p>
 * The register method is called when the build is finished so that the query is stored and can be fetched and executed
 * later on.
 */
public interface VaultNamedQueryBuilder extends VaultNamedQueryBuilderBase {

    /**
     * Append another column expression to the sort order of the returned results.
     * <p>
     * Calling this on a builder with an existing ordering will append this as a lower priority
     * ordering, so the order will be by the first and then the second call.
     * <p>
     * As soon as a sort order is specified, the pages will purely be based on offset and limit and may
     * return less rows than the limit once filtering has been applied.
     *
     * @param columnExpression the name of a column (qualified) or column expression (such as JSON path)
     * @param ascending        whether the ordering should be ascending or descending for this column expression
     * @param nullsFirst       whether nulls should come before non-null data (not SQL Server)
     * @return A builder instance with the order by appended.
     * @since 5.2
     */
    @NotNull
    VaultNamedQueryBuilder orderBy(String columnExpression, boolean ascending, boolean nullsFirst);

    /**
     * Have the query return only vault states that are unconsumed.  This is a convenience
     * function to save specifying the necessary where clause and filter, and is compatible with
     * paging.
     * <p>
     * Subsequently adding a filter that implements {@link VaultNamedQueryConsumableStateAndRefFilter} will
     * undo the filter part of this call. This filter may see states that are consumed after the created
     * timestamp limit of the queries, but they will correctly be identified through the
     * {@link Consumable} interface.
     *
     * @return a builder instance with these additional where clause and filter set.
     */
    VaultNamedQueryBuilder onlyUnconsumed();

    /**
     * Sets the where clause of the named query.
     * <p>
     * Vault named queries defined with {@link #whereJson(String)} return {@link StateAndRef}s when executed.
     *
     * @param query The JSON query representation.
     * @return A builder instance with the where clause set.
     */
    @NotNull
    VaultNamedQueryBuilder whereJson(@NotNull String query);

    /**
     * Sets the filter function of the named query.
     * <p>
     * Note that filtering will always be applied before mapping.
     *
     * @param filter A filter object.
     *
     * @return A builder instance with the filter function set.
     */
    @NotNull VaultNamedQueryBuilder filter(@NotNull VaultNamedQueryFilter<?> filter);

    /**
     * Sets the mapper function of the named query.
     * <p>
     * Note that the transformation will always be applied after filtering.
     *
     * @param transformer A transformer object.
     *
     * @return A builder instance with the mapper function set.
     */
    @NotNull VaultNamedQueryBuilder map(@NotNull VaultNamedQueryTransformer<?, ?> transformer);

    /**
     * Sets the collector function of the named query.
     *
     * @param collector A collector object.
     *
     * @return A builder instance with the collector function set.
     */
    @NotNull
    VaultNamedQueryBuilderCollected collect(@NotNull VaultNamedQueryCollector<?, ?> collector);
}
