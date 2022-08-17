package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.application.crypto.DigitalSignatureMetadata;
import net.corda.v5.application.serialization.SerializationService;
import net.corda.v5.crypto.DigitalSignature;
import net.corda.v5.crypto.SecureHash;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsensualSignedTransactionJavaApiTest {
    private final ConsensualSignedTransaction consensualSignedTransaction = mock(ConsensualSignedTransaction.class);
    private final DigitalSignature.WithKey signature = new DigitalSignature.WithKey(mock(PublicKey.class), "0".getBytes(), Map.of());
    private final DigitalSignatureMetadata signatureMetadata = new DigitalSignatureMetadata(Instant.now(), Map.of());
    private final DigitalSignatureAndMetadata signatureWithMetaData = new DigitalSignatureAndMetadata(signature, signatureMetadata);

    @Test
    public void getId() {
        SecureHash secureHash = new SecureHash("SHA-256", "123".getBytes());
        when(consensualSignedTransaction.getId()).thenReturn(secureHash);

        SecureHash result = consensualSignedTransaction.getId();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(secureHash);
        verify(consensualSignedTransaction, times(1)).getId();
    }

    @Test
    public void getSignatures() {
        when(consensualSignedTransaction.getSignatures()).thenReturn(List.of(signatureWithMetaData));

        List<DigitalSignatureAndMetadata> result = consensualSignedTransaction.getSignatures();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(List.of(signatureWithMetaData));
        verify(consensualSignedTransaction, times(1)).getSignatures();
    }

    @Test
    public void toLedgerTransaction() {
        final SerializationService mockSerializationService = mock(SerializationService.class);
        final ConsensualLedgerTransaction consensualLedgerTransaction = mock(ConsensualLedgerTransaction.class);
        when(consensualSignedTransaction.toLedgerTransaction(mockSerializationService)).thenReturn(consensualLedgerTransaction);

        final ConsensualLedgerTransaction result = consensualSignedTransaction.toLedgerTransaction(mockSerializationService);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(consensualLedgerTransaction);
        verify(consensualSignedTransaction, times(1)).toLedgerTransaction(mockSerializationService);
    }

    @Test
    public void addSignature() {
        PublicKey mockPublicKey = mock(PublicKey.class);
        final ConsensualSignedTransaction consensualSignedTransaction = mock(ConsensualSignedTransaction.class);
        when(consensualSignedTransaction.addSignature(mockPublicKey)).thenReturn(consensualSignedTransaction);

        final ConsensualSignedTransaction result = consensualSignedTransaction.addSignature(mockPublicKey);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(consensualSignedTransaction);
        verify(consensualSignedTransaction, times(1)).addSignature(mockPublicKey);
    }

    @Test
    public void getMissingSigningKeys() {
        final SerializationService mockSerializationService = mock(SerializationService.class);
        final Set<PublicKey> publicKeys = Set.of(mock(PublicKey.class));
        when(consensualSignedTransaction.getMissingSigningKeys(mockSerializationService)).thenReturn(publicKeys);

        final Set<PublicKey> result = consensualSignedTransaction.getMissingSigningKeys(mockSerializationService);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(publicKeys);
        verify(consensualSignedTransaction, times(1)).getMissingSigningKeys(mockSerializationService);
    }
}