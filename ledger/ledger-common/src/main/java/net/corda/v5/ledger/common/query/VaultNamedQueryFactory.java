package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

/**
 * The main interface that needs implemented by the named ledger queries. Implementing this interface will define how
 * the named query will be built and stored.
 */
public interface VaultNamedQueryFactory {
    @Suspendable
    void create(@NotNull VaultNamedQueryBuilderFactory vaultNamedQueryBuilderFactory);
}
