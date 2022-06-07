package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.mocks.DigestServiceMock
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.DigestService
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import java.security.MessageDigest
import java.security.spec.AlgorithmParameterSpec
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class CustomSignatureSpecTests {
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
            CustomSignatureSpec(
                signatureName = "  ",
                customDigestName = DigestAlgorithmName.SHA2_256
            )
        }
    }

    @Test
    fun `getSigningData should returned digest byte array for precalculated digest`() {
        val spec = CustomSignatureSpec(
            signatureName = "NONEwithECDSA",
            customDigestName = DigestAlgorithmName.SHA2_256
        )
        val data = UUID.randomUUID().toString().toByteArray()
        val expected = MessageDigest.getInstance("SHA-256").digest(data)
        assertArrayEquals(expected, spec.getSigningData(digestService, data))
    }

    @Test
    fun `getSigningData should return passed byte array for standard digest calculation`() {
        val spec = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        val data = UUID.randomUUID().toString().toByteArray()
        val expected = MessageDigest.getInstance("SHA-256").digest(data)
        assertArrayEquals(expected, spec.getSigningData(digestService, data))
    }

    @Test
    fun `hashCode should return same value for the same signature names, digest and params`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        assertEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `hashCode should return different value for the different signature names`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = CustomSignatureSpec(
            "RSA",
            DigestAlgorithmName.SHA2_256,
            params
        )
        assertNotEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `hashCode should return different value for the different digest`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_384,
            params
        )
        assertNotEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `hashCode should return different value for the different params`() {
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        val spec2 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        assertNotEquals(spec1.hashCode(), spec2.hashCode())
    }

    @Test
    fun `equals should return true for the same instance`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = spec1
        assertTrue(spec1.equals(spec2))
    }

    @Test
    fun `equals should return true for the same signature names and params`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        assertTrue(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false for the different signature names`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = CustomSignatureSpec(
            "RSA",
            DigestAlgorithmName.SHA2_256,
            params
        )
        assertFalse(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false for the different digest`() {
        val params = mock<AlgorithmParameterSpec>()
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            params
        )
        val spec2 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_384,
            params
        )
        assertFalse(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false for the different params`() {
        val spec1 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        val spec2 = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        assertFalse(spec1.equals(spec2))
    }

    @Test
    fun `hashCode should return false when comparing with null`() {
        val spec = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        assertFalse(spec.equals(null))
    }

    @Test
    fun `hashCode should return false when comparing with other type`() {
        val spec = CustomSignatureSpec(
            "RSA/NONE/PKCS1Padding",
            DigestAlgorithmName.SHA2_256,
            mock()
        )
        assertFalse(spec.equals("RSASSA-PSS"))
    }
}