package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that a tenant was not found.
 *
 * @property tenantId the tenant id which was not found.
 */
@CordaSerializable
class CryptoTenantNotFoundException : CryptoException {
    val tenantId: String

    constructor(tenantId: String, message: String) : super(message) {
        this.tenantId = tenantId
    }

    constructor(tenantId: String, message: String, cause: Throwable?) : super(message, cause) {
        this.tenantId = tenantId
    }
}