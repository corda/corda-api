package net.corda.v5.ledger.utxo.observer;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.crypto.DigestAlgorithmName;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.utxo.ContractState;
import net.corda.v5.ledger.utxo.TransactionState;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

public class TokenCacheJavaAPITest {

    private final DigestService digestService = mock(DigestService.class);

    @Test
    public void callOnProduced() {
        TransactionState<ContractState> transactionState = Mockito.mock(TransactionState.class);
        ContractState contractState = Mockito.mock(ContractState.class);

        final ExampleTokenStateObserver observer = new ExampleTokenStateObserver();

        UtxoToken result = observer.onCommit(contractState);

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
        public UtxoToken onCommit(@NotNull ContractState state) {
            return new UtxoToken(
                    //TODO: check with previous SecureHash("A", new byte[10])
                    new UtxoTokenPoolKey(digestService.hash(new byte[1], DigestAlgorithmName.SHA2_256), "sym"),
                    new BigDecimal(0),
                    new UtxoTokenFilterFields()
            );
        }
    }
}
