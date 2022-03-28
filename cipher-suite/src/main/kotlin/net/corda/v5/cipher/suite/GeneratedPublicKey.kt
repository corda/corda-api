package net.corda.v5.cipher.suite

import java.security.PublicKey

/**
 * Holding class for the returned by the [CryptoService] public key for generated key pair.
 *
 * @property publicKey The public key of the pair.
 * @property alias The alias of the key pair. In most cases that would be the same as was passed to the [CryptoService],
 * however if the HSM native supports large number of the keys which normally would be wrapped then that HSM
 * may return the alias of the owned key pair.
 *
 * Note about key aliases. Corda always uses single alias to identify a key pair however some HSMs need separate
 * aliases for public and private keys, in such cases their names have to be derived from the single key pair alias.
 * It could be suffixes or whatever internal naming scheme is used.
 */
class GeneratedPublicKey(
    override val publicKey: PublicKey,
    val alias: String
) : GeneratedKey