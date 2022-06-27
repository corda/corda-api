package net.corda.v5.crypto.failures

import net.corda.v5.crypto.failures.CryptoRetryStrategy.Companion.createLinearBackoff
import net.corda.v5.crypto.failures.CryptoRetryStrategy.Companion.createBackoff
import net.corda.v5.crypto.failures.CryptoRetryStrategy.Companion.createExponentialBackoff
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CryptoRetryStrategyTests {
    @Test
    fun `Should return default linear backoff`() {
        val strategy =  createLinearBackoff()
        var backoff = 0L
        backoff = strategy.getBackoff(1, backoff)
        assertEquals(200L, backoff)
        backoff = strategy.getBackoff(2, backoff)
        assertEquals(200L, backoff)
        backoff = strategy.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return customizes backoff`() {
        val strategy =  createBackoff(3, 100, 200)
        var backoff = 0L
        backoff = strategy.getBackoff(1, backoff)
        assertEquals(100L, backoff)
        backoff = strategy.getBackoff(2, backoff)
        assertEquals(200L, backoff)
        backoff = strategy.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return customizes backoff with repeating value`() {
        val strategy = createBackoff(3, 300)
        var backoff = 0L
        backoff = strategy.getBackoff(1, backoff)
        assertEquals(300L, backoff)
        backoff = strategy.getBackoff(2, backoff)
        assertEquals(300L, backoff)
        backoff = strategy.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return default exponential backoff`() {
        val strategy = createExponentialBackoff()
        var backoff = 0L
        backoff = strategy.getBackoff(1, backoff)
        assertEquals(1000L, backoff)
        backoff = strategy.getBackoff(2, backoff)
        assertEquals(2000L, backoff)
        backoff = strategy.getBackoff(3, backoff)
        assertEquals(4000L, backoff)
        backoff = strategy.getBackoff(4, backoff)
        assertEquals(8000L, backoff)
        backoff = strategy.getBackoff(5, backoff)
        assertEquals(16000L, backoff)
        backoff = strategy.getBackoff(6, backoff)
        assertEquals(-1, backoff)
    }

    @Test
    fun `Should return customizes exponential backoff`() {
        val strategy = createExponentialBackoff(4, 3000L)
        var backoff = 0L
        backoff = strategy.getBackoff(1, backoff)
        assertEquals(3000L, backoff)
        backoff = strategy.getBackoff(2, backoff)
        assertEquals(6000L, backoff)
        backoff = strategy.getBackoff(3, backoff)
        assertEquals(12000L, backoff)
        backoff = strategy.getBackoff(4, backoff)
        assertEquals(-1, backoff)
    }
}