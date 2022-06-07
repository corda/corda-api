package net.corda.v5.crypto

import net.corda.v5.crypto.mocks.DigestServiceMock
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals

class SignatureSpecTests {
    companion object {
        private lateinit var digestService: DigestService

        @BeforeAll
        @JvmStatic
        fun setup() {
            digestService = DigestServiceMock()
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with blank signature name`() {
        assertThrows<IllegalArgumentException> {
            SignatureSpec(
                signatureName = "  "
            )
        }
    }

    @Test
    fun `getSigningData should return passed byte array for standard digest calculation`() {
        val spec = ECDSA_SHA256_SIGNATURE_SPEC
        val data = UUID.randomUUID().toString().toByteArray()
        assertArrayEquals(data, spec.getSigningData(digestService, data))
    }

    @Test
    fun `hashCode should return same value for the same signature names`() {
        val spec1 = SignatureSpec("SHA256withECDSA")
        val spec2 = SignatureSpec("SHA256withECDSA")
        assertEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `hashCode should return different value for the different signature names`() {
        val spec1 = SignatureSpec("SHA256withECDSA")
        val spec2 = SignatureSpec("SHA384withECDSA")
        assertNotEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `equals should return true for the same instance`() {
        val spec1 = ECDSA_SHA256_SIGNATURE_SPEC
        val spec2 = ECDSA_SHA256_SIGNATURE_SPEC
        assertTrue(spec1.equals(spec2))
    }

    @Test
    fun `equals should return true for the same signature names`() {
        val spec1 = SignatureSpec("SHA256withECDSA")
        val spec2 = SignatureSpec("SHA256withECDSA")
        assertTrue(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false for the different signature names`() {
        val spec1 = SignatureSpec("SHA256withECDSA")
        val spec2 = SignatureSpec("SHA384withECDSA")
        assertFalse(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false when comparing with null`() {
        val spec = SignatureSpec("SHA256withECDSA")
        assertFalse(spec.equals(null))
    }

    @Test
    fun `hashCode should return false when comparing with other type`() {
        val spec = SignatureSpec("SHA256withECDSA")
        assertFalse(spec.equals("SHA256withECDSA"))
    }
}