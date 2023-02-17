package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public final class TimeWindowJavaApiTests extends AbstractMockTestHarness {

    public TimeWindowJavaApiTests() throws IOException {
    }

    @Test
    public void getFromShouldReturnTheExpectedValue() {
        Instant value = timeWindow.getFrom();
        Assertions.assertEquals(minInstant, value);
    }

    @Test
    public void getUntilShouldReturnTheExpectedValue() {
        Instant value = timeWindow.getUntil();
        Assertions.assertEquals(maxInstant, value);
    }

    @Test
    public void containsShouldReturnTheExpectedValue() {
        boolean value = timeWindow.contains(midpoint);
        Assertions.assertTrue(value);
    }
}
