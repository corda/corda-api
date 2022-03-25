package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SignatureScheme
import net.corda.v5.crypto.exceptions.CryptoServiceBadRequestException
import net.corda.v5.crypto.exceptions.CryptoServiceException
import java.security.PublicKey

/**
 * Crypto service which can sign as well as create new key pairs.
 *
 * Note about key aliases. Corda always uses single alias to identify a key pair however some HSMs need separate
 * aliases for public and private keys, in such cases their names have to be derived from the single key pair alia.
 * It could be suffixes or whatever internal naming scheme is used.
 */
interface CryptoService {
    /**
     * Returns true if the createWrappingKey operation is required otherwise false.
     * The wrapping key may not be required in situations such as HSM supports the wrapped keys natively or
     * wrapping key is global.
     */
    fun requiresWrappingKey(): Boolean

    /**
     * Schemes which this implementation of [CryptoService] supports.
     * */
    fun supportedSchemes(): Array<SignatureScheme>

    /**
     * Schemes which this implementation [CryptoService] supports when using a wrapping key.
     */
    fun supportedWrappingSchemes(): Array<SignatureScheme>

    /**
     * Returns the [PublicKey] of the input alias or null if it doesn't exist.
     *
     * @param tenantId the tenant's id which key is being looked up.
     * @param alias  the tenant's key alias to be looked up as such the alias may not be unique, the implementation
     * must translate that to some unique value like using HASH256 of the concatenated tenant id and the alias
     * (there is a default implementation CryptoServiceUtils.computeHSMAlias).
     *
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun findPublicKey(tenantId: String, alias: String): PublicKey?

    /**
     * Generates a new key to be used as a wrapping key. Some implementations may not have the notion of
     * the wrapping key in such cases the implementation should do nothing.
     * The method would be called only during the bootstrapping process.
     *
     * @param masterKeyAlias the alias of the key to be used as a wrapping key, the alias supposed to be unique and
     * is not a function of the tenant id as public key aliases.
     * @param failIfExists a flag indicating whether the method should fail if a key already exists under
     * the provided alias or return normally without overriding the key.
     *
     * @throws [CryptoServiceBadRequestException] if a key already exists under this alias
     * and [failIfExists] is set to true.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun createWrappingKey(masterKeyAlias: String, failIfExists: Boolean)

    /**
     * Generate and store an asymmetric key pair.
     * Note that schemeNumberID is Corda specific. Cross-check with the network operator for supported schemeNumberID
     * and their corresponding signature schemes. The main reason for using schemeNumberID and not algorithm OIDs is
     * because some schemes might not be standardised and thus an official OID might for this scheme not exist yet.
     *
     * @param tenantId the tenant's id which the key is being generated for.
     * @param alias the tenant's key alias for the key pair to be generated as such the alias may not be unique,
     * the implementation must translate that to some unique value like using HASH256 of the concatenated tenant id
     * and the alias (there is a default implementation CryptoServiceUtils.computeHSMAlias).
     * @param signatureScheme the scheme of the key pair to be generated.
     * @param context the optional key/value operation context.
     *
     * Returns the [PublicKey] of the generated key pair.
     *
     * @throws [CryptoServiceBadRequestException] if the [signatureScheme] is not supported.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun generateKeyPair(
        tenantId: String,
        alias: String,
        signatureScheme: SignatureScheme,
        context: Map<String, String>
    ): PublicKey

    /**
     * Generates an asymmetric key pair, returning the public key and the private key material wrapped
     * using the specified wrapping key.
     *
     * @param masterKeyAlias the alias of the key to be used as a wrapping key, the alias supposed to be unique and
     * is not a function of the tenant id as public key aliases.
     * @param wrappedSignatureScheme the scheme of the key pair to be generated.
     * @param context the optional key/value operation context.
     *
     * @throws [CryptoServiceBadRequestException] if the wrapping key does not exist under
     * the alias or the [wrappedSignatureScheme] is not supported.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun generateWrappedKeyPair(
        masterKeyAlias: String,
        wrappedSignatureScheme: SignatureScheme,
        context: Map<String, String>
    ): WrappedKeyPair

    /**
     * Sign a byte array using the private key identified by the input alias.
     * Returns the signature bytes formatted according to the default signature spec.
     *
     * @param tenantId the tenant's id which the key belongs to.
     * @param alias the tenant's alias of the key to be used for signing as such the alias may not be unique,
     * the implementation must translate that to some unique value like using HASH256 of the concatenated tenant id
     * and the alias (there is a default implementation CryptoServiceUtils.computeHSMAlias).
     * @param signatureScheme the scheme for the signing operation.
     * @param data the data to be signed.
     * @param context the optional key/value operation context.
     *
     * @throws [CryptoServiceBadRequestException] if the private key does not exist under the [alias],
     * the key scheme is not supported,  or the [data] is empty array.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun sign(
        tenantId: String,
        alias: String,
        signatureScheme: SignatureScheme,
        data: ByteArray,
        context: Map<String, String>
    ): ByteArray

    /**
     * Unwraps the provided wrapped key, using the specified wrapping key and signs the provided payload.
     *
     * @param wrappedKey the private key to be used for signing in a wrapped form. The [wrappedKey].masterKeyAlias
     * supposed to be unique and is not a function of the tenant id as public key aliases.
     * @param data the data to be signed.
     * @param context the optional key/value operation context.
     *
     * @throws [CryptoServiceBadRequestException] if the wrapping key does not exist under
     * the master alias in [wrappedKey] or the [data] is empty array.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun sign(
        wrappedKey: WrappedPrivateKey,
        data: ByteArray,
        context: Map<String, String>
    ): ByteArray
}
