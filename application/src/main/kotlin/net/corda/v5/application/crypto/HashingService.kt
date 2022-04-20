package net.corda.v5.application.crypto

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.DigestService
import net.corda.v5.crypto.SecureHash

/**
 * Handles hashing of bytes.
 *
 * Delegates all functionality to [DigestService].
 */
@DoNotImplement
interface HashingService : CordaServiceInjectable, CordaFlowInjectable {

    /**
     * Default [DigestAlgorithmName] for this hashing service.
     */
    val defaultDigestAlgorithmName: DigestAlgorithmName

    /**
     * Computes the digest of the [ByteArray].
     *
     * @param bytes The [ByteArray] to hash.
     * @param digestAlgorithmName The digest algorithm to be used for hashing.
     */
    @Suspendable
    fun hash(bytes: ByteArray, digestAlgorithmName: DigestAlgorithmName): SecureHash

    /**
     * Computes the digest of the [ByteArray] using the default digest algorithm ([DigestAlgorithmName.DEFAULT_ALGORITHM_NAME]).
     *
     * @param bytes The [ByteArray] to hash.
     */
    @Suspendable
    fun hash(bytes: ByteArray): SecureHash

    /**
     * Returns the [DigestAlgorithmName] digest length in bytes.
     *
     * @param digestAlgorithmName The digest algorithm to get the digest length for.
     */
    @Suspendable
    fun digestLength(digestAlgorithmName: DigestAlgorithmName): Int

    /**
     * Re-hashes [secureHash] bytes using its original digest algorithm.
     *
     * @param secureHash The [SecureHash] to re-hash.
     */
    @Suspendable
    fun reHash(secureHash: SecureHash): SecureHash

    /**
     * Generates a random hash value.
     */
    @Suspendable
    fun randomHash(digestAlgorithmName: DigestAlgorithmName): SecureHash

    /**
     * Appends [second] digest to [first], and then computes the digest of the result. Both digests need to be of the same digest algorithm.
     * The result digest is of the same digest algorithm as well.
     *
     * @param first The first digest in the concatenation.
     * @param second The second digest in the concatenation.
     */
    @Suspendable
    fun concatenate(first: SecureHash, second: SecureHash): SecureHash
}