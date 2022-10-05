package net.corda.v5.ledger.common.transaction;

import net.corda.v5.application.crypto.DigitalSignatureMetadata;
import net.corda.v5.crypto.DigitalSignature;
import net.corda.v5.crypto.merkle.MerkleProof;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.time.Instant;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class TransactionSignatureJavaApiTest {
    private final PublicKey mockPublicKey = mock(PublicKey.class);
    private final DigitalSignature.WithKey signature = new DigitalSignature.WithKey(mockPublicKey, "0".getBytes(), Map.of());
    private final DigitalSignatureMetadata signatureMetadata = new DigitalSignatureMetadata(Instant.now(), Map.of());
    private final MerkleProof mockMerkleProof = mock(MerkleProof.class);
    private final TransactionSignature transactionSignature = new TransactionSignature(signature, signatureMetadata, mockMerkleProof);

    @Test
    public void constructorTwoArgs() {
        TransactionSignature transactionSignatureTwo = new TransactionSignature(signature, signatureMetadata);
        Assertions.assertThat(transactionSignatureTwo).isInstanceOf(TransactionSignature.class);
    }

    @Test
    public void constructorThreeArgs() {
        TransactionSignature transactionSignatureThree = new TransactionSignature(signature, signatureMetadata, mockMerkleProof);
        Assertions.assertThat(transactionSignatureThree).isInstanceOf(TransactionSignature.class);
    }

    @Test
    public void getSignature() {
        DigitalSignature result = transactionSignature.getSignature();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(signature);
    }

    @Test
    public void getMetadata() {
        DigitalSignatureMetadata result = transactionSignature.getMetadata();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(signatureMetadata);
    }

    @Test
    public void getBatchProof() {
        MerkleProof result = transactionSignature.getBatchProof();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(mockMerkleProof);
    }

    @Test
    public void getBy() {
        PublicKey result = transactionSignature.getBy();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(mockPublicKey);
    }
}