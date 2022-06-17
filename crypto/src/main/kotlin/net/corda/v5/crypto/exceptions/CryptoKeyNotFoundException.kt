package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that a public key or wrapping key were not found also if the HSM may detect a condition when
 * the private key is not found.
 *
 * @property tenantId the tenant owning the key.
 * @property key for the public key the key's id, for the private key and wrapping key that's
 * their alias as used by the HSM.
 */
@CordaSerializable
class CryptoKeyNotFoundException : CryptoException {
    val tenantId: String
    val key: String

    constructor(tenantId: String, key: String, message: String) : super(message) {
        this.tenantId = tenantId
        this.key = key
    }

    constructor(tenantId: String, key: String, message: String, cause: Throwable?) : super(message, cause) {
        this.tenantId = tenantId
        this.key = key
    }
}