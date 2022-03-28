package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SignatureScheme

/**
 * Defines wrapping key alias to generate wrapped key pair.
 */
class KeyPairWrappingSpec(
    val masterKeyAlias: String,
    override val signatureScheme: SignatureScheme,
) : KeyPairGenerationSpec