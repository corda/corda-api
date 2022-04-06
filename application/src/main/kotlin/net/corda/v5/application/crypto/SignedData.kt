package net.corda.v5.application.crypto

import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.application.serialization.deserialize
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.util.uncheckedCast
import net.corda.v5.crypto.DigitalSignature
import net.corda.v5.crypto.SignatureVerificationService
import net.corda.v5.serialization.SerializedBytes
import java.security.SignatureException

/**
 * A serialized piece of data and its signature. Enforces signature validity in order to deserialize the data
 * contained within.
 *
 * @param raw the raw serialized data.
 * @param sig the (unverified) signature for the data.
 */
/*
JH: This feels like a crypto thing to me. Is the argument that a user should implement `verifyData`? Looking at it again
I'm thinking we should just remove it, but worth discussing.
 */
@CordaSerializable
open class SignedData<T : Any>(val raw: SerializedBytes<T>, val sig: DigitalSignature.WithKey) {
    /**
     * Return the deserialized data if the signature can be verified.
     *
     * @throws IllegalArgumentException if the data is invalid (only used if verifyData() is overloaded).
     * @throws SignatureException if the signature is invalid.
     */
    @Throws(SignatureException::class)
    fun verified(signatureVerifier: SignatureVerificationService, serializationService: SerializationService): T {
        signatureVerifier.verify(sig.by, sig.bytes, raw.bytes)
        val data: T = uncheckedCast(serializationService.deserialize<Any>(raw.bytes))
        verifyData(data)
        return data
    }

    /**
     * Verify the wrapped data after the signature has been verified and the data deserialised. Provided as an extension
     * point for subclasses.
     */
    protected open fun verifyData(data: T) {
        // By default we accept anything
    }
}
