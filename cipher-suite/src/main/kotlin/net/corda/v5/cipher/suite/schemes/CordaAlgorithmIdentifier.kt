package net.corda.v5.cipher.suite.schemes


// the name includes Corda so as not to clash in implementations that may use librares
// with similar identifiers.
data class CordaAlgorithmIdentifier(
    val identifier: String,
    val parameters: List<String>
)
