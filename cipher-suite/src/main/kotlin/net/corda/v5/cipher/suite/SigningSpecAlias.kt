package net.corda.v5.cipher.suite

/**
 * Holding class for the key pair which is persisted in HSM and referenced by its alias.
 *
 * @property alias The key pair alias.
 *
 * Note about key aliases. Corda always uses single alias to identify a key pair however some HSMs need separate
 * aliases for public and private keys, in such cases their names have to be derived from the single key pair alias.
 * It could be suffixes or whatever internal naming scheme is used.
 */
class SigningSpecAlias(
    val alias: String
) : SigningSpec