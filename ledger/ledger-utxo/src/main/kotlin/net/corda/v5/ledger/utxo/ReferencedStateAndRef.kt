package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Represents a [StateAndRef] that should be included in a UTXO ledger transaction as a reference input state.
 *
 * @constructor Creates a new instance of the [ReferencedStateAndRef] data class.
 * @property stateAndRef The [StateAndRef] that should be included in a UTXO ledger transaction as a reference input state.
 */
@CordaSerializable
data class ReferencedStateAndRef<out T : ContractState>(val stateAndRef: StateAndRef<T>)
