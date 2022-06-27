package net.corda.v5.crypto.failures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoRetryStrategyJavaApiTest {
    @Test
    public void canBeUsedAsLambda() {
        var backoff = callee(1, 1000, ((attempt, currentBackoffMillis) -> {
            if(attempt == 1) {
                return currentBackoffMillis;
            } else {
                return -1;
            }
        }));
        assertEquals(1000, backoff);
    }

    @Test
    public void canCreateExponentialBackoff() {
        var defaultBackoff = CryptoRetryStrategy.createExponentialBackoff();
        assertEquals(5, defaultBackoff.length);
        var backoff = CryptoRetryStrategy.createExponentialBackoff(2, 100);
        assertEquals(1, backoff.length);
    }

    @Test
    public void canCreateLinearBackoff() {
        var defaultBackoff = CryptoRetryStrategy.createLinearBackoff();
        assertEquals(2, defaultBackoff.length);
        var backoff = CryptoRetryStrategy.createBackoff(2, 100);
        assertEquals(1, backoff.length);
    }

    private long callee(int attempt, long current, CryptoRetryStrategy strategy) {
        return strategy.getBackoff(attempt, current);
    }
}
