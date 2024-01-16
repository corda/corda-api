package net.corda.v5.ledger.utxo.query;

import net.corda.v5.ledger.utxo.ContractState;
import net.corda.v5.ledger.utxo.StateAndRef;

/**
 * Representation of an in-memory filter function that will be applied to the result set that was returned by the named
 * query. The result set in this case contains {@link Consumable} of {@link StateAndRef}s.
 * <p>
 * If you want the consumption state revealed to you, you must use this subclass of the
 * {@link VaultNamedQueryFilter} interface.
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class MyVaultNamedQueryFilter : VaultNamedQueryConsumableStateAndRefFilter<ContractState> {
 *
 *     override fun filter(state: Consumable<StateAndRef<ContractState>>, parameters: Map<String, Any>): Boolean {
 *         return !state.isConsumed
 *     }
 * }
 * }</pre></li>
 * <li>Java:<pre>{@code
 * public class MyVaultNamedQueryFilter implements VaultNamedQueryConsumableStateAndRefFilter<ContractState> {
 *
 *     @Override
 *     public Boolean filter(Consumable<StateAndRef<ContractState>> state, Map<String, Object> parameters) {
 *         return !state.isConsumed();
 *     }
 * }
 * }</pre></li></ul>
 *
 * @param <T> Type of the {@link StateAndRef} results returned from the database.
 */
public interface VaultNamedQueryConsumableStateAndRefFilter<T extends ContractState>
        extends VaultNamedQueryFilter<Consumable<StateAndRef<T>>> {
}
