package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SignatureScheme

/**
 * Marker interface denote the signing parameters.
 */
interface SigningSpec {
    val signatureScheme: SignatureScheme
}