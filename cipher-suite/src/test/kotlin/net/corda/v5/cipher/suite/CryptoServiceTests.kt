package net.corda.v5.cipher.suite

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CryptoServiceTests {
    companion object {
        private lateinit var service: CryptoService

        @BeforeAll
        @JvmStatic
        fun setup() {
            service = mock {
                on { computeHSMAlias(any(), any()) }.thenCallRealMethod()
            }
        }
    }

    @Test
    fun `computeHSMAlias should compute deterministic alias for the same input tenant and alias`() {
        val tenant = UUID.randomUUID().toString()
        val alias = UUID.randomUUID().toString()
        val a1 = service.computeHSMAlias(tenant, alias)
        val a2 = service.computeHSMAlias(tenant, alias)
        val a3 = service.computeHSMAlias(tenant, alias)
        assertEquals(a1, a2)
        assertEquals(a1, a3)
    }

    @Test
    fun `computeHSMAlias should compute different alias for the same input tenant but different aliases`() {
        val alias = UUID.randomUUID().toString()
        val a1 = service.computeHSMAlias(UUID.randomUUID().toString(), alias)
        val a2 = service.computeHSMAlias(UUID.randomUUID().toString(), alias)
        assertNotEquals(a1, a2)
    }

    @Test
    fun `computeHSMAlias should compute different alias for the same input alias but different tenants`() {
        val tenant = UUID.randomUUID().toString()
        val a1 = service.computeHSMAlias(tenant, UUID.randomUUID().toString())
        val a2 = service.computeHSMAlias(tenant, UUID.randomUUID().toString())
        assertNotEquals(a1, a2)
    }
}