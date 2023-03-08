package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Representation of an in-memory filter function that will be applied to the result set that was returned by the named
 * query.
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class MyVaultNamedQueryFilter : VaultNamedQueryFilter<ContractState> {
 *
 *     override fun filter(state: ContractState, parameters: Map<String, Any>): Boolean {
 *         return true
 *     }
 * }
 * }</pre></li>
 * <li>Java:<pre>{@code
 * public class MyVaultNamedQueryFilter implements VaultNamedQueryFilter<ContractState> {
 *
 *     @Override
 *     public Boolean filter(ContractState state, Map<String, Object> parameters) {
 *         return true;
 *     }
 * }
 * }</pre></li></ul>
 *
 * @param <S> Type of the state that was returned from the database.
 */
public interface VaultNamedQueryFilter<S> {
    @Suspendable
    @NotNull Boolean filter(@NotNull S state, @NotNull Map<String, Object> parameters);
}
