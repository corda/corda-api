package net.corda.v5.ledger.utxo

import java.security.PublicKey

/**
 * Defines a contract state that is bearable (i.e. a "bearer instrument").
 *
 * The expectation is that bearable state implementations will require a single bearer who will be responsible for
 * the ownership of the state, and will be responsible, or have the right to perform certain actions over the state.
 *
 * @property bearer The public key of the bearer of the current state.
 */
interface BearableState : ContractState {
    val bearer: PublicKey
}
