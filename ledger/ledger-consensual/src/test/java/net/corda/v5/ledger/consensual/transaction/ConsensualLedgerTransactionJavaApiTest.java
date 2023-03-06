package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.crypto.DigestAlgorithmName;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.consensual.ConsensualState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsensualLedgerTransactionJavaApiTest {
    private final ConsensualLedgerTransaction consensualLedgerTransaction = mock(ConsensualLedgerTransaction.class);
    private final DigestService digestService = mock(DigestService.class);

    @Test
    public void getId() {
        SecureHash secureHash = digestService.hash("123".getBytes(), DigestAlgorithmName.SHA2_256);
        when(consensualLedgerTransaction.getId()).thenReturn(secureHash);

        SecureHash result = consensualLedgerTransaction.getId();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(secureHash);
        verify(consensualLedgerTransaction, times(1)).getId();
    }

    @Test
    public void getRequiredSignatories() {
        PublicKey publicKey = mock(PublicKey.class);
        when(consensualLedgerTransaction.getRequiredSignatories()).thenReturn(Set.of(publicKey));

        Set<PublicKey> result = consensualLedgerTransaction.getRequiredSignatories();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(Set.of(publicKey));
        verify(consensualLedgerTransaction, times(1)).getRequiredSignatories();
    }

    @Test
    public void getTimestamp() {
        Instant timestamp = Instant.now();
        when(consensualLedgerTransaction.getTimestamp()).thenReturn(timestamp);

        Instant result = consensualLedgerTransaction.getTimestamp();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(timestamp);
        verify(consensualLedgerTransaction, times(1)).getTimestamp();
    }

    @Test
    public void getStates() {
        ConsensualState consensualState = mock(ConsensualState.class);
        when(consensualLedgerTransaction.getStates()).thenReturn(List.of(consensualState));

        List<ConsensualState> result = consensualLedgerTransaction.getStates();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(List.of(consensualState));
        verify(consensualLedgerTransaction, times(1)).getStates();
    }
}
