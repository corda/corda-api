package net.corda.v5.crypto;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;

/**
 * A wrapper around a digital signature.
 */
@DoNotImplement
@CordaSerializable
public interface DigitalSignature {

    @NotNull
    byte[] getBytes();

    /**
     * A digital signature that identifies who is the owner of the signing key used to create this signature.
     */
    @DoNotImplement
    interface WithKeyId extends DigitalSignature {

        // TODO consider renaming to getKeyId() ?
        /**
         * The key id of the public key (public key hash) whose corresponding private key was used to sign the data
         * TODO change the below to say that if composite key is passed in sign operation then key id will be of
         *  firstly found key leaf
         * (as if an instance
         * of the {@link CompositeKey} is passed to the sign operation it may contain keys which are not actually owned by
         * the member).
         */
        @NotNull
        SecureHash getBy();
    }
}
