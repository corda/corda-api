package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.exceptions.CordaRuntimeException
import net.corda.v5.crypto.CompositeKey
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.DigitalSignature
import net.corda.v5.crypto.SignatureSpec
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
interface SigningService {

    /**
     * Using the provided signing [PublicKey], internally chooses the most appropriate signing spec,
     * looks up the matching [PrivateKey] and signs the data, selecting the most appropriate signing type.
     * This overload is preferred; see the other overload if you need to control the signing type.
     *
     * @param payload The data to sign over using the chosen key.
     * @param metadata Optional map of string configuration metadata to be included in the signature. If the metadata
     *                 is null, or if there are no know entries in the map, that's equivalent to calling `sign` without
     *                 metadata.
     * @param publicKey The [PublicKey] partner to an internally held [PrivateKey], either derived from the node's
     * primary identity, or previously generated via the freshKey method. If the [PublicKey] is actually
     * a [CompositeKey], the first leaf signing key hosted by the node is used.
     * @param digestAlgorithmName the message digest algorithm name; if null have the crypto service choose one.
     * @return A [DigitalSignature.WithKey] representing the signed data and the [PublicKey] that belongs to the
     * same [KeyPair] as the [PrivateKey] that signed the data. The signature type will be determined automatically.
     *
     * @throws [CordaRuntimeException] If the input key is not a member of [keys].
     */
    @Suspendable
    fun sign(payload: ByteArray, metadata: Map<String, String>?, publicKey: PublicKey, digestAlgorithmName: DigestAlgorithmName?): DigitalSignature.WithMetadata

    /**
     * Using the provided signing [PublicKey], internally looks up the matching [PrivateKey] and signs the data.
     * This is the low level overload where the signature type is specified; since the best choices change over
     * time it is preferred that callers use the other overload and let the service work out the best choices.
     *
     * @param payload The data to sign over using the chosen key.
     * @param metadata Optional map of string configuration metadata to be included in the signature. If the metadata
     *                 is null, or if there are no know entries in the map, that's equivalent to calling `sign` without
     *                 metadata.
     * @param publicKey The [PublicKey] partner to an internally held [PrivateKey], either derived from the node's
     * primary identity, or previously generated via the freshKey method. If the [PublicKey] is actually
     * a [CompositeKey], the first leaf signing key hosted by the node is used.
     * @param signatureSpec The [SignatureSpec] to use when producing this signature.
     *
     * @return A [DigitalSignature.WithKey] representing the signed data and the [PublicKey] that belongs to the
     * same [KeyPair] as the [PrivateKey] that signed the data.
     *
     * @throws [CordaRuntimeException] If the input key is not a member of [keys].
     */
    @Suspendable
    fun sign(payload: ByteArray, metadata: Map<String, String>?, publicKey: PublicKey, signatureSpec: SignatureSpec): DigitalSignature.WithMetadata

    /**
     * Work out the best [SignatureSpec] to use for a given public key and possibly a specific digest algorithm
     *
     * @param publicKey The [PublicKey], inspected for its type.
     * @param digestAlgorithmName Optionally, the digest algorithm. If not specified, pick the most appropriate
     * digest algorithm
     *
     * @return a [SignatureSpec] that is appropriate.
     */

    fun selectSignatureSpec(publicKey: PublicKey, digestAlgorithmName: DigestAlgorithmName?): SignatureSpec
}
