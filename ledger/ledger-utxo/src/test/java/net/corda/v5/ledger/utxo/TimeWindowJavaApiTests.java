package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

public class TimeWindowJavaApiTests extends Mock {

    @Test
    public void getFromShouldReturnTheCorrectValue() {
        Instant value = timeWindow.getFrom();
        Assertions.assertEquals(from, value);
    }

    @Test
    public void getUntilShouldReturnTheCorrectValue() {
        Instant value = timeWindow.getUntil();
        Assertions.assertEquals(until, value);
    }

    @Test
    public void getMidpointShouldReturnTheCorrectValue() {
        Instant value = timeWindow.getMidpoint();
        Assertions.assertEquals(midpoint, value);
    }

    @Test
    public void getDurationShouldReturnTheCorrectValue() {
        Duration value = timeWindow.getDuration();
        Assertions.assertEquals(duration, value);
    }
}
