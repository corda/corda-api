package net.corda.v5.crypto.failures

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class CryptoThrottlingExceptionTests {
    @Test
    fun `Should return default linear backoff with message only`() {
        val e =  CryptoThrottlingException("Something wrong.")
        assertNull(e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(200L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(200L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(-1L, backOff)
    }

    @Test
    fun `Should return customized backoff with message only`() {
        val e =  CryptoThrottlingException("Something wrong.", 100, 200)
        assertNull(e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(100L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(200L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(-1L, backOff)
    }

    @Test
    fun `Should return default linear backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException("Something wrong.", cause)
        assertSame(cause, e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(200L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(200L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(-1L, backOff)
    }

    @Test
    fun `Should return customized backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException("Something wrong.", cause, 100, 200)
        assertSame(cause, e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(100L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(200L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(-1L, backOff)
    }

    @Test
    fun `Should return default exponential backoff with message only`() {
        val e =  CryptoThrottlingException.createExponential("Something wrong.")
        assertNull(e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(1000L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(2000L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(4000L, backOff)
        backOff = e.getBackoff(4, backOff)
        assertEquals(8000L, backOff)
        backOff = e.getBackoff(5, backOff)
        assertEquals(16000L, backOff)
        backOff = e.getBackoff(6, backOff)
        assertEquals(-1, backOff)
    }

    @Test
    fun `Should return customized exponential backoff with message only`() {
        val e =  CryptoThrottlingException.createExponential("Something wrong.", 4, 3000)
        assertNull(e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(3000L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(6000L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(12000L, backOff)
        backOff = e.getBackoff(4, backOff)
        assertEquals(-1, backOff)
    }

    @Test
    fun `Should return default exponential backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException.createExponential("Something wrong.", cause)
        assertSame(cause, e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(1000L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(2000L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(4000L, backOff)
        backOff = e.getBackoff(4, backOff)
        assertEquals(8000L, backOff)
        backOff = e.getBackoff(5, backOff)
        assertEquals(16000L, backOff)
        backOff = e.getBackoff(6, backOff)
        assertEquals(-1, backOff)
    }

    @Test
    fun `Should return customized exponential backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException.createExponential("Something wrong.", cause, 4, 3000)
        assertSame(cause, e.cause)
        var backOff = 0L
        backOff = e.getBackoff(1, backOff)
        assertEquals(3000L, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(6000L, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(12000L, backOff)
        backOff = e.getBackoff(4, backOff)
        assertEquals(-1, backOff)
    }
}