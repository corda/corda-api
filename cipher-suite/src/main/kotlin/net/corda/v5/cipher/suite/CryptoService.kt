package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SignatureScheme
import net.corda.v5.crypto.HMAC_SHA256_ALGORITHM
import net.corda.v5.crypto.exceptions.CryptoServiceBadRequestException
import net.corda.v5.crypto.exceptions.CryptoServiceException
import net.corda.v5.crypto.hmac
import org.bouncycastle.util.encoders.Base32

/**
 * Crypto service which can sign as well as create new key pairs.
 *
 * Note about key aliases. Corda always uses single alias to identify a key pair however some HSMs need separate
 * aliases for public and private keys, in such cases their names have to be derived from the single key pair alias.
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
     * Generates a new key to be used as a wrapping key. Some implementations may not have the notion of
     * the wrapping key in such cases the implementation should do nothing.
     *
     * @param masterKeyAlias the alias of the key to be used as a wrapping key.
     * @param failIfExists a flag indicating whether the method should fail if a key already exists under
     * the provided alias or return normally without overriding the key.
     *
     * @throws [CryptoServiceBadRequestException] if a key already exists under this alias
     * and [failIfExists] is set to true.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun createWrappingKey(
        masterKeyAlias: String,
        failIfExists: Boolean
    )

    /**
     * Generate and store an asymmetric key pair.
     * Note that schemeNumberID is Corda specific. Cross-check with the network operator for supported schemeNumberID
     * and their corresponding signature schemes. The main reason for using schemeNumberID and not algorithm OIDs is
     * because some schemes might not be standardised and thus an official OID might for this scheme not exist yet.
     *
     * @param spec parameters to generate key pair, can be [KeyPairAliasSpec] or [KeyPairWrappingSpec].
     * @param context the optional key/value operation context.
     *
     * Returns information about the generated key, could be either [GeneratedPublicKey] or [GeneratedWrappedKey]
     *
     * @throws [CryptoServiceBadRequestException] if the [signatureScheme] is not supported.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun generateKeyPair(
        spec: KeyPairGenerationSpec,
        context: Map<String, String>
    ): GeneratedKey


    /**
     * Sign a byte array using the private key identified by the input arguments.
     * Returns the signature bytes formatted according to the default signature spec.
     *
     * @param key (either [SigningKeyAlias] or [SigningWrappedKey]) to be used for signing.
     * @param signatureScheme the scheme for the signing operation.
     * @param data the data to be signed.
     * @param context the optional key/value operation context.
     *
     * @throws [CryptoServiceBadRequestException] if the private key does not exist as defined in the [key],
     * the key scheme is not supported,  or the [data] is empty array.
     * @throws [CryptoServiceException] for general cryptographic exceptions.
     */
    fun sign(
        key: SigningKey,
        signatureScheme: SignatureScheme,
        data: ByteArray,
        context: Map<String, String>
    ): ByteArray

    /**
     * Computes an alias based on the value supplied by the tenant. As the HSM can be shared across several tenants
     * that will provide a level of separation.
     *
     * The default implementation computes HMAC (HmacSHA256) for concatenation of tenant's id and their alias
     * then transforms it to base32 and takes first 30 characters of that result converting all to lowercase.
     * If input alias is null then the default implementation returns null.
     *
     * @param tenantId The tenant's id which the [alias] belongs to
     * @param alias Alias as supplied by the [tenantId], if the value is null then it means that the alias is being
     * computed for a key which requires wrapping. If the HSM natively supports large number of keys the function
     * may return a not null value which can be used to generate a key owned by the HSM.
     * @param signatureScheme the scheme for the key generation operation.
     * @param secret Secret.
     *
     * @return computed alias which must be unique and must be deterministic, e.g. for the same
     * inputs ([tenantId] and [alias]) always produce the same output. The return value will be passed into the
     * [generateKeyPair]
     *
     * @throws IllegalArgumentException if the tenant is blank or alias is not null but blank or aias is not null but
     * the secret is empty.
     */
    fun computeHSMAlias(
        tenantId: String,
        alias: String?,
        signatureScheme: SignatureScheme,
        secret: ByteArray?
    ): String? {
        require(tenantId.isNotBlank()) {
            "The tenant id cannot be empty."
        }
        if(alias == null) {
            return null
        }
        require(alias.isNotBlank()) {
            "The alias cannot be empty or null."
        }
        require(secret != null && secret.isNotEmpty()) {
            "The secret cannot be empty."
        }
        return Base32.toBase32String(
            (tenantId + alias).encodeToByteArray().hmac(secret, HMAC_SHA256_ALGORITHM)
        ).take(30).toLowerCase()
    }
}
