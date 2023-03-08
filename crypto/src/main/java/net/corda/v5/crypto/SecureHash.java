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
    @NotNull
    public String getAlgorithm();

    /**
     * Returns hexadecimal representation of the hash value.
     */
    @NotNull
    public String toHexString();

    /**
     * Returns the first specified number of hexadecimal digits of the {@link SecureHash} value.
     *
     * @param prefixLen The number of characters in the prefix.
     */
    @NotNull
    public String prefixChars(int prefixLen);


    /**
     * Returns the first 6 hexadecimal digits of the {@link SecureHash} value.
     */
    @NotNull
    public String prefixChars();

    /**
     * Converts a {@link SecureHash} object to a string representation containing the <code>algorithm</code> and hexadecimal
     * representation of the <code>bytes</code> separated by the colon character.
     */
    @NotNull
    public String toString();
}