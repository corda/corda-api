package net.corda.v5.cipher.suite

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.security.SecureRandom
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CryptoServiceTests {
    companion object {
        private val secureRandom = SecureRandom()
        private lateinit var service: CryptoService

        @BeforeAll
        @JvmStatic
        fun setup() {
            service = mock {
                on { computeHSMAlias(any(), any(), any()) }.thenCallRealMethod()
            }
        }

        private fun generateSecret(): ByteArray {
            val bytes = ByteArray(32)
            secureRandom.nextBytes(bytes)
            return bytes
        }
    }

    @Test
    fun `computeHSMAlias should throw IllegalArgumentException for blank tenant id`() {
        assertThrows<IllegalArgumentException> {
            service.computeHSMAlias("", UUID.randomUUID().toString(), generateSecret())
        }
    }

    @Test
    fun `computeHSMAlias should throw IllegalArgumentException for blank alias`() {
        assertThrows<IllegalArgumentException> {
            service.computeHSMAlias(UUID.randomUUID().toString(), "", generateSecret())
        }
    }

    @Test
    fun `computeHSMAlias should throw IllegalArgumentException for empty secret`() {
        assertThrows<IllegalArgumentException> {
            service.computeHSMAlias(UUID.randomUUID().toString(), UUID.randomUUID().toString(), ByteArray(0))
        }
    }

    @Test
    fun `computeHSMAlias should compute deterministic output for the same inputs`() {
        val tenant = UUID.randomUUID().toString()
        val alias = UUID.randomUUID().toString()
        val secret = generateSecret()
        val a1 = service.computeHSMAlias(tenant, alias, secret)
        val a2 = service.computeHSMAlias(tenant, alias, secret)
        val a3 = service.computeHSMAlias(tenant, alias, secret)
        assertEquals(a1, a2)
        assertEquals(a1, a3)
    }

    @Test
    fun `computeHSMAlias should compute different output for different tenants`() {
        val alias = UUID.randomUUID().toString()
        val secret = generateSecret()
        val a1 = service.computeHSMAlias(UUID.randomUUID().toString(), alias, secret)
        val a2 = service.computeHSMAlias(UUID.randomUUID().toString(), alias, secret)
        assertNotEquals(a1, a2)
    }

    @Test
    fun `computeHSMAlias should compute different output for different aliases`() {
        val tenant = UUID.randomUUID().toString()
        val secret = generateSecret()
        val a1 = service.computeHSMAlias(tenant, UUID.randomUUID().toString(), secret)
        val a2 = service.computeHSMAlias(tenant, UUID.randomUUID().toString(), secret)
        assertNotEquals(a1, a2)
    }

    @Test
    fun `computeHSMAlias should compute different output for different secrets`() {
        val tenant = UUID.randomUUID().toString()
        val alias = UUID.randomUUID().toString()
        val secret1 = generateSecret()
        val secret2 = generateSecret()
        val a1 = service.computeHSMAlias(tenant, alias, secret1)
        val a2 = service.computeHSMAlias(tenant, alias, secret2)
        assertNotEquals(a1, a2)
    }
}