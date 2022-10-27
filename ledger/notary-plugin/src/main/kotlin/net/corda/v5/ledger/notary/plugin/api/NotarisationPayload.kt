package net.corda.v5.ledger.notary.plugin.api

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.ledger.notary.plugin.core.NotarisationRequestSignature

/**
 * Container for the transaction and notarisation request signature. This is the payload that gets sent by a client to
 * a notary service for committing the input states of the [transaction].
 *
 * This interface uses Any as for the [transaction] type. The [transaction] object will be validated as a concrete type
 * (for example [UtxoSignedTransaction][net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction]) against the
 * specified [validTypes] list.
 */
@CordaSerializable
interface NotarisationPayload {
    val transaction: Any
    val requestSignature: NotarisationRequestSignature
    val validTypes: List<Class<*>>
}
