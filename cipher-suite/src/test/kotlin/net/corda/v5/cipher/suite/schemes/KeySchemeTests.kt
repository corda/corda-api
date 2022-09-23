package net.corda.v5.cipher.suite.schemes

import net.corda.v5.crypto.ECDSA_SECP256K1_CODE_NAME
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows



class KeySchemeTests {
    @Test
    fun `Should throw IllegalArgumentException when initializing with blank code name`() {
        assertThrows<IllegalArgumentException> {
            KeyScheme(
                codeName = "  ",
                algorithmIDs = listOf(CordaAlgorithmIdentifier("id_ecPublicKey", listOf("secp256k1"))),
                providerName = "provider",
                algorithmName = "EC",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SIGN)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with empty algorithmIDs`() {
        assertThrows<IllegalArgumentException> {
            KeyScheme(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = emptyList(),
                providerName = "provider",
                algorithmName = "EC",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SIGN)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with blank provider name`() {
        assertThrows<IllegalArgumentException> {
            KeyScheme(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = listOf(CordaAlgorithmIdentifier("id_ecPublicKey", listOf("secp256k1"))),
                providerName = "  ",
                algorithmName = "EC",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SIGN)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with blank algorithm name`() {
        assertThrows<IllegalArgumentException> {
            KeyScheme(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = listOf(CordaAlgorithmIdentifier("id_ecPublicKey", listOf("secp256k1"))),
                providerName = "provider",
                algorithmName = "  ",
                algSpec = null,
                keySize = null,
                capabilities = setOf(KeySchemeCapability.SIGN)
            )
        }
    }

    @Test
    fun `Should throw IllegalArgumentException when initializing with empty capabilities`() {
        assertThrows<IllegalArgumentException> {
            KeyScheme(
                codeName = ECDSA_SECP256K1_CODE_NAME,
                algorithmIDs = listOf(CordaAlgorithmIdentifier("id_ecPublicKey", listOf("secp256k1"))),
                providerName = "provider",
                algorithmName = "some-algorithm",
                algSpec = null,
                keySize = null,
                capabilities = emptySet()
            )
        }
    }
}