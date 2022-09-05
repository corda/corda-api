package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash

/**
 * Represents a UTXO ledger transaction.
 *
 * // TODO : Properties...
 */
@DoNotImplement
//@Suppress("LongParameterList")
interface UtxoLedgerTransaction {

    val timeWindow: TimeWindow?
    val attachments: List<Attachment>
    val commands: List<CommandAndSignatories<*>>

    val inputStateAndRefs: List<StateAndRef<*>>
    val inputTransactionStates: List<TransactionState<*>> get() = inputStateAndRefs.map { it.state }
    val inputContractStates: List<ContractState> get() = inputTransactionStates.map { it.contractState }

    val referenceInputStateAndRefs: List<StateAndRef<*>>
    val referenceInputTransactionStates: List<TransactionState<*>> get() = referenceInputStateAndRefs.map { it.state }
    val referenceInputContractStates: List<ContractState> get() = referenceInputTransactionStates.map { it.contractState }

    val outputStateAndRefs: List<StateAndRef<*>>
    val outputTransactionStates: List<TransactionState<*>> get() = outputStateAndRefs.map { it.state }
    val outputContractStates: List<ContractState> get() = outputTransactionStates.map { it.contractState }

    /**
     * Verifies the current [UtxoLedgerTransaction].
     *
     * // TODO : Throws...
     */
    fun verify()

    // region Attachments

    /**
     * Obtains the ledger transaction [Attachment] with the specified id.
     *
     * @param id The id of the ledger transaction [Attachment] to obtain.
     * @return Returns the ledger transaction [Attachment] with the specified id.
     * @throws IllegalArgumentException if the ledger transaction [Attachment] with the specified id cannot be found.
     */
    fun getAttachment(id: SecureHash): Attachment {
        return attachments.filter { it.id == id }.singleOrNull() ?: throw IllegalArgumentException(
            "Failed to find a single ledger transaction attachment with the specified id: $id."
        )
    }

    // endregion

    // region Commands

    /**
     * Obtains all ledger transaction [CommandAndSignatories] that match the specified [Command] type.
     *
     * @param T The underlying type of the [Command].
     * @param type The type of the [Command].
     * @return Returns all ledger transaction [CommandAndSignatories] that match the specified [Command] type.
     */
    fun <T : Command> getCommandsAndSignatories(type: Class<T>): List<CommandAndSignatories<T>> {
        return commands.filter { it.command.javaClass.isAssignableFrom(type) }.map { it.cast(type) }
    }

    /**
     * Obtains a single ledger transaction [CommandAndSignatories] that matches the specified [Command] type.
     *
     * @param T The underlying type of the [Command].
     * @param type The type of the [Command].
     * @return Returns a single ledger transaction [CommandAndSignatories] that matches the specified [Command] type.
     * @throws IllegalArgumentException if a single ledger transaction [CommandAndSignatories] that matches the specified [Command] type cannot be found.
     */
    fun <T : Command> getCommandAndSignatories(type: Class<T>): CommandAndSignatories<T> {
        return getCommandsAndSignatories(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction command with the specified type: ${type.canonicalName}."
        )
    }

    // endregion

    // region Input States

    /**
     * Obtains all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getInputStateAndRefs(type: Class<T>): List<StateAndRef<T>> {
        return inputStateAndRefs.filter { type.isAssignableFrom(it.state.contractState.javaClass) }.map { it.cast(type) }
    }

    /**
     * Obtains a single ledger transaction [StateAndRef] input that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns a single ledger transaction [StateAndRef] input that match the specified [ContractState] type.
     * @throws IllegalArgumentException if a single ledger transaction [StateAndRef] input that matches the specified [ContractState] type cannot be found.
     */
    fun <T : ContractState> getInputStateAndRef(type: Class<T>): StateAndRef<T> {
        return getInputStateAndRefs(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction input state and ref with the specified type: ${type.canonicalName}."
        )
    }

    /**
     * Obtains all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getInputStates(type: Class<T>): List<T> {
        return getInputStateAndRefs(type).map { it.state.contractState }
    }

    /**
     * Obtains a single ledger transaction [ContractState] input that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns a single ledger transaction [ContractState] input that match the specified [ContractState] type.
     * @throws IllegalArgumentException if a single ledger transaction [ContractState] input that matches the specified [ContractState] type cannot be found.
     */
    fun <T : ContractState> getInputState(type: Class<T>): T {
        return getInputStates(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction input state with the specified type: ${type.canonicalName}."
        )
    }

    // endregion

    // region Reference Input States

    /**
     * Obtains all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getReferenceInputStateAndRefs(type: Class<T>): List<StateAndRef<T>> {
        return referenceInputStateAndRefs.filter { type.isAssignableFrom(it.state.contractState.javaClass) }.map { it.cast(type) }
    }

    /**
     * Obtains a single ledger transaction [StateAndRef] reference input that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns a single ledger transaction [StateAndRef] reference input that match the specified [ContractState] type.
     * @throws IllegalArgumentException if a single ledger transaction [StateAndRef] reference input that matches the specified [ContractState] type cannot be found.
     */
    fun <T : ContractState> getReferenceInputStateAndRef(type: Class<T>): StateAndRef<T> {
        return getReferenceInputStateAndRefs(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction reference input state and ref with the specified type: ${type.canonicalName}."
        )
    }

    /**
     * Obtains all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getReferenceInputStates(type: Class<T>): List<T> {
        return getReferenceInputStateAndRefs(type).map { it.state.contractState }
    }

    /**
     * Obtains a single ledger transaction [ContractState] reference input that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns a single ledger transaction [ContractState] reference input that match the specified [ContractState] type.
     * @throws IllegalArgumentException if a single ledger transaction [ContractState] reference input that matches the specified [ContractState] type cannot be found.
     */
    fun <T : ContractState> getReferenceInputState(type: Class<T>): T {
        return getReferenceInputStates(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction reference input state with the specified type: ${type.canonicalName}."
        )
    }

    // endregion

    // region Output States

    /**
     * Obtains all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getOutputStateAndRefs(type: Class<T>): List<StateAndRef<T>> {
        return outputStateAndRefs.filter { type.isAssignableFrom(it.state.contractState.javaClass) }.map { it.cast(type) }
    }

    /**
     * Obtains a single ledger transaction [StateAndRef] output that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns a single ledger transaction [StateAndRef] output that match the specified [ContractState] type.
     * @throws IllegalArgumentException if a single ledger transaction [StateAndRef] output that matches the specified [ContractState] type cannot be found.
     */
    fun <T : ContractState> getOutputStateAndRef(type: Class<T>): StateAndRef<T> {
        return getOutputStateAndRefs(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction output state and ref with the specified type: ${type.canonicalName}."
        )
    }

    /**
     * Obtains all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
     */
    fun <T : ContractState> getOutputStates(type: Class<T>): List<T> {
        return getOutputStateAndRefs(type).map { it.state.contractState }
    }

    /**
     * Obtains a single ledger transaction [ContractState] output that match the specified [ContractState] type.
     *
     * @param T The underlying type of the [ContractState].
     * @param type The type of the [ContractState].
     * @return Returns a single ledger transaction [ContractState] output that match the specified [ContractState] type.
     * @throws IllegalArgumentException if a single ledger transaction [ContractState] output that matches the specified [ContractState] type cannot be found.
     */
    fun <T : ContractState> getOutputState(type: Class<T>): T {
        return getOutputStates(type).singleOrNull() ?: throw IllegalArgumentException(
            "Failed to obtain a single ledger transaction output state with the specified type: ${type.canonicalName}."
        )
    }

    // endregion

    // region Grouped States

    /**
     * Obtains groups of ledger transaction [StateAndRef] inputs and outputs that match the specified [ContractState] type, grouped by the specified selector key.
     *
     * @param T The underlying type of the [ContractState].
     * @param K The underlying type of the selector key.
     * @param type The type of the [ContractState].
     * @param selector The selector that will be common to all grouped [StateAndRef] inputs and outputs.
     * @return Returns groups of ledger transaction [StateAndRef] inputs and outputs that match the specified [ContractState] type, grouped by the specified selector key.
     */
    fun <T : ContractState, K : Any> getGroupedStates(type: Class<T>, selector: (StateAndRef<T>) -> K): List<InputOutputGroup<T, K>> {
        val inputStateAndRefs: Iterable<StateAndRef<T>> = getInputStateAndRefs(type)
        val outputStateAndRefs: Iterable<StateAndRef<T>> = getOutputStateAndRefs(type)

        val inputGroups: Map<K, List<StateAndRef<T>>> = inputStateAndRefs.groupBy(selector)
        val outputGroups: Map<K, List<StateAndRef<T>>> = outputStateAndRefs.groupBy(selector)

        val mappedInputGroupEntries: List<InputOutputGroup<T, K>> = inputGroups.entries.map {
            InputOutputGroup(it.value, outputGroups[it.key] ?: emptyList(), it.key)
        }

        val mappedOutputGroupEntries: List<InputOutputGroup<T, K>> = outputGroups.entries.map {
            InputOutputGroup(inputGroups[it.key] ?: emptyList(), it.value, it.key)
        }

        return (mappedInputGroupEntries + mappedOutputGroupEntries).distinct()
    }

    // endregion
}

// region Input States

/**
 * Obtains all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [StateAndRef] inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getInputStateAndRefs(): List<StateAndRef<T>> {
    return getInputStateAndRefs(T::class.java)
}

/**
 * Obtains a single ledger transaction [StateAndRef] input that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a single ledger transaction [StateAndRef] input that match the specified [ContractState] type.
 * @throws IllegalArgumentException if a single ledger transaction [StateAndRef] input that matches the specified [ContractState] type cannot be found.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getInputStateAndRef(): StateAndRef<T> {
    return getInputStateAndRef(T::class.java)
}

/**
 * Obtains all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [ContractState] inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getInputStates(): List<T> {
    return getInputStates(T::class.java)
}

/**
 * Obtains a single ledger transaction [ContractState] input that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a single ledger transaction [ContractState] input that match the specified [ContractState] type.
 * @throws IllegalArgumentException if a single ledger transaction [ContractState] input that matches the specified [ContractState] type cannot be found.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getInputState(): T {
    return getInputState(T::class.java)
}

// endregion

// region Reference Input States

/**
 * Obtains all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [StateAndRef] reference inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getReferenceInputStateAndRefs(): List<StateAndRef<T>> {
    return getReferenceInputStateAndRefs(T::class.java)
}

/**
 * Obtains a single ledger transaction [StateAndRef] reference input that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a single ledger transaction [StateAndRef] reference input that match the specified [ContractState] type.
 * @throws IllegalArgumentException if a single ledger transaction [StateAndRef] reference input that matches the specified [ContractState] type cannot be found.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getReferenceInputStateAndRef(): StateAndRef<T> {
    return getReferenceInputStateAndRef(T::class.java)
}

/**
 * Obtains all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [ContractState] reference inputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getReferenceInputStates(): List<T> {
    return getReferenceInputStates(T::class.java)
}

/**
 * Obtains a single ledger transaction [ContractState] reference input that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a single ledger transaction [ContractState] reference input that match the specified [ContractState] type.
 * @throws IllegalArgumentException if a single ledger transaction [ContractState] reference input that matches the specified [ContractState] type cannot be found.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getReferenceInputState(): T {
    return getReferenceInputState(T::class.java)
}

// endregion

// region Output States

/**
 * Obtains all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [StateAndRef] outputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getOutputStateAndRefs(): List<StateAndRef<T>> {
    return getOutputStateAndRefs(T::class.java)
}

/**
 * Obtains a single ledger transaction [StateAndRef] output that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a single ledger transaction [StateAndRef] output that match the specified [ContractState] type.
 * @throws IllegalArgumentException if a single ledger transaction [StateAndRef] output that matches the specified [ContractState] type cannot be found.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getOutputStateAndRef(): StateAndRef<T> {
    return getOutputStateAndRef(T::class.java)
}

/**
 * Obtains all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns all ledger transaction [ContractState] outputs that match the specified [ContractState] type.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getOutputStates(): List<T> {
    return getOutputStates(T::class.java)
}

/**
 * Obtains a single ledger transaction [ContractState] output that match the specified [ContractState] type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a single ledger transaction [ContractState] output that match the specified [ContractState] type.
 * @throws IllegalArgumentException if a single ledger transaction [ContractState] output that matches the specified [ContractState] type cannot be found.
 */
inline fun <reified T : ContractState> UtxoLedgerTransaction.getOutputState(): T {
    return getOutputState(T::class.java)
}

// endregion

// region Grouped States

/**
 * Obtains groups of ledger transaction [StateAndRef] inputs and outputs that match the specified [ContractState] type, grouped by the specified selector key.
 *
 * @param T The underlying type of the [ContractState].
 * @param K The underlying type of the selector key.
 * @param selector The selector that will be common to all grouped [StateAndRef] inputs and outputs.
 * @return Returns groups of ledger transaction [StateAndRef] inputs and outputs that match the specified [ContractState] type, grouped by the specified selector key.
 */
inline fun <reified T : ContractState, K : Any> UtxoLedgerTransaction.getGroupedStates(
    noinline selector: (StateAndRef<T>) -> K
): List<InputOutputGroup<T, K>> {
    return getGroupedStates(T::class.java, selector)
}

// endregion
