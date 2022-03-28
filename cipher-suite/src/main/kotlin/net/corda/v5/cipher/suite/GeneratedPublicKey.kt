package net.corda.v5.cipher.suite

import java.security.PublicKey

/**
 * Holding class for the returned by the [CryptoService] public key for generated key pair.
 *
 * @property publicKey The public key of the pair.
 */
class GeneratedPublicKey(
    override val publicKey: PublicKey
) : GeneratedKey