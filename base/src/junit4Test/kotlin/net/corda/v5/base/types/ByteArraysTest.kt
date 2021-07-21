package net.corda.v5.base.types

import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.ByteBuffer
import java.nio.ReadOnlyBufferException
import kotlin.test.assertEquals

class ByteArraysTest {
    @Test(timeout=300_000)
	fun `slice works`() {
        byteArrayOf(9, 9, 0, 1, 2, 3, 4, 9, 9).let {
            sliceWorksImpl(it, OpaqueBytesSubSequence(it, 2, 5))
        }
        byteArrayOf(0, 1, 2, 3, 4).let {
            sliceWorksImpl(it, OpaqueBytes(it))
        }
    }

    private fun sliceWorksImpl(array: ByteArray, seq: ByteSequence) {
        // Python-style negative indices can be implemented later if needed:
        assertSame(IllegalArgumentException::class.java, catchThrowable { seq.slice(-1) }.javaClass)
        assertSame(IllegalArgumentException::class.java, catchThrowable { seq.slice(start = 0, end = -1) }.javaClass)
        fun check(expected: ByteArray, actual: ByteBuffer) {
            assertEquals(ByteBuffer.wrap(expected), actual)
            assertSame(ReadOnlyBufferException::class.java, catchThrowable { actual.array() }.javaClass)
            assertSame(array,
                    ByteBuffer::class.java
                            .getDeclaredField("hb")
                            .apply { isAccessible = true }
                            .get(actual) as ByteArray
            )
        }
        check(byteArrayOf(0, 1, 2, 3, 4), seq.slice())
        check(byteArrayOf(0, 1, 2, 3, 4), seq.slice(0, 5))
        check(byteArrayOf(0, 1, 2, 3, 4), seq.slice(0, 6))
        check(byteArrayOf(0, 1, 2, 3), seq.slice(0, 4))
        check(byteArrayOf(1, 2, 3), seq.slice(1, 4))
        check(byteArrayOf(1, 2, 3, 4), seq.slice(1, 5))
        check(byteArrayOf(1, 2, 3, 4), seq.slice(1, 6))
        check(byteArrayOf(4), seq.slice(4))
        check(byteArrayOf(), seq.slice(5))
        check(byteArrayOf(), seq.slice(6))
        check(byteArrayOf(2), seq.slice(2, 3))
        check(byteArrayOf(), seq.slice(2, 2))
        check(byteArrayOf(), seq.slice(2, 1))
    }

    @Test(timeout=300_000)
	fun `test hex parsing strictly uppercase`() {
        val HEX_REGEX = "^[0-9A-F]+\$".toRegex()

        val opaqueBytes = OpaqueBytes.of( 1, 23, 63, 127, 34, 44, 55, 66, 22, 110)
        val hexString = opaqueBytes.bytes.toHexString()
        assertTrue(hexString.matches(HEX_REGEX))
    }
}
