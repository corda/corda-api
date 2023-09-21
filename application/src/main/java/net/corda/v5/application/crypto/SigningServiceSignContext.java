package net.corda.v5.application.crypto;

import net.corda.v5.base.annotations.CordaSerializable;

/*
 * Context attached to the sign operation of SigningService
 */
@CordaSerializable
public final class SigningServiceSignContext {
    private final String keyCategory;

    /**
     * @param keyCategory the category of the key to be signed with
     */
    public SigningServiceSignContext(String keyCategory) {
        this.keyCategory = keyCategory;
    }
}
