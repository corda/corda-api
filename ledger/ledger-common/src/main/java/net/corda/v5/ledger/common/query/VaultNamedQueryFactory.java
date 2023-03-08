package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

/**
 * The main interface that needs implemented by the named ledger queries. Implementing this interface will define how
 * the named query will be built and stored.
 * Example usage:
 * class MyVaultNamedQueryFactory : VaultNamedQueryFactory {
 *
 *     override fun create(vaultNamedQueryBuilderFactory: VaultNamedQueryBuilderFactory) {
 *         vaultNamedQueryBuilderFactory.create("FIND_WITH_CORDA_COLUMNS")
 *             .whereJson(
 *                 "WHERE custom ->> 'TestUtxoState.testField' = :testField " +
 *                         "AND custom ->> 'Corda.participants' IN :participants " +
 *                         "AND custom ? :contractStateType "
 *                         "AND custom ->> 'Corda.createdTimestamp' > :created_timestamp"
 *             )
 *             .filter(MyVaultNamedQueryFilter())
 *             .map(MyVaultNamedQueryTransformer())
 *             .collect(MyVaultNamedQueryCollector())
 *             .register()
 *
 *     }
 * }
 */
public interface VaultNamedQueryFactory {
    @Suspendable
    void create(@NotNull VaultNamedQueryBuilderFactory vaultNamedQueryBuilderFactory);
}
