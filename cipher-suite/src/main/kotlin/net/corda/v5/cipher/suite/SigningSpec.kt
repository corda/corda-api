package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.KeyScheme

/**
 * Marker interface denoting the signing parameters.
 *
 * @property keyScheme The scheme for the key used for signing operation.
 */
interface SigningSpec {
    val keyScheme: KeyScheme
}