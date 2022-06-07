package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.KeyScheme
import net.corda.v5.crypto.SignatureSpec
import net.corda.v5.crypto.exceptions.CryptoServiceBadRequestException
import net.corda.v5.crypto.exceptions.CryptoServiceException

/**
 * Crypto service which can sign as well as create new key pairs.
 *
 * Note about key aliases. Corda always uses single alias to identify a key pair however some HSMs need separate
 * aliases for public and private keys, in such cases their names have to be derived from the single key pair alias.
 * It could be suffixes or whatever internal naming scheme is used.
 * Also note that's not required to keep a public
 * key in the HSM as that will be kept by the upstream Crypto Services.
 */
interface CryptoService {
    /**
     * Returns list of crypto service extensions, such as REQUIRE_WRAPPING_KEY, DELETE_KEYS.
     */
    val extensions: List<CryptoServiceExtensions>

    /**
     * Key schemes and signature specs for each key which this implementation of [CryptoService] supports.
     */
    val supportedSchemes: Map<KeyScheme, List<SignatureSpec>>

    /**
     * Generates and optionally stores an asymmetric key pair.
     *
     * @param spec parameters to generate key pair.
     * @param context the optional key/value operation context. The context will have at least two variables defined -
     * 'tenantId' and 'category'.
     *
     * Returns information about the generated key, could be either [GeneratedPublicKey] or [GeneratedWrappedKey]
     * depending on the generated key type.
     *
     * @throws [CryptoServiceBadRequestException] if the [KeyScheme] is not supported.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun generateKeyPair(
        spec: KeyGenerationSpec,
        context: Map<String, String>
    ): GeneratedKey

    /**
     * Sign a byte array using the private key identified by the input arguments.
     * Returns the signature bytes formatted according to the default signature spec.
     *
     * @param spec (either [SigningAliasSpec] or [SigningWrappedSpec]) to be used for signing.
     * @param data the data to be signed.
     * @param context the optional key/value operation context. The context will have at least one variable defined -
     * 'tenantId'.
     *
     * @throws [CryptoServiceBadRequestException] if the private key does not exist as defined in the [key],
     * the key scheme is not supported,  or the [data] is empty array.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun sign(
        spec: SigningSpec,
        data: ByteArray,
        context: Map<String, String>
    ): ByteArray

    /**
     * Generates a new key to be used as a wrapping key. Some implementations may not have the notion of
     * the wrapping key in such cases the implementation should do nothing (note that REQUIRE_WRAPPING_KEY should not
     * be listed for such implementations).
     *
     * @param masterKeyAlias the alias of the key to be used as a wrapping key.
     * @param failIfExists a flag indicating whether the method should fail if a key already exists under
     * the provided alias or return normally without overriding the key.
     * @param context the optional key/value operation context.
     *
     * @throws [CryptoServiceBadRequestException] if a key already exists under this alias
     * and [failIfExists] is set to true.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     * @throws NotImplementedError if the operation is not supported.
     */
    fun createWrappingKey(
        masterKeyAlias: String,
        failIfExists: Boolean,
        context: Map<String, String>
    )

    /**
     * Deletes the key corresponding to the input alias of the service supports the operations .
     * This method doesn't throw if the alias is not found. The services which support that operation must list that
     * in the [CryptoServiceExtensions].
     * @param context the optional key/value operation context. The context will have at least two variables defined -
     * 'tenantId' and 'keyType'.
     *
     * @throws CryptoServiceException if the key cannot be removed.
     * @throws NotImplementedError if the operation is not supported.
     */
    fun delete(alias: String, context: Map<String, String>)
}
