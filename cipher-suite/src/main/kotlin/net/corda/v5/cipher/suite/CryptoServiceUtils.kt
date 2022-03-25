@file:JvmName("CryptoServiceUtils")

package net.corda.v5.cipher.suite

import net.corda.v5.crypto.sha256Bytes
import org.bouncycastle.util.encoders.Base32

/**
 * Computes an alias based on the value supplied by the tenant. As the HSM can be shared across several tenants
 * that will provide a level of separation. Performance wise it's around 1.2 sec for 1M computations
 * on 2.3 GHz 8-Core Intel Core i9 Intel laptop processor.
 *
 * The implementation computes SHA256 hash for concatenation of tenant's id and their alias than transforms
 * it to base32 and takes first 30 characters of that result converting all to lowercase.
 *
 * @param tenantId The tenant's id which the [alias] belongs to.
 * @param alias Alias as supplied by the [tenantId].
 *
 * @return computed alias which must be unique and must be deterministic, e.g. for the same
 * inputs ([tenantId] and [alias]) always produce the same output.
 */
fun computeHSMAlias(tenantId: String, alias: String): String
        = Base32.toBase32String((tenantId + alias).encodeToByteArray().sha256Bytes()).take(30).toLowerCase()

