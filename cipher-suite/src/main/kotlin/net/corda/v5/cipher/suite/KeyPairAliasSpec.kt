package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SignatureScheme

/**
 * Define alias to generate a key pair.
 */
class KeyPairAliasSpec(
    val alias: String,
    override val signatureScheme: SignatureScheme
) : KeyPairGenerationSpec