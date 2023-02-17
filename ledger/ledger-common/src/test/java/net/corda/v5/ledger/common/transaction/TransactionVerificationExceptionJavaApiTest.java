package net.corda.v5.ledger.common.transaction;

import net.corda.v5.crypto.SecureHash;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionVerificationExceptionJavaApiTest {

    private final Throwable throwable = new Throwable();

    private final TransactionVerificationException exception = new TransactionVerificationException(
            new SecureHash("SHA-256", "123".getBytes()),
            "testMessage",
            throwable
    );

    @Test
    public void getTransactionId() {
        SecureHash result = exception.getTransactionId();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(new SecureHash("SHA-256", "123".getBytes()));
    }

    @Test
    public void getMessage() {
        String result = exception.getMessage();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("testMessage");
    }

    @Test
    public void getCause() {
        Throwable result = exception.getCause();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(throwable);
    }

    @Test
    public void setMessage() {
        exception.setMessage("newTestMessage");
        String result = exception.getMessage();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("newTestMessage");
        Assertions.assertThat(result).isNotEqualTo("testMessage");
    }

    @Test
    public void setCause() {
        Throwable newThrowable = new Throwable();
        exception.setCause(newThrowable);
        Throwable result = exception.getCause();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(newThrowable);
        Assertions.assertThat(result).isNotEqualTo(throwable);
    }
}
