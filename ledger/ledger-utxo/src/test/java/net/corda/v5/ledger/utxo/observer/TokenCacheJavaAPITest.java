package net.corda.v5.ledger.utxo.observer;

import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.utxo.ContractState;
import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.TransactionState;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class TokenCacheJavaAPITest {

    @Test
    public void getTokenPoolKey() {
        TransactionState<ContractState> transactionState = Mockito.mock(TransactionState.class);
        ContractState contractState = Mockito.mock(ContractState.class);
        StateAndRef<ContractState> stateAndRef = Mockito.mock(StateAndRef.class);

        Mockito.when(transactionState.getContractState()).thenReturn(contractState);

        final ExampleTokenStateObserver observer = new ExampleTokenStateObserver();

        UtxoTokenPoolKey result = observer.getTokenPoolKey(stateAndRef);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void callOnProduced() {
        TransactionState<ContractState> transactionState = Mockito.mock(TransactionState.class);
        ContractState contractState = Mockito.mock(ContractState.class);
        StateAndRef<ContractState> stateAndRef = Mockito.mock(StateAndRef.class);

        Mockito.when(transactionState.getContractState()).thenReturn(contractState);

        final ExampleTokenStateObserver observer = new ExampleTokenStateObserver();

        UtxoToken result = observer.onProduced(stateAndRef);

        Assertions.assertThat(result).isNotNull();
    }

    public class ExampleTokenStateObserver implements UtxoLedgerTokenStateObserver<ContractState> {

        @NotNull
        @Override
        public Class<ContractState> getStateType() {
            return ContractState.class;
        }

        @NotNull
        @Override
        public UtxoToken onProduced(@NotNull StateAndRef<? extends ContractState> stateAndRef) {
            return new UtxoToken(
                    new BigDecimal(0),
                    new UtxoTokenFilterFields()
            );
        }

        @NotNull
        @Override
        public UtxoTokenPoolKey getTokenPoolKey(@NotNull StateAndRef<? extends ContractState> stateAndRef) {
            return new UtxoTokenPoolKey(new SecureHash("A", new byte[10]), "sym");
        }
    }
}
