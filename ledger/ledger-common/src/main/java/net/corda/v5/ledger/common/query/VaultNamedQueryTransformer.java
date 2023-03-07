package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Representation of a transformer function that will be applied to the result set returned by the named query.
 * @param <S> Type of the state returned from the database
 * @param <T> Type that the original state is transformed into
 */
public interface VaultNamedQueryTransformer<S, T> {
    @Suspendable
    @NotNull
    T transform(@NotNull S state, @NotNull Map<String, Object> parameters);
}
