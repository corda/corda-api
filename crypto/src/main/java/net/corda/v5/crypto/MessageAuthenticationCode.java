package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

public final class MessageAuthenticationCode {
    private MessageAuthenticationCode() {}
    
    /**
     * Constant specifying the HMAC SHA-256 algorithm.
     */
    @NotNull
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * Constant specifying the HMAC SHA-512 algorithm.
     */
    @NotNull
    public final static String HMAC_SHA512_ALGORITHM = "HmacSHA512";
}
