package net.corda.v5.ledger.notary.pluggable

import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.utxo.StateRef

/**
 * A notarisation request specifies a list of states to consume and the id of the consuming transaction. Its primary
 * purpose is for notarisation traceability – a signature over the notarisation request, [NotarisationRequestSignature],
 * allows a notary to prove that a certain party requested the consumption of a particular state.
 *
 * While the signature must be retained, the notarisation request does not need to be transferred or stored anywhere - it
 * can be built from a [net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction]. The notary can recompute it from
 * the committed states index.
 *
 * Reference inputs states are not included as a separate property in the [NotarisationRequest] as they are not
 * consumed.
 *
 * In case there is a need to prove that a party spent a particular state, the notary will:
 * 1) Locate the consuming transaction id in the index, along with all other states consumed in the same transaction.
 * 2) Build a [NotarisationRequest].
 * 3) Locate the [NotarisationRequestSignature] for the transaction id. The signature will contain the signing public key.
 * 4) Demonstrate the signature verifies against the serialized request. The provided states are always sorted internally,
 *    to ensure the serialization does not get affected by the order.
 */
interface NotarisationRequest {
    val transactionId: SecureHash
    val statesToConsume: List<StateRef>
}
