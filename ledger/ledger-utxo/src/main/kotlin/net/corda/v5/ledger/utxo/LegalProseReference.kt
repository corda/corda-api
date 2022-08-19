package net.corda.v5.ledger.utxo

/**
 * Indicates the URI that references the legal prose associated with the current [Contract].
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class LegalProseReference(val value: String)
