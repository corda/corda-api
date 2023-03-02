package net.corda.v5.crypto;

import java.util.Locale;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The digest algorithm name. This class is to be used in Corda hashing API.
 */

@CordaSerializable
public final class DigestAlgorithmName {
    /**
     * Construt a digest algorith name.
     * 
     *  * @param name The name of the digest algorithm to be used for the instance.
     */
    public DigestAlgorithmName(@NotNull String name) {
        super();
        this.name = name;
        if (this.name.isBlank()) throw new IllegalArgumentException("Hash algorithm name unavailable or not specified");
    }

    @NotNull
    private final String name;

    /**
     * Instance of SHA-256
     */
    @NotNull
    public static final DigestAlgorithmName SHA2_256 = new DigestAlgorithmName("SHA-256");

    /**
     * Instance of Double SHA-256
     */
    @NotNull
    public static final DigestAlgorithmName SHA2_256D = new DigestAlgorithmName("SHA-256D");

    /**
     * Instance of SHA-384
     */
    @NotNull
    public static final DigestAlgorithmName SHA2_384 = new DigestAlgorithmName("SHA-384");

    /**
     * Instance of SHA-512
     */
    @NotNull
    public static final DigestAlgorithmName SHA2_512 = new DigestAlgorithmName("SHA-512");


    /**
     * Instance of algorithm which is considered to be default. Set as SHA-256
     */
    @NotNull
    public static final DigestAlgorithmName DEFAULT_ALGORITHM_NAME;

    /**
     * Converts a [DigestAlgorithmName] object to a string representation.
     */
    @NotNull
    public String toString() {
        return this.name;
    }

    /**
     * Returns a hash code value for the object.
     */
    public int hashCode() {
        return this.name.toUpperCase(Locale.ROOT).hashCode();
    }

    /**
     * Returns a Boolean to indicate if the two specified instances of the [DigestAlgorithmName] are the same based on their content.
     */
    public boolean equals(@Nullable Object other) {
        if (other == null) return false;
        if (this == other) return true;
        if (!(other instanceof DigestAlgorithmName)) return false;
        DigestAlgorithmName otherDigest = (DigestAlgorithmName) other;
        return this.name.equalsIgnoreCase(otherDigest.name);
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    static {
        DEFAULT_ALGORITHM_NAME = SHA2_256;
    }
}
