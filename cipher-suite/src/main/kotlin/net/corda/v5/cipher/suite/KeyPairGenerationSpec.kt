package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SignatureScheme

/**
 * Define marker interface for parameters to generate a key pair.
 */
interface KeyPairGenerationSpec {
    val signatureScheme: SignatureScheme
}

