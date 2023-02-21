package net.corda.v5.crypto;

import java.security.PublicKey;
import java.util.Map;

/**
 * A wrapper around a digital signature.
 */
public class DigitalSignature {
    public byte[] bytes;

    /**
     * Construct a digital signature
     *
     * @param bytes the signature
     */
    public DigitalSignature(byte[] bytes) {
        this.bytes = bytes;
    }
    
    public static class WithKey extends DigitalSignature {
        public WithKey(PublicKey by, byte[] bytes, Map<String, String> context) {
            super(bytes);
            this.by = by;
        }
        /**
         * Public key which corresponding private key was used to sign the data (as if an instance
         * of the [CompositeKey] is passed to the sign operation it may contain keys which are not actually owned by
         * the member).
         */
        
        PublicKey by;
        
        /**
         * The context which was passed to the signing operation, note that this context is not signed over.
         */
        public Map<String, String> context;


        public PublicKey getBy() {
            return by;
        }
    }
}
