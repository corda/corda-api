package net.corda.v5.ledger.notary.pluggable

import net.corda.v5.crypto.DigitalSignature

/**
 * A wrapper around a digital signature used for notarisation requests.
 *
 * The [platformVersion] is required so the notary can verify the signature against the right version of serialized
 * bytes of the [NotarisationRequest]. Otherwise, the request may be rejected.
 */
interface NotarisationRequestSignature {
    val digitalSignature: DigitalSignature.WithKey
    val platformVersion: Int
}
