package net.corda.v5.crypto

import net.corda.v5.crypto.mocks.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.security.PublicKey
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KeyUtilsTests {
    companion object {

        @JvmStatic
        fun publicKeys(): List<PublicKey> = specs.values.map {
            generateKeyPair(it).public
        }

        val other = generateKeyPair(RSA_SPEC).public
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    fun `isKeyFulfilledBy overload with single key should return true if the keys are matching for a given public key`(key: PublicKey) {
        assertTrue(KeyUtils.isKeyFulfilledBy(key, key))
    }


    @ParameterizedTest
    @MethodSource("publicKeys")
    fun `isKeyFulfilledBy overload with single key should return false if the keys are not matching for a given public key`(key: PublicKey) {
        assertFalse(KeyUtils.isKeyFulfilledBy(key, generateKeyPair(ECDSA_SECP256K1_SPEC).public))
    }


    @ParameterizedTest
    @MethodSource("publicKeys")
    fun `isKeyFulfilledBy overload with collection should return true if the keys are matching at least one given public key`(key: PublicKey) {
        assertTrue(KeyUtils.isKeyFulfilledBy(key, listOf(generateKeyPair(ECDSA_SECP256R1_SPEC).public, key)))
    }


    @ParameterizedTest
    @MethodSource("publicKeys")
    fun `isKeyFulfilledBy overload with collection should return false if the keys are not matching at least one given public key`(key: PublicKey) {
        assertFalse(KeyUtils.isKeyFulfilledBy(key, listOf(generateKeyPair(ECDSA_SECP256R1_SPEC).public)))
    }
    
    @ParameterizedTest
    @MethodSource("publicKeys") 
    fun `isKeyInSet claims a key is in a member of single-element set containing that key`(key: PublicKey) {
        assertTrue(KeyUtils.isKeyInSet(key, listOf(key)))
    }

    @ParameterizedTest
    @MethodSource("publicKeys")
    fun `isKeyInSet claims a key is not a member of single-element set containing another key`(key: PublicKey) {
        assertFalse(KeyUtils.isKeyInSet(key, listOf(other)))
    }
}