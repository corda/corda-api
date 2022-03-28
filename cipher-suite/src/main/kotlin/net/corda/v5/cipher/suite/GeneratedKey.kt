package net.corda.v5.cipher.suite

import java.security.PublicKey

/**
 * Marked interface denoting the generated key pair by the [CryptoService].
 * There are two implementations [GeneratedWrappedKey] if the generated key was wrapped or
 *
 * @property publicKey The public key of the pair.
 */
interface GeneratedKey {
    val publicKey: PublicKey
}