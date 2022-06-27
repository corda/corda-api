package net.corda.v5.crypto.failures;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CryptoRetryStrategyJavaApiTest {
    @Test
    public void canBeUsedAsLambda() {
        var backoff = callee(1, 1000, ((attempt, currentBackoffMillis) -> {
            if (attempt == 1) {
                return currentBackoffMillis;
            } else {
                return -1;
            }
        }));
        assertEquals(1000, backoff);
    }

    @Test
    public void canCreateBackoff() {
        assertNotNull(CryptoRetryStrategy.createExponentialBackoff());
        assertNotNull(CryptoRetryStrategy.createExponentialBackoff(2, 100));
        assertNotNull(CryptoRetryStrategy.createLinearBackoff());
        assertNotNull(CryptoRetryStrategy.createBackoff(2, List.of(100L)));
    }

    @Test
    public void canCreateDefaultImplementationDirectly() {
        new CryptoRetryStrategy.Default(new Long[]{100L, 200L});
    }

    private long callee(int attempt, long current, CryptoRetryStrategy strategy) {
        return strategy.getBackoff(attempt, current);
    }
}
