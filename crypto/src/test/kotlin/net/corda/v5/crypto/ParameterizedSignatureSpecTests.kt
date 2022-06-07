package net.corda.v5.crypto

import net.corda.v5.crypto.mocks.DigestServiceMock
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec
import java.util.UUID
import kotlin.test.assertEquals

class ParameterizedSignatureSpecTests {
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
            ParameterizedSignatureSpec(
                signatureName = "  ",
                PSSParameterSpec(
                    "SHA-256",
                    "MGF1",
                    MGF1ParameterSpec.SHA256,
                    32,
                    1
                )
            )
        }
    }

    @Test
    fun `getSigningData should return passed byte array for standard digest calculation`() {
        val spec = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val data = UUID.randomUUID().toString().toByteArray()
        assertArrayEquals(data, spec.getSigningData(digestService, data))
    }

    @Test
    fun `hashCode should return same value for the same signature names and same params`() {
        val spec1 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val spec2 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            spec1.params
        )
        assertEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `hashCode should return different value for the different signature names`() {
        val spec1 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val spec2 = ParameterizedSignatureSpec(
            "RSASSA",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        assertNotEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `hashCode should return different value for the different params`() {
        val spec1 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val spec2 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-384",
                "MGF1",
                MGF1ParameterSpec.SHA384,
                48,
                1
            )
        )
        assertNotEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `equals should return true for the same instance`() {
        val spec1 = RSASSA_PSS_SHA256_SIGNATURE_SPEC
        val spec2 = RSASSA_PSS_SHA256_SIGNATURE_SPEC
        assertTrue(spec1.equals(spec2))
    }

    @Test
    fun `equals should return true for the same signature names and same params`() {
        val spec1 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val spec2 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            spec1.params
        )
        assertTrue(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false for the different signature names`() {
        val spec1 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val spec2 = ParameterizedSignatureSpec(
            "RSASSA",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        assertFalse(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false for the different params`() {
        val spec1 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        val spec2 = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-384",
                "MGF1",
                MGF1ParameterSpec.SHA384,
                48,
                1
            )
        )
        assertFalse(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false when comparing with null`() {
        val spec = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        assertFalse(spec.equals(null))
    }

    @Test
    fun `hashCode should return false when comparing with other type`() {
        val spec = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        assertFalse(spec.equals("RSASSA-PSS"))
    }
}