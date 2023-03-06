package net.corda.v5.ledger.common.transaction;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.crypto.DigestAlgorithmName;
import net.corda.v5.crypto.SecureHash;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TransactionSignatureExceptionJavaApiTest {
    private final Throwable throwable = new Throwable();
    private final DigestService digestService = mock(DigestService.class);
    private final TransactionSignatureException transactionSignatureException = new TransactionSignatureException(
            digestService.hash("123".getBytes(), DigestAlgorithmName.SHA2_256),
            "testMessage",
            throwable);

    @Test
    public void getTransactionId() {
        SecureHash result = transactionSignatureException.getTransactionId();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(digestService.hash("123".getBytes(), DigestAlgorithmName.SHA2_256));
    }

    @Test
    public void getMessage() {
        String result = transactionSignatureException.getMessage();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("testMessage");
    }

    @Test
    public void getCause() {
        Throwable result = transactionSignatureException.getCause();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(throwable);
    }

    @Test
    public void setMessage() {
        transactionSignatureException.setMessage("newTestMessage");
        String result = transactionSignatureException.getMessage();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("newTestMessage");
        Assertions.assertThat(result).isNotEqualTo("testMessage");
    }

    @Test
    public void setCause() {
        Throwable newThrowable = new Throwable();
        transactionSignatureException.setCause(newThrowable);
        Throwable result = transactionSignatureException.getCause();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(newThrowable);
        Assertions.assertThat(result).isNotEqualTo(throwable);
    }
}

