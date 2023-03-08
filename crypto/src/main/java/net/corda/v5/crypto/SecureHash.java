package net.corda.v5.crypto;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;

/**
 * A cryptographically secure hash value, computed by a specified digest algorithm ({@link DigestAlgorithmName}).
 * A {@link SecureHash} can be computed and acquired through the {@link net.corda.v5.application.crypto.DigestService}.
 */
@DoNotImplement
@CordaSerializable
public interface SecureHash {
    /**
     * Hashing algorithm which was used to generate the hash.
     */
    @NotNull
    String getAlgorithm();

    // TODO we could get away without this, but adding it for consistency with `PrivacySalt`
    /**
     * The result bytes of the hashing operation with the specified digest algorithm. The specified digest algorithm
     * can be acquired through {@link SecureHash#getAlgorithm()}
     */
    @NotNull
    byte[] getBytes();

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
     * Converts a {@link SecureHash} object to a string representation containing the <code>algorithm</code> and hexadecimal
     * representation of the <code>bytes</code> separated by the colon character.
     */
    @NotNull
    String toString();
}