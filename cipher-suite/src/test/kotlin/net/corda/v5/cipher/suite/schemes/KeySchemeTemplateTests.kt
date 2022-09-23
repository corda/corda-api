package net.corda.v5.cipher.suite.schemes

import net.corda.v5.crypto.ECDSA_SECP256K1_CODE_NAME
import org.bouncycastle.pqc.jcajce.spec.SPHINCS256KeyGenParameterSpec
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KeySchemeTemplateTests {
    @Test
    fun `Should create KeyScheme out of the template`() {
        val template = KeySchemeTemplate(
            codeName = "some-code",
            algorithmIDs = listOf(CordaAlgorithmIdentifier("sphincs256", arrayListOf("0", "SHA512_256"))),
            algorithmName = "some-algorithm",
            algSpec = SPHINCS256KeyGenParameterSpec(SPHINCS256KeyGenParameterSpec.SHA512_256),
            keySize = 17,
            capabilities = setOf(KeySchemeCapability.SHARED_SECRET_DERIVATION)
        )
        val scheme = template.makeScheme("BC")
        assertEquals(template.codeName, scheme.codeName)
        assertEquals(template.algorithmName, scheme.algorithmName)
        assertEquals(template.algSpec, scheme.algSpec)
        assertEquals(template.keySize, scheme.keySize)
        assertEquals(template.algorithmIDs.size, scheme.algorithmIDs.size)
        scheme.algorithmIDs.forEachIndexed { i, a ->
            assertEquals(template.algorithmIDs[i], a)
        }
        assertEquals("BC", scheme.providerName)
        assertEquals(1, scheme.capabilities.size)
        assertTrue(scheme.capabilities.contains(KeySchemeCapability.SHARED_SECRET_DERIVATION))
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with blank code name`() {
        assertThrows<IllegalArgumentException> {
            KeySchemeTemplate(
                codeName = "  ",
                algorithmIDs = listOf(CordaAlgorithmIdentifier("id_ecPublicKey", listOf("secp256k1"))),
                algorithmName = "EC",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SHARED_SECRET_DERIVATION)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with empty algorithmOIDs`() {
        assertThrows<IllegalArgumentException> {
            KeySchemeTemplate(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = emptyList(),
                algorithmName = "EC",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SHARED_SECRET_DERIVATION)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with blank algorithm name`() {
        assertThrows<IllegalArgumentException> {
            KeySchemeTemplate(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = listOf(CordaAlgorithmIdentifier("id_ecPublicKey", listOf("secp256k1"))),
                algorithmName = "  ",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SHARED_SECRET_DERIVATION)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with empty capabilities`() {
        assertThrows<IllegalArgumentException> {
            KeySchemeTemplate(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = listOf(CordaAlgorithmIdentifier("X9ObjectIdentifiers", listOf("secp256k1"))),
                algorithmName = "some-algorithm",
                algSpec = null,
                keySize = null,
                capabilities = emptySet()
            )
        }
    }
}