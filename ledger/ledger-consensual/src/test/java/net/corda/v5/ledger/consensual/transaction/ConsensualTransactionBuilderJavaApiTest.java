package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.ledger.consensual.ConsensualState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsensualTransactionBuilderJavaApiTest {
    private final ConsensualTransactionBuilder consensualTransactionBuilder = mock(ConsensualTransactionBuilder.class);

    @Test
    public void getStates() {
        ConsensualState consensualState = mock(ConsensualState.class);
        when(consensualTransactionBuilder.getStates()).thenReturn(List.of(consensualState));

        List<ConsensualState> result = consensualTransactionBuilder.getStates();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(List.of(consensualState));
        verify(consensualTransactionBuilder, times(1)).getStates();
    }

    @Test
    public void withStatesOne() {
        final ConsensualState consensualState = mock(ConsensualState.class);
        final ConsensualTransactionBuilder mockTransactionBuilder = mock(ConsensualTransactionBuilder.class);
        when(consensualTransactionBuilder.withStates(consensualState)).thenReturn(mockTransactionBuilder);

        final ConsensualTransactionBuilder result = consensualTransactionBuilder.withStates(consensualState);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(mockTransactionBuilder);
        verify(consensualTransactionBuilder, times(1)).withStates(consensualState);
    }

    @Test
    public void withStatesTwo() {
        final ConsensualState consensualState1 = mock(ConsensualState.class);
        final ConsensualState consensualState2 = mock(ConsensualState.class);
        final ConsensualTransactionBuilder mockTransactionBuilder = mock(ConsensualTransactionBuilder.class);
        when(consensualTransactionBuilder.withStates(consensualState1, consensualState2))
                .thenReturn(mockTransactionBuilder);

        final ConsensualTransactionBuilder result = consensualTransactionBuilder
                .withStates(consensualState1, consensualState2);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(mockTransactionBuilder);
        verify(consensualTransactionBuilder, times(1))
                .withStates(consensualState1, consensualState2);
    }


    @Test
    public void signInitial() {
        final PublicKey publicKey = mock(PublicKey.class);
        final ConsensualSignedTransaction mockSignedTransaction = mock(ConsensualSignedTransaction.class);
        when(consensualTransactionBuilder.signInitial(publicKey)).thenReturn(mockSignedTransaction);

        final ConsensualSignedTransaction result = consensualTransactionBuilder.signInitial(publicKey);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(mockSignedTransaction);
        verify(consensualTransactionBuilder, times(1)).signInitial(publicKey);
    }
}