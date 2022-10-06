package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.DigitalSignature
import net.corda.v5.crypto.SignatureSpec
import net.corda.v5.crypto.exceptions.CryptoSignatureException
import java.security.PublicKey

/**
 * Allows flows to verify digital signatures.
 *
 * Corda provides an instance of [DigitalSignatureVerificationService] to flows via property injection.
 */
@DoNotImplement
interface DigitalSignatureVerificationService {
    /**
     *
     * Check a payload and signature, and optionally a digest algorithm, check the signature matches using a specific public key.
     * This overload is preferable so that calling code does not need to assume specific cryptographic algorithms, since
     * the best choices change over time.
     *
     * @param payload The payload/message that may be in clear data that was signed (e.g. the Merkle root).
     * @param signatureData The signature data and public key, which should have been earlier produced by [SigningService.sign].
     * @param publicKey A verified public key aginst which to verify the signature.
     * @return The metadata associated with the signature, passed in to [SigningService.sign]
     *
     * @throws CryptoSignatureException If verification of the digital signature fails.
     * @throws IllegalArgumentException If the signature scheme is not supported or if any of the clear or signature
     * data is empty.
     */
    fun verify(payload: ByteArray, signatureData: DigitalSignature.WithMetadata, publicKey: PublicKey, digestAlgorithmName: DigestAlgorithmName?): Map<String, String>

    /**
     *
     * Check a payload and signature, and optionally a digest algorithm, check the signature matches using a specific public key.
     * This overload allows the called to precisely select an algorithm when control is required. In most cases please use
     * the overload above so that the currently most appropriate algorithm can be chosen by the serivce.
     *
     * @param payload The payload/message that may be in clear data that was signed (e.g. the Merkle root).
     * @param signatureData The signature data and public key, which should have been earlier produced by [SigningService.sign].
     * @param publicKey A verified public key aginst which to verify the signature.
     * @param signatureSpec The expected specific signature spec.
     *
     * @throws CryptoSignatureException If verification of the digital signature fails.
     * @throws IllegalArgumentException If the signature scheme is not supported or if any of the clear or signature
     * data is empty.
     */
    fun verify(payload: ByteArray, signatureData: DigitalSignature.WithMetadata, publicKey: PublicKey, signatureSpec: SignatureSpec): Map<String, String>
}
