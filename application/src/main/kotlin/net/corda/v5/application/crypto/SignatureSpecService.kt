package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SignatureSpec
import java.security.PublicKey

@DoNotImplement
interface SignatureSpecService {
    /**
     * Work out a signature and hash algorithm to use for a given public key, given current security policies.
     *
     * @param publicKey the public key to be used for signing
     *
     * @return An appropriate [SignatureSpec], or null if nothing is available for the key type.
     */
    @Suspendable
    fun defaultSignatureSpecFor(publicKey: PublicKey): SignatureSpec?

    /**
     * Work out a signature algorithm given current security policies and a hash algorithm
     *
     * @param publicKey the public key to be used for signing
     * @param digestAlgorithm the digest algorithm to use, e.g. []DigestAlgorithmName.SHA2_256]
     *
     * @return An appropriate [SignatureSpec], or null if nothing is available for the key type
     */

    @Suspendable
    fun defaultSignatureSpecFor(publicKey: PublicKey, digestAlgorithm: DigestAlgorithmName): SignatureSpec?

    @Suspendable
    fun compatibleSignatureSpecsFor(publicKey: PublicKey): List<SignatureSpec>

    @Suspendable
    fun compatibleSignatureSpecsFor(publicKey: PublicKey, hash: DigestAlgorithmName): List<SignatureSpec>
}