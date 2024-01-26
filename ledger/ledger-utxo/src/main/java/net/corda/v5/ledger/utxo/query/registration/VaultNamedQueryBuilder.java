package net.corda.v5.ledger.utxo.query.registration;

import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.query.VaultNamedQueryCollector;
import net.corda.v5.ledger.utxo.query.VaultNamedQueryFilter;
import net.corda.v5.ledger.utxo.query.VaultNamedQueryTransformer;
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
     * <p>
     * Vault named queries defined with {@link #whereJson(String)} return {@link StateAndRef}s when executed.
     *
     * @param query The JSON query representation.
     *
     * @return A builder instance with the where clause set.
     */
    @NotNull
    VaultNamedQueryBuilder whereJson(@NotNull String query);


    /**
     * Sets an ORDER BY expression. The columnExpression needs to evaluate to a value in the vault database, i.e.
     * either a column name or a JSON expression. The flags string allows to set order by flags, e.g. "ASC",
     * these will be passed through to the database as is, so be careful with using database vendor specific
     * expressions.
     *
     * This function can be called multiple times on one VaultNamedQueryBuilder. The order of calls matters - the first
     * call defines the primary order, and within that the column of the second call will be used etc.
     *
     * @param columnExpression SQL expression that results in a value to order by
     * @param flags ORDER BY flags, e.g. ASC or DESC.
     * @return A builder instance with the ORDER BY field set.
     */
    @NotNull
    VaultNamedQueryBuilder orderBy( @NotNull String columnExpression, String flags );

    /**
     * Sets an ORDER BY expression. The columnExpression needs to evaluate to a value in the vault database, i.e.
     * either a column name or a JSON expression. This is equivalent
     * to calling `oderBy(columnExpression, null);`
     *
     * This function can be called multiple times on one VaultNamedQueryBuilder. The order of calls matters - the first
     * call defines the primary order, and within that the column of the second call will be used etc.
     *
     * @param columnExpression SQL expression that results in a value to order by
     * @return A builder instance with the ORDER BY field set.
     */
    @NotNull
    VaultNamedQueryBuilder orderBy( @NotNull String columnExpression);


    /**
     * Sets a flag to add the appropriate where clause to only select states that are unconsumed as of the time
     * of the initial query. Use this in favour of adding a `visible_states.consumed is NULL` clause to the
     * whereJson field. Note that states returned from such a query can still be consumed if they have
     * been consumed after the initial query was executed.
     *
     * @return A builder instance with the unconsumed only flag set.
     */
    @NotNull
    VaultNamedQueryBuilder selectUnconsumedStatesOnly();

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
