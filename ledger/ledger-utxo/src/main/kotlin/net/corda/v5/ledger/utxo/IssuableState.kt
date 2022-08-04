package net.corda.v5.ledger.utxo

import java.security.PublicKey

/**
 * Defines a contract state that is issuable.
 *
 * The expectation is that issuable state implementations will require that the issuer signs over any transaction
 * where the state is being issued, and/or consumed, without producing an update.
 *
 * @property issuer The public key of the issuer of the current state.
 */
interface IssuableState : ContractState {
    val issuer: PublicKey
}
