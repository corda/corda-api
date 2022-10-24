package net.corda.v5.ledger.notary.pluggable

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.uniqueness.model.UniquenessCheckError

/**
 * An interface that represents the payload returned by the notary server flow to the client flow. Contains the
 * signatures from the server side and the error, in case there's any.
 */
interface NotarisationResponse {
    val signatures: List<DigitalSignatureAndMetadata>
    val error: UniquenessCheckError?
}