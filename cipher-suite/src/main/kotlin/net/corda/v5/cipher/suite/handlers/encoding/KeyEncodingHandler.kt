package net.corda.v5.cipher.suite.handlers.encoding

import net.corda.v5.cipher.suite.CipherSuiteHandler
import net.corda.v5.cipher.suite.KeyScheme
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import java.security.PublicKey

/**
 * Handles the key serialisation operations.
 */
interface KeyEncodingHandler : CipherSuiteHandler {
    fun getAlgorithmIdentifier(publicKey: PublicKey): AlgorithmIdentifier?

    /**
     * Decodes public key from byte array.
     *
     * @throws IllegalArgumentException if the key scheme is not supported.
     * @throws net.corda.v5.crypto.exceptions.CryptoException for general cryptographic exceptions.
     */
    fun decode(encodedKey: ByteArray): PublicKey?

    /**
     * Low level decoding method from byte array.
     *
     * @throws IllegalArgumentException if the key scheme is not supported.
     * @throws net.corda.v5.crypto.exceptions.CryptoException for general cryptographic exceptions.
     */
    fun decode(scheme: KeyScheme, publicKeyInfo: SubjectPublicKeyInfo, encodedKey: ByteArray): PublicKey?

    /**
     * Decodes public key from PEM encoded string.
     *
     * @throws IllegalArgumentException if the key scheme is not supported.
     * @throws net.corda.v5.crypto.exceptions.CryptoException for general cryptographic exceptions.
     */
    fun decodePem(encodedKey: String): PublicKey?

    /**
     * Low level decoding method from PEM encoded string.
     *
     * @throws IllegalArgumentException if the key scheme is not supported.
     * @throws net.corda.v5.crypto.exceptions.CryptoException for general cryptographic exceptions.
     */
    fun decodePem(scheme: KeyScheme, publicKeyInfo: SubjectPublicKeyInfo, pemContent: ByteArray): PublicKey?

    /**
     * Encodes public key to byte array.
     * The default implementation returns the [PublicKey.getEncoded]
     *
     * @throws IllegalArgumentException if the key scheme is not supported.
     * @throws net.corda.v5.crypto.exceptions.CryptoException for general cryptographic exceptions.
     */
    fun encodeAsByteArray(publicKey: PublicKey): ByteArray = publicKey.encoded

    /**
     * Encodes public key to PEM encoded string.
     *
     * @throws IllegalArgumentException if the key scheme is not supported.
     * @throws net.corda.v5.crypto.exceptions.CryptoException for general cryptographic exceptions.
     */
    fun encodeAsPem(publicKey: PublicKey): String
}