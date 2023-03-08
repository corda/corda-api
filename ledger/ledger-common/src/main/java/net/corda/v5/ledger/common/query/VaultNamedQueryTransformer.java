package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Representation of a transformer function that will be applied to the result set returned by the named query.
 * @param <S> Type of the state returned from the database
 * @param <T> Type that the original state is transformed into
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class MyVaultNamedQueryTransformer : VaultNamedQueryTransformer<ContractState, MyPojo> {
 *
 *     override fun transform(state: ContractState, parameters: Map<String, Any>): MyPojo {
 *         return MyPojo(1)
 *     }
 * }
 * }</pre></li>
 * <li>Java:<pre>{@code
 * public class MyVaultNamedQueryTransformer implements VaultNamedQueryTransformer<ContractState, MyPojo> {
 *
 *     @Override
 *     public MyPojo transform(ContractState state, Map<String, Object> parameters) {
 *         return new MyPojo(1);
 *     }
 * }
 * }</pre></li></ul>
 */
public interface VaultNamedQueryTransformer<S, T> {
    @Suspendable
    @NotNull
    T transform(@NotNull S state, @NotNull Map<String, Object> parameters);
}
