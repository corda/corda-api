package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

/**
 * Defines a contract state.
 *
 * A contract state (or just "state") contains opaque data used by a contract program. It can be thought of as a disk
 * file that the program can use to persist data across transactions.
 *
 * States are immutable. Once created they are never updated, instead, any changes must generate a new successor state.
 * States can be updated (consumed) only once. The notary is responsible for ensuring there is no "double spending" by
 * only signing a transaction if the input states are all free.
 *
 * @property participants The public keys of any participants associated with the current contract state.
 */
@CordaSerializable
interface ContractState {
    val participants: Set<PublicKey>
}

/**
 * Casts the current [ContractState] to the specified type.
 *
 * @param T The underlying type of the [ContractState].
 * @param type The type of the [ContractState] to cast.
 * @return Returns a new [ContractState] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [ContractState] cannot be cast to the specified type.
 */
fun <T : ContractState> ContractState.cast(type: Class<T>): T {
    return if (javaClass.isAssignableFrom(type)) {
        throw IllegalArgumentException("ContractState of type ${javaClass.canonicalName} cannot be cast to type ${type.canonicalName}.")
    } else type.cast(this)
}

/**
 * Casts the current [ContractState] to the specified type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a new [ContractState] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [ContractState] cannot be cast to the specified type.
 */
inline fun <reified T : ContractState> ContractState.cast(): T {
    return cast(T::class.java)
}

/**
 * Gets the class name of the contract associated with the current state, or null if the class name cannot be inferred.
 *
 * @return Returns the class name of the contract associated with the current state, or null if the class name cannot be inferred.
 */
fun ContractState.getContractClassName(): String? {
    val annotation = javaClass.getAnnotation(BelongsToContract::class.java)

    if (annotation != null) {
        return annotation.value.java.canonicalName
    }

    val enclosingClass = javaClass.enclosingClass

    if (enclosingClass != null && Contract::class.java.isAssignableFrom(enclosingClass)) {
        return enclosingClass.canonicalName
    }

    return null
}

/**
 * Gets the class name of the contract associated with the current state.
 *
 * @return Returns the class name of the contract associated with the current state.
 * @throws IllegalArgumentException if the contract class name could not be inferred from the current contract state.
 */
fun ContractState.getContractClassNameOrThrow(): String = requireNotNull(getContractClassName()) {
    """
        Unable to infer Contract class name because state class ${javaClass.canonicalName} is not annotated with
        @BelongsToContract, and does not have an enclosing class which implements Contract. Either annotate ${javaClass.canonicalName}
        with @BelongsToContract, or supply an explicit contract parameter to addOutputState().
        """.trimIndent().replace('\n', ' ')
}
