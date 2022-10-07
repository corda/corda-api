package net.corda.v5.cipher.suite

import java.security.SecureRandom

/**
 * Provides access to a instance of [SecureRandom]
 */
interface SecureRandomProvider {
    /**
     * An instance of [SecureRandom] which should be used to generate cryptographically secure random value.
     */
    val secureRandom: SecureRandom
}