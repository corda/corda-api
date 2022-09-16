package net.corda.v5.cipher.suite.schemes

/**
 * Defines which operations the key scheme cna be used for, such as for signing or/and deriving a shared secret.
 */
enum class KeySchemeCapability {
    /**
     * The key scheme supports signing operation.
     */
    SIGN,

    /**
     * The key scheme supports shared secret derivations.
     */
    SHARED_SECRET_DERIVATION
}