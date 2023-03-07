package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface representing a named query storage.
 */
public interface VaultNamedQueryRegistry {
    /**
     * @param name The name of the named query that needs to be accessed
     * @return The named query object associated with the given name or null if it was not found in the registry
     */
    @Suspendable
    @Nullable VaultNamedQuery getQuery(@NotNull String name);

    // TODO This should not be on the public API, need to understand OSGi magic to make it internal
    @Suspendable
    void registerQuery(@NotNull VaultNamedQuery name);
}
