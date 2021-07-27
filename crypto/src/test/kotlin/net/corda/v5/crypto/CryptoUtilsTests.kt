package net.corda.v5.crypto

import net.corda.v5.base.util.toBase58
import net.corda.v5.crypto.mocks.ECDSA_SECP256K1_SPEC
import net.corda.v5.crypto.mocks.ECDSA_SECP256R1_SPEC
import net.corda.v5.crypto.mocks.EDDSA_ED25519_SPEC
import net.corda.v5.crypto.mocks.RSA_SPEC
import net.corda.v5.crypto.mocks.generateKeyPair
import net.corda.v5.crypto.mocks.specs
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.security.MessageDigest
import java.security.PublicKey
import kotlin.test.assertTrue

class CryptoUtilsTests {
    companion object {
        @JvmStatic
        fun publicKeys(): Array<PublicKey> = specs.values.map {
            generateKeyPair(it).public
        }.toTypedArray()
    }

    @Test
    @Timeout(10)
    fun `Should compute correctly SHA256 for a given byte array`() {
        val hash = "42".toByteArray().sha256Bytes()
        val expected = byteArrayOf(
                115, 71, 92, -76, 10, 86, -114, -115, -88, -96, 69, -50, -47, 16, 19, 126, 21, -97, -119, 10, -60, -38, -120,
                59, 107, 23, -36, 101, 27, 58, -128, 73
        )
        assertArrayEquals(expected, hash)
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `Should compute correctly SHA256 for a given public key`(key: PublicKey) {
        val hash = key.sha256Bytes()
        val expected = MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.name).digest(key.encoded)
        assertArrayEquals(expected, hash)
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `toStringShort should return base58 representation with DL prefix of SHA256 for a given public key`(key: PublicKey) {
        val str = key.toStringShort()
        val expected = MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.name).digest(key.encoded).toBase58()
        assertEquals("DL$expected", str)
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `keys should return collection consisting of itself for a given public key`(key: PublicKey) {
        val result = key.keys
        assertEquals(1, result.size)
        assertEquals(key, result.first())
    }

    @Test
    @Timeout(10)
    fun `keys should return collection consisting of all leaves for a given composite key with flat leaves`() {
        val publicKeyRSA = generateKeyPair(RSA_SPEC).public
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val publicKeyEd1 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val publicKeyEd2 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyRSA,
                publicKeyK1,
                publicKeyR1,
                publicKeyEd1,
                publicKeyEd2
        ).build()
        val result = compositeKey.keys
        assertEquals(5, result.size)
        assertTrue(result.any { it == publicKeyRSA })
        assertTrue(result.any { it == publicKeyK1 })
        assertTrue(result.any { it == publicKeyR1 })
        assertTrue(result.any { it == publicKeyEd1 })
        assertTrue(result.any { it == publicKeyEd2 })
    }

    @Test
    @Timeout(10)
    fun `keys should return collection consisting of all root leaves for a given hierarchical composite key`() {
        val alicePublicKey = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val bobPublicKey = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val key1 = CompositeKey.Builder().addKeys(alicePublicKey, bobPublicKey).build()
        val key2 = CompositeKey.Builder().addKeys(alicePublicKey, key1).build()
        val key3 = CompositeKey.Builder().addKeys(alicePublicKey, key2).build()
        val key4 = CompositeKey.Builder().addKeys(alicePublicKey, key3).build()
        val key5 = CompositeKey.Builder().addKeys(alicePublicKey, key4).build()
        val key6 = CompositeKey.Builder().addKeys(alicePublicKey, key5, key2).build()
        val result = key6.keys
        assertEquals(2, result.size)
        assertTrue(result.any { it == alicePublicKey })
        assertTrue(result.any { it == bobPublicKey })
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `isFulfilledBy overload with single key should return true if the keys are matching for a given public key`(key: PublicKey) {
        assertTrue(key.isFulfilledBy(key))
    }

    @Test
    @Timeout(10)
    fun `isFulfilledBy overload with single key should return true if the keys is held in the composite key`() {
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyK1
        ).build()
        assertTrue(compositeKey.isFulfilledBy(publicKeyK1))
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `isFulfilledBy overload with single key should return false if the keys are not matching for a given public key`(key: PublicKey) {
        assertFalse(key.isFulfilledBy(generateKeyPair(ECDSA_SECP256K1_SPEC).public))
    }

    @Test
    @Timeout(10)
    fun `isFulfilledBy overload with single key should return false if the keys is not held in the composite key`() {
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyK1
        ).build()
        assertFalse(compositeKey.isFulfilledBy(generateKeyPair(ECDSA_SECP256K1_SPEC).public))
    }

    @Test
    @Timeout(10)
    fun `isFulfilledBy overload with single key should return false if the the composite key has more than one key`() {
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyK1, publicKeyR1
        ).build()
        assertFalse(compositeKey.isFulfilledBy(publicKeyK1))
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `isFulfilledBy overload with collection should return true if the keys are matching at least one given public key`(key: PublicKey) {
        assertTrue(key.isFulfilledBy(listOf(generateKeyPair(ECDSA_SECP256R1_SPEC).public, key)))
    }

    @Test
    @Timeout(10)
    fun `isFulfilledBy overload with collection should return true if all keys are held in the composite key`() {
        val publicKeyRSA = generateKeyPair(RSA_SPEC).public
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val publicKeyEd1 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val publicKeyEd2 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyRSA,
                publicKeyK1,
                publicKeyR1,
                publicKeyEd1,
                publicKeyEd2
        ).build()
        assertTrue {
            compositeKey.isFulfilledBy(listOf(publicKeyRSA, publicKeyK1, publicKeyR1, publicKeyEd1, publicKeyEd2))
        }
    }

    @Test
    @Timeout(10)
    fun `isFulfilledBy overload with collection should return true if at least one key is not held in the composite key`() {
        val publicKeyRSA = generateKeyPair(RSA_SPEC).public
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val publicKeyEd1 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val publicKeyEd2 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyRSA,
                publicKeyK1,
                publicKeyR1,
                publicKeyEd1,
                publicKeyEd2
        ).build()
        assertTrue {
            compositeKey.isFulfilledBy(listOf(
                    publicKeyRSA, publicKeyK1, publicKeyR1, publicKeyEd1, publicKeyEd2, generateKeyPair(ECDSA_SECP256K1_SPEC).public))
        }
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `isFulfilledBy overload with collection should return false if the keys are not matching at least one given public key`(key: PublicKey) {
        assertFalse(key.isFulfilledBy(listOf(generateKeyPair(ECDSA_SECP256R1_SPEC).public)))
    }

    @Test
    @Timeout(10)
    fun `isFulfilledBy overload with collection should return false if not all keys are held in the composite key`() {
        val publicKeyRSA = generateKeyPair(RSA_SPEC).public
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val publicKeyEd1 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val publicKeyEd2 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyRSA,
                publicKeyK1,
                publicKeyR1,
                publicKeyEd1,
                publicKeyEd2
        ).build()
        assertFalse {
            compositeKey.isFulfilledBy(listOf(publicKeyRSA, publicKeyK1, publicKeyR1))
        }
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `containsAny should return true if the key is in collection a given public key`(key: PublicKey) {
        assertTrue(key.containsAny(listOf(
                generateKeyPair(ECDSA_SECP256R1_SPEC).public,
                key
        )))
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    @Timeout(10)
    fun `containsAny should return false if the key is ot in collection a given public key`(key: PublicKey) {
        assertFalse(key.containsAny(listOf(
                generateKeyPair(ECDSA_SECP256R1_SPEC).public,
        )))
    }

    @Test
    @Timeout(10)
    fun `containsAny should return true when composite key leaves intersect with collection`() {
        val publicKeyRSA = generateKeyPair(RSA_SPEC).public
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val publicKeyEd1 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val publicKeyEd2 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyRSA,
                publicKeyK1,
                publicKeyR1,
                publicKeyEd1,
                publicKeyEd2
        ).build()
        assertTrue {
            compositeKey.containsAny(listOf(publicKeyRSA, generateKeyPair(ECDSA_SECP256R1_SPEC).public, publicKeyR1))
        }
    }

    @Test
    @Timeout(10)
    fun `containsAny should return false when composite key leaves do not intersect with collection`() {
        val publicKeyRSA = generateKeyPair(RSA_SPEC).public
        val publicKeyK1 = generateKeyPair(ECDSA_SECP256K1_SPEC).public
        val publicKeyR1 = generateKeyPair(ECDSA_SECP256R1_SPEC).public
        val publicKeyEd1 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val publicKeyEd2 = generateKeyPair(EDDSA_ED25519_SPEC).public
        val compositeKey = CompositeKey.Builder().addKeys(
                publicKeyRSA,
                publicKeyK1,
                publicKeyR1,
                publicKeyEd1,
                publicKeyEd2
        ).build()
        assertFalse {
            compositeKey.containsAny(listOf(generateKeyPair(ECDSA_SECP256R1_SPEC).public, generateKeyPair(ECDSA_SECP256R1_SPEC).public))
        }
    }

    @Test
    @Timeout(10)
    fun `byKeys should return the set of all public keys of the DigitalSignature WithKey collection`() {
        val signature1 = DigitalSignature.WithKey(generateKeyPair(RSA_SPEC).public, ByteArray(5) { 255.toByte() })
        val signature2 = DigitalSignature.WithKey(generateKeyPair(ECDSA_SECP256R1_SPEC).public, ByteArray(5) { 255.toByte() })
        val signature3 = DigitalSignature.WithKey(generateKeyPair(EDDSA_ED25519_SPEC).public, ByteArray(5) { 255.toByte() })
        val duplicateSignature = DigitalSignature.WithKey(generateKeyPair(ECDSA_SECP256K1_SPEC).public, ByteArray(5) { 255.toByte() })
        val signatures = listOf(signature1, duplicateSignature, signature2, duplicateSignature, signature3)
        val result = signatures.byKeys()
        assertEquals(4, result.size)
        assertTrue(result.contains(signature1.by))
        assertTrue(result.contains(signature2.by))
        assertTrue(result.contains(signature3.by))
        assertTrue(result.contains(duplicateSignature.by))
    }

    @Test
    @Timeout(10)
    fun `Should split KeyPair`() {
        val keyPair = generateKeyPair(ECDSA_SECP256R1_SPEC)
        val (private, public) = keyPair
        assertEquals(keyPair.public, public)
        assertEquals(keyPair.private, private)
    }
}