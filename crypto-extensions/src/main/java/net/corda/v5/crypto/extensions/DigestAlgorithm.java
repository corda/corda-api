package net.corda.v5.crypto.extensions;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

/**
 * Interface defining a custom digest calculation implementation. The interface should be implemented
 * if a CPK developer wishes to provide support for digest algorithms beyond those supported by the Corda platform.
 * The implementation of the interface must be coupled with the implementation of the {@link DigestAlgorithmFactory} which
 * will be used to create the instances. For each algorithm there must be matching a pair of
 * {@link DigestAlgorithmFactory} and {@link DigestAlgorithm} implementations.
 * </p>
 * Example use of {@link DigestAlgorithm}:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class TripleSha256Digest : DigestAlgorithm {
 *     companion object {
 *         const val ALGORITHM = "SHA-256-TRIPLE"
 *         const val STREAM_BUFFER_SIZE = DEFAULT_BUFFER_SIZE
 *         fun ByteArray.sha256Bytes(): ByteArray =
 *             MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.name).digest(this)
 *     }
 *
 *     override fun getAlgorithm() = ALGORITHM
 *     override fun getDigestLength() = 32
 *     override fun digest(bytes: ByteArray): ByteArray = bytes.sha256Bytes().sha256Bytes().sha256Bytes()
 *     override fun digest(inputStream: InputStream): ByteArray {
 *         val messageDigest = MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.name)
 *         val buffer = ByteArray(STREAM_BUFFER_SIZE)
 *         while (true) {
 *             val read = inputStream.read(buffer)
 *             if (read <= 0) break
 *             messageDigest.update(buffer, 0, read)
 *         }
 *         return messageDigest.digest().sha256Bytes().sha256Bytes()
 *     }
 * }
 * </pre></li>
 * <li>Java:<pre>{@code
 * public class TripleSha256Digest implements DigestAlgorithm {
 *     static String ALGORITHM = "SHA-256-TRIPLE";
 *     static int BUFFER_SIZE = 8192;
 *     static byte[] sha256Bytes(MessageDigest md, byte[] bytes) {
 *         md.reset();
 *         md.update(bytes);
 *         return md.digest();
 *     }
 *
 *     @Override
 *     public String getAlgorithm() {
 *         return ALGORITHM;
 *     }
 *     @Override
 *     public int getDigestLength() {
 *         return 32;
 *     }
 *     @Override
 *     public byte[] digest(@NotNull byte[] bytes) {
 *         try {
 *             MessageDigest md = MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.getName());
 *             byte[] digestOnce = sha256Bytes(md, bytes);
 *             byte[] digestTwice = sha256Bytes(md, digestOnce);
 *             byte[] digestThrice = sha256Bytes(md, digestTwice);
 *             return digestThrice;
 *         } catch (Exception e) {
 *             throw new RuntimeException(e);
 *         }
 *     }
 *     @Override
 *     public byte[] digest(@NotNull InputStream inputStream) {
 *         try {
 *             MessageDigest md = MessageDigest.getInstance(DigestAlgorithmName.SHA2_256.getName());
 *             byte[] buffer = new byte[BUFFER_SIZE];
 *             int read;
 *             while ((read = inputStream.read(buffer)) >= 0) {
 *                 md.update(buffer, 0, read);
 *             }
 *             byte[] digestOnce = md.digest();
 *             byte[] digestTwice = sha256Bytes(md, digestOnce);
 *             byte[] digestThrice = sha256Bytes(md, digestTwice);
 *             return digestThrice;
 *         } catch (Exception e) {
 *             throw new RuntimeException(e);
 *         }
 *     }
 * }
 * </pre></li>
 * </ul>
 *
 * Example use of {@link DigestAlgorithmFactory}:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class TripleSha256 : DigestAlgorithmFactory {
 *     override fun getAlgorithm() = TripleSha256Digest.ALGORITHM
 *     override fun getInstance(): DigestAlgorithm = TripleSha256Digest()
 * }
 * </pre></li>
 * <li>Java:<pre>{@code
 * class TripleSha256 implements DigestAlgorithmFactory {
 *     @Override
 *     public String getAlgorithm() {
 *         return TripleSha256Digest.ALGORITHM;
 *     }
 *     @Override
 *     public DigestAlgorithm getInstance() {
 *         return new TripleSha256Digest();
 *     }
 * }
 * </pre></li>
 * </ul>
 */
public interface DigestAlgorithm {
    /**
     * Get the algorithm identifier, for example, 'QUAD-SHA-256', the unique name (per Corda Platform and given CPK)
     * of the digest algorithm. The name must match the names used by the corresponding [DigestAlgorithmFactory].
     *
     * @return Algorithm name as a string.
     */
    @NotNull
    String getAlgorithm();

    /**
     * The length of the digest in bytes.
     *
     * @return Number of bytes in the digest as an integer.
     */
    int getDigestLength();

    /**
     * Computes the digest of the byte array. The computation must be stateless and thread safe.
     *
     * @param bytes The byte array to hash.
     * @return The hash value of the bytes.
     */
    @NotNull
    byte[] digest(@NotNull byte[] bytes);

    /**
     * Computes the digest of the {@link InputStream} bytes. The computation must be stateless and thread safe.
     *
     * @param inputStream The [InputStream] to hash.
     * @return The hash value of the [digestLength].
     */
    @NotNull
    byte[] digest(@NotNull InputStream inputStream);
}
