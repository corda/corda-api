package net.corda.v5.crypto;

public interface MessageAuthenticationCode {

    /**
     * Constant specifying the HMAC SHA-256 algorithm.
     */
    String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * Constant specifying the HMAC SHA-512 algorithm.
     */
    String HMAC_SHA512_ALGORITHM = "HmacSHA512";
}
