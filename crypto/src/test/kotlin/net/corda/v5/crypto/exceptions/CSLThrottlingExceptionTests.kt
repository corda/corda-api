package net.corda.v5.crypto.exceptions

import net.corda.v5.crypto.exceptions.CSLThrottlingException.Companion.DEFAULT_THROTTLE_INITIAL_BACKOFF
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CSLThrottlingExceptionTests {
    @Test
    fun `Should return default initial backoff if the current backoff is less than that`() {
        val newBackOff = CSLThrottlingException("Something wrong.")
            .getBackoff(17, DEFAULT_THROTTLE_INITIAL_BACKOFF - 1)
        assertEquals(DEFAULT_THROTTLE_INITIAL_BACKOFF, newBackOff)
    }

    @Test
    fun `Should return increasing backoff`() {
        val e = CSLThrottlingException("Something wrong.", RuntimeException())
        var backOff = e.getBackoff(1, DEFAULT_THROTTLE_INITIAL_BACKOFF - 1)
        assertEquals(DEFAULT_THROTTLE_INITIAL_BACKOFF, backOff)
        backOff = e.getBackoff(2, backOff)
        assertEquals(DEFAULT_THROTTLE_INITIAL_BACKOFF * 2, backOff)
        backOff = e.getBackoff(3, backOff)
        assertEquals(DEFAULT_THROTTLE_INITIAL_BACKOFF * 4, backOff)
        backOff = e.getBackoff(4, backOff)
        assertEquals(DEFAULT_THROTTLE_INITIAL_BACKOFF * 8, backOff)
        backOff = e.getBackoff(5, backOff)
        assertEquals(DEFAULT_THROTTLE_INITIAL_BACKOFF * 16, backOff)
    }
}