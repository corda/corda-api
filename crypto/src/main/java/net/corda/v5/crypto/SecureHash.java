package net.corda.v5.crypto;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.types.ByteArrays;
import net.corda.v5.base.types.OpaqueBytes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

/**
 * Container for a cryptographically secure hash value.
 * Provides utilities for generating a cryptographic hash using different algorithms (currently only SHA-256 supported).
 */
@CordaSerializable
public interface SecureHash {

    /**
     * Returns hexadecimal representation of the hash value.
     */
    @NotNull
    String toHexString();

    /**
     * Returns the first specified number of hexadecimal digits of the {@link SecureHash} value.
     *
     * @param prefixLen The number of characters in the prefix.
     */
    @NotNull
    String prefixChars(int prefixLen);


    /**
     * Returns the first 6 hexadecimal digits of the {@link SecureHash} value.
     */
    @NotNull
    String prefixChars();

    /**
     * Compares the two given instances of the {@link SecureHash} based on the content.
     */
    boolean equals(@Nullable Object other);

    /**
     * Returns a hash code value for the object.
     */
    int hashCode();

    /**
     * Converts a {@link SecureHash} object to a string representation containing the <code>algorithm</code> and hexadecimal
     * representation of the <code>bytes</code> separated by the colon character.
     */
    @NotNull
    String toString();

    @NotNull
    String getAlgorithm();

    @NotNull
    byte[] getHashBytes();

}