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
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(200L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(200L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return customized backoff with message only`() {
        val e =  CryptoThrottlingException("Something wrong.", 100, 200)
        assertNull(e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(100L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(200L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return default linear backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException("Something wrong.", cause)
        assertSame(cause, e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(200L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(200L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return customized backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException("Something wrong.", cause, 100, 200)
        assertSame(cause, e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(100L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(200L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(-1L, backoff)
    }

    @Test
    fun `Should return default exponential backoff with message only`() {
        val e =  CryptoThrottlingException.createExponential("Something wrong.")
        assertNull(e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(1000L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(2000L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(4000L, backoff)
        backoff = e.getBackoff(4, backoff)
        assertEquals(8000L, backoff)
        backoff = e.getBackoff(5, backoff)
        assertEquals(16000L, backoff)
        backoff = e.getBackoff(6, backoff)
        assertEquals(-1, backoff)
    }

    @Test
    fun `Should return customized exponential backoff with message only`() {
        val e =  CryptoThrottlingException.createExponential("Something wrong.", 4, 3000)
        assertNull(e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(3000L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(6000L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(12000L, backoff)
        backoff = e.getBackoff(4, backoff)
        assertEquals(-1, backoff)
    }

    @Test
    fun `Should return default exponential backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException.createExponential("Something wrong.", cause)
        assertSame(cause, e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(1000L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(2000L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(4000L, backoff)
        backoff = e.getBackoff(4, backoff)
        assertEquals(8000L, backoff)
        backoff = e.getBackoff(5, backoff)
        assertEquals(16000L, backoff)
        backoff = e.getBackoff(6, backoff)
        assertEquals(-1, backoff)
    }

    @Test
    fun `Should return customized exponential backoff with message and cause`() {
        val cause = RuntimeException()
        val e =  CryptoThrottlingException.createExponential("Something wrong.", cause, 4, 3000)
        assertSame(cause, e.cause)
        var backoff = 0L
        backoff = e.getBackoff(1, backoff)
        assertEquals(3000L, backoff)
        backoff = e.getBackoff(2, backoff)
        assertEquals(6000L, backoff)
        backoff = e.getBackoff(3, backoff)
        assertEquals(12000L, backoff)
        backoff = e.getBackoff(4, backoff)
        assertEquals(-1, backoff)
    }
}