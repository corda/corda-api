package net.corda.v5.ledger.utxo

import java.util.*

/**
 * Defines a contract state that is identifiable.
 *
 * The expectation is that identifiable state implementations will evolve by superseding previous versions of
 * themselves whilst sharing a common identifier (id).
 *
 * @property id The identifier of the current state.
 */
interface IdentifiableState : ContractState {
    val id: UUID
}
