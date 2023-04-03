package net.corda.v5.ledger.utxo.query.json;

import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.ledger.utxo.ContractState;
import org.jetbrains.annotations.NotNull;

/**
 * The interface that needs to be implemented in order to create a JSON representation of a state type. Implementing
 * this interface will define how a state can be represented as a JSON string.
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class TestUtxoStateVaultJsonFactory: ContractStateVaultJsonFactory<TestUtxoState> {
 *
 *     override val stateType: Class<TestUtxoState> = TestUtxoState::class.java
 *
 *     override fun append(state: TestUtxoState, jsonMarshallingService: JsonMarshallingService): String {
 *         return jsonMarshallingService.format(TestUtxoStatePojo(state.testField))
 *     }
 * }
 *
 * data class TestUtxoStatePojo(val testField: String)
 * }</pre></li>
 * <li>Java:<pre>{@code
 * public class TestUtxoStateVaultJsonFactory implements ContractStateVaultJsonFactory<TestUtxoState> {
 *
 *     private class TestUtxoStatePojo {
 *         private String testField;
 *
 *         TestUtxoStatePojo(String testField) {
 *             this.testField = testField;
 *         }
 *     }
 *
 *     @Override
 *     public Class<T> getStateType() {
 *         return TestUtxoState.class;
 *     }
 *
 *     @Override
 *     public String append(TestUtxoState state, JsonMarshallingService jsonMarshallingService) {
 *         return jsonMarshallingService.format(new TestUtxoStatePojo(state.getTestField()));
 *     }
 * }
 * }</pre></li></ul>
 *
 * @param <T> The type of the state that this class belongs to. Must be a subtype of {@link ContractState}.
 */
public interface ContractStateVaultJsonFactory<T extends ContractState> {

    /**
     * @return The type of the state this factory belongs to.
     */
    @NotNull Class<T> getStateType();

    /**
     * The function that defines how the given state can be represented as a JSON string.
     *
     * @param state The state object.
     * @param jsonMarshallingService An instance of a {@link JsonMarshallingService} that can be used when creating a
     * JSON representation.
     *
     * @return The JSON representation as a String.
     */
    @NotNull String append(@NotNull T state, @NotNull JsonMarshallingService jsonMarshallingService);
}
