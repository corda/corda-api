package net.corda.v5.ledger.notary.pluggable

/**
 * Container for the transaction and notarisation request signature. This is the payload that gets sent by a client to
 * a notary service for committing the input states of the [transaction].
 *
 * This interface uses Any as for the [transaction] type. The [transaction] object will be validated as a concrete type
 * (for example [net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction]) when a class inherits from this interface
 * with a specified [validTypes] list.
 */
interface NotarisationPayload {
    val transaction: Any
    val requestSignature: NotarisationRequestSignature
    val validTypes: List<Class<*>>
}
