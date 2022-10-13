package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.DigitalSignature
import net.corda.v5.crypto.DigitalSignedDocument
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
     * The signature spec including digest will be extracted from the payload. This will either return cleanly, in which the case
     * the verified signatures are returned, or exceptions will be thrown
     *
     * @param payload The payload/message that may be in clear data that was signed (e.g. the Merkle root).
     * @param publicKeys A set of verified public keys to use for verification. Some of the signatures in signed document must have keys in this set.
     * @return The metadata associated with the signature, passed in to [DigitalSigningService.sign]
     *
     * @throws CryptoSignatureException If verification of the digital signature fails.
     * @throws IllegalArgumentException If the signature scheme is not supported or if any of the clear or signature
     * data is empty.
     */
    fun <T, M> verify(payload: DigitalSignedDocument<T, M>, publicKeys: Set<PublicKey>): List<DigitalSignature>
}
