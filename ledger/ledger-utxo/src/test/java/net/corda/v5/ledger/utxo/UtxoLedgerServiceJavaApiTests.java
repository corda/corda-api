package net.corda.v5.ledger.utxo;

import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransactionVerifier;
import net.corda.v5.ledger.utxo.transaction.UtxoTransactionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class UtxoLedgerServiceJavaApiTests extends AbstractMockTestHarness {

    @Test
    public void getTransactionBuilderShouldReturnTheExpectedResult() {
        UtxoTransactionBuilder value = utxoLedgerService.getTransactionBuilder();
        Assertions.assertEquals(utxoTransactionBuilder, value);
    }

    @Test
    public void resolvePluralShouldReturnTheExpectedResult() {
        List<StateAndRef<ContractState>> value = utxoLedgerService.resolve(List.of(stateRef));
        Assertions.assertEquals(List.of(contractStateAndRef), value);
    }

    @Test
    public void resolveSingularShouldReturnTheExpectedResult() {
        StateAndRef<ContractState> value = utxoLedgerService.resolve(stateRef);
        Assertions.assertEquals(contractStateAndRef, value);
    }

    @Test
    public void findSignedTransaction() {
        SecureHash secureHash = new SecureHash("SHA-256", "123".getBytes());
        UtxoSignedTransaction utxoSignedTransaction = mock(UtxoSignedTransaction.class);
        when(utxoLedgerService.findSignedTransaction(secureHash)).thenReturn(utxoSignedTransaction);

        UtxoSignedTransaction result = utxoLedgerService.findSignedTransaction(secureHash);

        org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(utxoSignedTransaction);
        verify(utxoLedgerService, times(1)).findSignedTransaction(secureHash);
    }

    @Test
    public void findLedgerTransaction() {
        SecureHash secureHash = new SecureHash("SHA-256", "123".getBytes());
        UtxoLedgerTransaction utxoLedgerTransaction = mock(UtxoLedgerTransaction.class);
        when(utxoLedgerService.findLedgerTransaction(secureHash)).thenReturn(utxoLedgerTransaction);

        UtxoLedgerTransaction result = utxoLedgerService.findLedgerTransaction(secureHash);

        org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(utxoLedgerTransaction);
        verify(utxoLedgerService, times(1)).findLedgerTransaction(secureHash);
    }

    @Test
    public void finality() {
        UtxoSignedTransaction UtxoSignedTransactionIn = mock(UtxoSignedTransaction.class);
        UtxoSignedTransaction UtxoSignedTransactionOut = mock(UtxoSignedTransaction.class);
        List<FlowSession> flowSessions = Arrays.asList(mock(FlowSession.class), mock(FlowSession.class));
        when(utxoLedgerService.finality(UtxoSignedTransactionIn, flowSessions)).thenReturn(UtxoSignedTransactionOut);

        UtxoSignedTransaction result = utxoLedgerService.finality(UtxoSignedTransactionIn, flowSessions);

        org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(UtxoSignedTransactionOut);
        verify(utxoLedgerService, times(1)).finality(UtxoSignedTransactionIn, flowSessions);
    }

    @Test
    public void receiveFinality() {
        UtxoSignedTransaction UtxoSignedTransaction = mock(UtxoSignedTransaction.class);
        FlowSession flowSession = mock(FlowSession.class);
        UtxoSignedTransactionVerifier UtxoSignedTransactionVerifier = mock(UtxoSignedTransactionVerifier.class);
        when(utxoLedgerService.receiveFinality(flowSession, UtxoSignedTransactionVerifier)).thenReturn(UtxoSignedTransaction);

        UtxoSignedTransaction result = utxoLedgerService.receiveFinality(flowSession, UtxoSignedTransactionVerifier);

        org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(UtxoSignedTransaction);
        verify(utxoLedgerService, times(1)).receiveFinality(flowSession, UtxoSignedTransactionVerifier);
    }
}
