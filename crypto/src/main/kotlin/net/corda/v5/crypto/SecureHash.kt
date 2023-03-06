package net.corda.v5.crypto

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Container for a cryptographically secure hash value.
 * Provides utilities for generating a cryptographic hash using different algorithms (currently only SHA-256 supported).
 *
 * @param algorithm Hashing algorithm which was used to generate the hash.
 * @param bytes Hash value.
 *
 * @property bytes Hash value
 */
@CordaSerializable
interface SecureHash {

    /**
     * Returns hexadecimal representation of the hash value.
     */
    fun toHexString()

    /**
     * Returns the first [prefixLen] hexadecimal digits of the [SecureHash] value.
     * @param prefixLen The number of characters in the prefix.
     */
    fun prefixChars(prefixLen: Int)

    /**
     * Compares the two given instances of the [SecureHash] based on the content.
     */
    override fun equals(other: Any?): Boolean

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode(): Int

    /**
     * Converts a [SecureHash] object to a string representation containing the [algorithm] and hexadecimal
     * representation of the [bytes] separated by the colon character.
     */
    override fun toString(): String
}