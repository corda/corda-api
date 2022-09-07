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

/**
 * Creates a [ReferencedStateAndRef] from the current [StateAndRef] instance.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a [ReferencedStateAndRef] from the current [StateAndRef] instance.
 */
fun <T : ContractState> StateAndRef<T>.toReferencedStateAndRef(): ReferencedStateAndRef<T> {
    return ReferencedStateAndRef(this)
}

/**
 * Casts the current [StateAndRef] to the specified type.
 *
 * @param T The underlying type of the [ContractState].
 * @param type The type of the [ContractState] to cast to.
 * @return Returns a new [StateAndRef] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [StateAndRef] cannot be cast to the specified type.
 */
fun <T : ContractState> StateAndRef<*>.cast(type: Class<T>): StateAndRef<T> {
    return StateAndRef(state.cast(type), ref)
}

/**
 * Casts the current [StateAndRef] to the specified type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a new [StateAndRef] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [StateAndRef] cannot be cast to the specified type.
 */
inline fun <reified T : ContractState> StateAndRef<*>.cast(): StateAndRef<T> {
    return cast(T::class.java)
}
