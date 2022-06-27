package net.corda.v5.crypto.failures

import net.corda.v5.crypto.failures.CryptoRetryStrategy.Companion.createLinearBackoff
import net.corda.v5.crypto.failures.CryptoRetryStrategy.Companion.createBackoff
import net.corda.v5.crypto.failures.CryptoRetryStrategy.Companion.createExponentialBackoff
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CryptoRetryStrategyTests {
    @Test
    fun `Should return default linear backoff`() {
        val backoff =  createLinearBackoff()
        assertEquals(2, backoff.size)
        assertEquals(200L, backoff[0])
        assertEquals(200L, backoff[1])
    }

    @Test
    fun `Should return customizes backoff`() {
        val backoff =  createBackoff(5, 100, 200, 300, 400)
        assertEquals(4, backoff.size)
        assertEquals(100L, backoff[0])
        assertEquals(200L, backoff[1])
        assertEquals(300L, backoff[2])
        assertEquals(400L, backoff[3])
    }

    @Test
    fun `Should return customizes backoff with repeating value`() {
        val backoff =  createBackoff(5, 300)
        assertEquals(4, backoff.size)
        assertEquals(300L, backoff[0])
        assertEquals(300L, backoff[1])
        assertEquals(300L, backoff[2])
        assertEquals(300L, backoff[3])
    }

    @Test
    fun `Should return customizes backoff with repeating values`() {
        val backoff =  createBackoff(5, 100, 200)
        assertEquals(4, backoff.size)
        assertEquals(100L, backoff[0])
        assertEquals(200L, backoff[1])
        assertEquals(200L, backoff[2])
        assertEquals(200L, backoff[3])
    }

    @Test
    fun `Should return default exponential backoff`() {
        val backoff = createExponentialBackoff()
        assertEquals(5, backoff.size)
        assertEquals(1000L, backoff[0])
        assertEquals(2000L, backoff[1])
        assertEquals(4000L, backoff[2])
        assertEquals(8000L, backoff[3])
        assertEquals(16000L, backoff[4])
    }

    @Test
    fun `Should return customizes exponential backoff`() {
        val backoff = createExponentialBackoff(4, 3000L)
        assertEquals(3, backoff.size)
        assertEquals(3000L, backoff[0])
        assertEquals(6000L, backoff[1])
        assertEquals(12000L, backoff[2])
    }
}