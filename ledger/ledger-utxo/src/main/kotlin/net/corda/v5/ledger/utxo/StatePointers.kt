package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.util.*

/**
 * Defines a pointer to a [ContractState].
 *
 * @property identifier The identifier of the [ContractState] being pointed to.
 * @property type The type of the [ContractState] being pointed to.
 * @property isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
 */
@CordaSerializable
interface StatePointer<T : ContractState> {
    val identifier: Any
    val type: Class<T>
    val isResolved: Boolean
}

/**
 * Represents a state pointer that points statically to a specific [StateRef].
 *
 * @constructor Creates a new instance of the [StaticPointer] data class.
 * @property identifier The identifier of the [ContractState] being pointed to.
 * @property type The type of the [ContractState] being pointed to.
 * @property isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
 */
data class StaticPointer<T : ContractState>(
    override val identifier: StateRef,
    override val type: Class<T>,
    override val isResolved: Boolean
) : StatePointer<T> {

    companion object {

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateRef The identifier of the [ContractState] being pointed to.
         * @param type The type of the [ContractState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        @JvmStatic
        fun <T : ContractState> create(stateRef: StateRef, type: Class<T>, isResolved: Boolean): StaticPointer<T> {
            return StaticPointer(stateRef, type, isResolved)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateRef The identifier of the [ContractState] being pointed to.
         * @param type The type of the [ContractState] being pointed to.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        @JvmStatic
        fun <T : ContractState> create(stateRef: StateRef, type: Class<T>): StaticPointer<T> {
            return create(stateRef, type, false)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [ContractState] being pointed to.
         * @param type The type of the [ContractState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        @JvmStatic
        fun <T : ContractState> create(
            stateAndRef: StateAndRef<T>,
            type: Class<T>,
            isResolved: Boolean
        ): StaticPointer<T> {
            return create(stateAndRef.ref, type, isResolved)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [ContractState] being pointed to.
         * @param type The type of the [ContractState] being pointed to.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        @JvmStatic
        fun <T : ContractState> create(stateAndRef: StateAndRef<T>, type: Class<T>): StaticPointer<T> {
            return create(stateAndRef, type, false)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateRef The identifier of the [ContractState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        inline fun <reified T : ContractState> create(stateRef: StateRef, isResolved: Boolean): StaticPointer<T> {
            return create(stateRef, T::class.java, isResolved)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateRef The identifier of the [ContractState] being pointed to.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        inline fun <reified T : ContractState> create(stateRef: StateRef): StaticPointer<T> {
            return create(stateRef, T::class.java)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [ContractState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        inline fun <reified T : ContractState> create(
            stateAndRef: StateAndRef<T>,
            isResolved: Boolean
        ): StaticPointer<T> {
            return create(stateAndRef, T::class.java, isResolved)
        }

        /**
         * Creates a [StaticPointer] from the specified [StateRef].
         *
         * @param T The underlying type of the [ContractState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [ContractState] being pointed to.
         * @return Returns an [StaticPointer] pointing to the specified [StateRef].
         */
        inline fun <reified T : ContractState> create(stateAndRef: StateAndRef<T>): StaticPointer<T> {
            return create(stateAndRef, T::class.java)
        }
    }

    /**
     * Creates a new instance of the [StaticPointer] data class.
     *
     * @param identifier The identifier of the [ContractState] being pointed to.
     * @param type The type of the [ContractState] being pointed to.
     */
    constructor(identifier: StateRef, type: Class<T>) : this(identifier, type, false)

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param other The object to compare with the current object.
     * @return Returns true if the specified object is equal to the current object; otherwise, false.
     */
    override fun equals(other: Any?): Boolean {
        return this === other
                || other is StaticPointer<*>
                && other.identifier == identifier
                && other.type == type
                && other.isResolved == isResolved
    }

    /**
     * Serves as the default hash function.
     *
     * @return Returns a hash code for the current object.
     */
    override fun hashCode(): Int {
        return Objects.hash(identifier, type, isResolved)
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    override fun toString(): String {
        return "StaticPointer:$identifier"
    }
}

/**
 * Represents a state pointer that points to the most recent, known version of an [IdentifiableState].
 *
 * @constructor Creates a new instance of the [IdentifiablePointer] data class.
 * @property identifier The identifier of the [IdentifiableState] being pointed to.
 * @property type The type of the [IdentifiableState] being pointed to.
 * @property isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
 */
data class IdentifiablePointer<T : IdentifiableState>(
    override val identifier: UUID,
    override val type: Class<T>,
    override val isResolved: Boolean
) : StatePointer<T> {

    companion object {

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param id The identifier of the [IdentifiableState] being pointed to.
         * @param type The type of the [IdentifiableState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        @JvmStatic
        fun <T : IdentifiableState> create(id: UUID, type: Class<T>, isResolved: Boolean): IdentifiablePointer<T> {
            return IdentifiablePointer(id, type, isResolved)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param id The identifier of the [IdentifiableState] being pointed to.
         * @param type The type of the [IdentifiableState] being pointed to.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        @JvmStatic
        fun <T : IdentifiableState> create(id: UUID, type: Class<T>): IdentifiablePointer<T> {
            return create(id, type, false)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [IdentifiableState] being pointed to.
         * @param type The type of the [IdentifiableState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        @JvmStatic
        fun <T : IdentifiableState> create(
            stateAndRef: StateAndRef<T>,
            type: Class<T>,
            isResolved: Boolean
        ): IdentifiablePointer<T> {
            return create(stateAndRef.state.contractState.id, type, isResolved)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [IdentifiableState] being pointed to.
         * @param type The type of the [IdentifiableState] being pointed to.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        @JvmStatic
        fun <T : IdentifiableState> create(stateAndRef: StateAndRef<T>, type: Class<T>): IdentifiablePointer<T> {
            return create(stateAndRef, type, false)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param id The identifier of the [IdentifiableState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        inline fun <reified T : IdentifiableState> create(id: UUID, isResolved: Boolean): IdentifiablePointer<T> {
            return create(id, T::class.java, isResolved)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param id The identifier of the [IdentifiableState] being pointed to.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        inline fun <reified T : IdentifiableState> create(id: UUID): IdentifiablePointer<T> {
            return create(id, T::class.java)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [IdentifiableState] being pointed to.
         * @param isResolved Determines whether the current pointer should be resolved to a reference input when used in a transaction.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        inline fun <reified T : IdentifiableState> create(
            stateAndRef: StateAndRef<T>,
            isResolved: Boolean
        ): IdentifiablePointer<T> {
            return create(stateAndRef, T::class.java, isResolved)
        }

        /**
         * Creates an [IdentifiablePointer] from the specified [UUID].
         *
         * @param T The underlying type of the [IdentifiableState] being pointed to.
         * @param stateAndRef The [StateAndRef] containing the identifier of the [IdentifiableState] being pointed to.
         * @return Returns an [IdentifiablePointer] pointing to the specified [UUID].
         */
        inline fun <reified T : IdentifiableState> create(stateAndRef: StateAndRef<T>): IdentifiablePointer<T> {
            return create(stateAndRef, T::class.java)
        }
    }

    /**
     * Creates a new instance of the [IdentifiablePointer] data class.
     * @param identifier The identifier of the [IdentifiableState] being pointed to.
     * @param type The type of the [IdentifiableState] being pointed to.
     */
    constructor(identifier: UUID, type: Class<T>) : this(identifier, type, false)

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param other The object to compare with the current object.
     * @return Returns true if the specified object is equal to the current object; otherwise, false.
     */
    override fun equals(other: Any?): Boolean {
        return this === other
                || other is IdentifiablePointer<*>
                && other.identifier == identifier
                && other.type == type
                && other.isResolved == isResolved
    }

    /**
     * Serves as the default hash function.
     *
     * @return Returns a hash code for the current object.
     */
    override fun hashCode(): Int {
        return Objects.hash(identifier, type, isResolved)
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    override fun toString(): String {
        return "IdentifiablePointer:$identifier"
    }
}
