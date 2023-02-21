package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Container for a public key hash value.
 * Provides utilities for generating a public key hash by calculating it from a given [PublicKey], or by parsing a given
 * [String] or [ByteArray] input.
 *
 * @property value Hexadecimal string representing SHA-256 of the public key.
 */
public final class PublicKeyHash {
    @NotNull
    private final String value;

    public PublicKeyHash(String value) {
        this.value = value;
    }
    
    /**
     * Parses the given hash as [ByteArray] and wraps it in a [PublicKeyHash].
     *
     * @param bytes The byte array to be parsed.
     * @return Returns a [PublicKeyHash] containing the SHA-256 hash.
     * @throws IllegalArgumentException if size of [bytes] is not 32.
     */
    @NotNull
    static public PublicKeyHash parse(@NotNull byte[] bytes) {
        if (bytes.length != 32) {
            throw new IllegalArgumentException("Input must be 32 bytes long for SHA-256 hash.");
        }
        return new PublicKeyHash(bytesToHexString(bytes));
    }

    /**
     * Parses the given hash as hexadecimal [String] and wraps it in a [PublicKeyHash].
     *
     * @param str The Hex string to be parsed.
     * @return Returns a [PublicKeyHash] containing the SHA-256 hash.
     * @throws IllegalArgumentException if length of [str] is not 64, or if [str] is not a valid Hex string.
     */
    @NotNull
    static public PublicKeyHash parse(@NotNull String str) {
        if (str.length() != 64) {
            throw new IllegalArgumentException("Input must be 64 characters long for Hex of SHA-256 hash.");
        }
        String strUpper = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!(ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F')) {
                throw new IllegalArgumentException("Input is an invalid Hex String.");
            }
        }
        return new PublicKeyHash(strUpper);
    }

    /**
     * Computes the public key hash from a given [PublicKey].
     *
     * @param publicKey The public key whose hash is to be generated.
     * @return Returns a [PublicKeyHash] containing the SHA-256 hash.
     */
    @NotNull
    public static PublicKeyHash calculate(@NotNull PublicKey publicKey) throws NoSuchAlgorithmException {
        return calculate(publicKey.getEncoded()); 
    }

    /**
     * Computes the public key hash from a given encoded [PublicKey].
     *
     * @param publicKey The public key whose hash is to be generated.
     * @return Returns a [PublicKeyHash] containing the SHA-256 hash.
     */
    @NotNull
    public static PublicKeyHash calculate(@NotNull byte[] publicKey) throws NoSuchAlgorithmException  {
        byte[] digest = MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.getName()).digest(publicKey);
        String value = bytesToHexString(digest);
        return new PublicKeyHash(value);
    }


    @NotNull
    public static String bytesToHexString(@NotNull byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * Returns the id as the first 12 characters of the [value] which is SHA-256 hash of the public key.
     */
    @NotNull
    public String getId() {
        return value.substring(0, 12);
    }

    /**
     * Returns a hash code value for the object.
     */
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compares the object with the given instance of the [PublicKeyHash], [ByteArray], or [String] by converting the
     * object hexadecimal representation.
     */
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (other instanceof byte[] && (bytesToHexString((byte[]) other).equals(value))) return true;
        if (other instanceof String && ((String) other).equalsIgnoreCase(value)) return true;
        if (other instanceof PublicKeyHash && ((PublicKeyHash) other).toString().equalsIgnoreCase(this.value)) return true;
        return false;
    }

    /**
     * Converts a [PublicKeyHash] object to a string representation. Returns Hex representation of its hash value.
     */
    @NotNull
    public String toString() {
        return value;
    }
}
