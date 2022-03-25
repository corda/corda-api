package net.corda.v5.cipher.suite.schemes

import net.corda.v5.cipher.suite.computeHSMAlias
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CryptoServiceUtilsTests {
    @Test
    fun `computeHSMAlias should compute deterministic alias for the same input tenant and alias`() {
        val tenant = UUID.randomUUID().toString()
        val alias = UUID.randomUUID().toString()
        val a1 = computeHSMAlias(tenant, alias)
        val a2 = computeHSMAlias(tenant, alias)
        val a3 = computeHSMAlias(tenant, alias)
        assertEquals(a1, a2)
        assertEquals(a1, a3)
    }

    @Test
    fun `computeHSMAlias should compute different alias for the same input tenant but different aliases`() {
        val alias = UUID.randomUUID().toString()
        val a1 = computeHSMAlias(UUID.randomUUID().toString(), alias)
        val a2 = computeHSMAlias(UUID.randomUUID().toString(), alias)
        assertNotEquals(a1, a2)
    }

    @Test
    fun `computeHSMAlias should compute different alias for the same input alias but different tenants`() {
        val tenant = UUID.randomUUID().toString()
        val a1 = computeHSMAlias(tenant, UUID.randomUUID().toString())
        val a2 = computeHSMAlias(tenant, UUID.randomUUID().toString())
        assertNotEquals(a1, a2)
    }
}