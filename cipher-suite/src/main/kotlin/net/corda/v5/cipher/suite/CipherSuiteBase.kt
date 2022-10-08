package net.corda.v5.cipher.suite

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.cipher.suite.handlers.digest.DigestHandler
import net.corda.v5.cipher.suite.handlers.encoding.KeyEncodingHandler
import net.corda.v5.cipher.suite.handlers.verification.VerifySignatureHandler
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import java.security.PublicKey
import java.security.SecureRandom

/**
 * Represents common operations between in-process and crypto worker cipher suites.
 */
@DoNotImplement
interface CipherSuiteBase : SecureRandomProvider {

    fun register(secureRandom: SecureRandom)

    /**
     * Registers digest handler.
     */
    fun register(algorithmName: String, digest: DigestHandler)

    /**
     * Finds a key scheme by its name if it's supported by the suite or null if it's not supported.
     */
    fun findKeyScheme(codeName: String): KeyScheme?

    /**
     * Finds a key scheme for the [PublicKey] if it's supported by the suite or null if it's not supported.
     */
    fun findKeyScheme(publicKey: PublicKey): KeyScheme?

    /**
     * Finds a key scheme by [AlgorithmIdentifier] if it's supported by the suite or null if it's not supported.
     */
    fun findKeyScheme(algorithm: AlgorithmIdentifier): KeyScheme?

    /**
     * Returns a registered [DigestHandler] for the given digest algorithm name or null if none is registered.
     */
    fun findDigestHandler(algorithmName: String): DigestHandler?

    /**
     * Returns a registered [VerifySignatureHandler] for the given key scheme or null if none is registered.
     */
    fun findVerifySignatureHandler(schemeCodeName: String): VerifySignatureHandler?

    /**
     * Returns a registered [KeyEncodingHandler] for the given key scheme or null if none is registered.
     */
    fun findKeyEncodingHandler(schemeCodeName: String): KeyEncodingHandler?

    /**
     * Returns all registered handlers in the ranking order without duplication.
     */
    fun getAllKeyEncodingHandlers(): List<KeyEncodingHandler>

    /**
     * Returns information about supported key schemes.
     */
    fun getAllSupportedKeySchemes(): List<KeySchemeInfo>
}