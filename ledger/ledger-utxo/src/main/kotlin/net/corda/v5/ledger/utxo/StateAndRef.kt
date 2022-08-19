package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable

/**
 * Represents a composition of a [TransactionState] and a [StateRef].
 *
 * @constructor Creates a new instance of the [StateAndRef] data class.
 * @property state The [TransactionState] component of the current [StateAndRef].
 * @property ref The [StateRef] component of the current [StateAndRef].
 */
@CordaSerializable
data class StateAndRef<out T : ContractState>(val state: TransactionState<T>, val ref: StateRef)
