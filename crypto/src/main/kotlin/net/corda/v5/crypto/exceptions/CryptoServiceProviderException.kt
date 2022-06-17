package net.corda.v5.crypto.exceptions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Signals that a provider for [CryptoService] was not found.
 *
 * @property serviceName the name of the missing provider.
 */
@CordaSerializable
class CryptoServiceProviderException : CryptoProviderException {
    val serviceName: String

    constructor(serviceName: String, message: String) : super(message) {
        this.serviceName = serviceName
    }

    constructor(serviceName: String, message: String, cause: Throwable?) : super(message, cause) {
        this.serviceName = serviceName
    }
}