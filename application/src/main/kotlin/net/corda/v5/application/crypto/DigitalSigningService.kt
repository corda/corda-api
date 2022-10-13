package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.*
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*

/**
 * Responsible for storing and using private keys to sign things. An implementation of this may, for example, call out
 * to a hardware security module that enforces various auditing and frequency-of-use requirements.
 *
 * Corda provides an instance of [DigitalSignatureVerificationService] to flows via property injection.
 */
@DoNotImplement
interface DigitalSigningService {

    /**
     * Construct a <DigitalSignedDocument> with the input data and whatever subset of publickeys have
     * private keys available. Choose the most appropriate sign and hash algorithms for each public key.
     *
     * @param payload The serializable object to sign over using the chosen key.
     * @param metadata Additional data to sign over. Pass in Unit if unneeded. Must be Corda serializable.
     * @param publicKeys A set of [PublicKey]. Some of these public keys must have private keys available in the crypto service.
     *                   The result will include signatures for each field.
     * @param digestAlgorithmName the message digest algorithm name; if null have the crypto service choose one.
     * @return A [DigitalSignature.WithKey] representing the signed data and the [PublicKey] that belongs to the
     * same [KeyPair] as the [PrivateKey] that signed the data. The signature type will be determined automatically.
     *
     */
    @Suspendable
    fun <T, M> sign(payload: T, metadata: M, publicKeys: Set<PublicKey>, digestAlgorithmName: DigestAlgorithmName?): DigitalSignedDocument<T, M>

    /**
     * Construct a <DigitalSignedDocument> with the input data and whatever subset of publickeys have
     * private keys available. This interface assumes serialisation is stable, so that the signature can be verified
     * years later.
     *
     * @param payload The serializable object to sign over using the chosen key.
     * @param metadata Additional data to sign over. Pass in Unit if unneeded. Must be Corda serializable.
     * @param publicKeys A set of [PublicKey]. Some of these public keys must have private keys available in the crypto service.
     *                   The result will include signatures for each field.
     * @param signatureSpec The digital signature
     * @return A [DigitalSignature.WithKey] representing the signed data and the [PublicKey] that belongs to the
     * same [KeyPair] as the [PrivateKey] that signed the data. The signature type will be determined automatically.
     *
     * Construct a <DigitalSignedDocument> witht he input
     */
    @Suspendable
    fun <T, M> sign(payload: T, metadata: M, publicKeys: Set<PublicKey>, signatureSpec: SignatureSpec): DigitalSignedDocument<T, M>
}
