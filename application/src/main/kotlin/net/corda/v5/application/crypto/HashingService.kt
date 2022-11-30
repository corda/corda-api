package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SecureHash
import java.io.InputStream

/**
 * Provides hashing capabilities to be used in all sandbox types.
 */
@DoNotImplement
interface HashingService {
    /**
     * Computes the digest of the [ByteArray].
     *
     * @param bytes The [ByteArray] to hash.
     * @param digestAlgorithmName The digest algorithm to be used for hashing.
     */
    @Suspendable
    fun hash(bytes: ByteArray, digestAlgorithmName: DigestAlgorithmName): SecureHash

    /**
     * Computes the digest of the [InputStream].
     *
     * @param inputStream The [InputStream] to hash.
     * @param digestAlgorithmName The digest algorithm to be used for hashing.
     */
    @Suspendable
    fun hash(inputStream : InputStream, digestAlgorithmName: DigestAlgorithmName): SecureHash

    /**
     * Returns the [DigestAlgorithmName] digest length in bytes.
     *
     * @param digestAlgorithmName The digest algorithm to get the digest length for.
     */
    @Suspendable
    fun digestLength(digestAlgorithmName: DigestAlgorithmName): Int
}