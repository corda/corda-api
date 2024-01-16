package net.corda.v5.ledger.utxo.query;

import net.corda.v5.ledger.utxo.ContractState;
import net.corda.v5.ledger.utxo.StateAndRef;

/**
 * Representation of a transformer function that will be applied to the result set returned by the named query.
 * It is assumed that the result set will contain {@link Consumable} of {@link StateAndRef} objects.
 * <p>
 * If you want the consumption state revealed to you, you must use this subclass of the
 * {@link VaultNamedQueryTransformer} interface.
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class MyVaultNamedQueryTransformer : VaultNamedQueryConsumableStateAndRefTransformer<ContractState, MyPojo> {
 *
 *     override fun transform(data: Consumable<StateAndRef<ContractState>>, parameters: Map<String, Any>): MyPojo {
 *         return MyPojo(1)
 *     }
 * }
 * }</pre></li>
 * <li>Java:<pre>{@code
 * public class MyVaultNamedQueryTransformer implements VaultNamedQueryConsumableStateAndRefTransformer<ContractState, MyPojo> {
 *
 *     @Override
 *     public MyPojo transform(Consumable<StateAndRef<ContractState>> state, Map<String, Object> parameters) {
 *         return new MyPojo(1);
 *     }
 * }
 * }</pre></li></ul>
 *
 * @param <T> Type of the {@link StateAndRef} results returned from the database.
 * @param <R> Type that the original data is transformed into.
 */
public interface VaultNamedQueryConsumableStateAndRefTransformer<T extends ContractState, R>
        extends VaultNamedQueryTransformer<Consumable<StateAndRef<T>>, R> {
}