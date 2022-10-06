package net.corda.v5.crypto

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.types.OpaqueBytes
import java.security.PublicKey

/**
 * A wrapper around a digital signature.
 *
 * @property bytes The signature.
 * @param bytes The signature.
 */
@CordaSerializable
open class DigitalSignature(
    bytes: ByteArray
) : OpaqueBytes(bytes) {
    /**
     * A digital signature that identifies who the public key is owned by.
     *
     * @param by The public key of the corresponding private key used to sign the data (as if an instance
     * of the [CompositeKey] is passed to the sign operation it may contain keys which are not actually owned by
     * the member).
     * @param bytes The signature.
     * @param context The context which was passed to the signing operation, note that this context is not signed over.
     */
    open class WithKey(
        /**
         * Public key which corresponding private key was used to sign the data (as if an instance
         * of the [CompositeKey] is passed to the sign operation it may contain keys which are not actually owned by
         * the member).
         */
        val by: PublicKey,
        bytes: ByteArray,
        /**
         * The context which was passed to the signing operation, note that this context is not signed over.
         */
        val context: Map<String, String>
    ) : DigitalSignature(bytes)

    class WithMetadata(
        /**
         * Public key which corresponding private key was used to sign the data (as if an instance
         * of the [CompositeKey] is passed to the sign operation it may contain keys which are not actually owned by
         * the member).
         */
        by: PublicKey,
        bytes: ByteArray,
        /**
         * The metadata which was used by the signing operation, note that the metadata **is signed over**.
         * The format will be stable and consistent. The actual signing data is concatenation of the serialised metadata and the  payload data.
         */
        metadata: Map<String, String>,
    ) : WithKey(by, bytes, context)
}
