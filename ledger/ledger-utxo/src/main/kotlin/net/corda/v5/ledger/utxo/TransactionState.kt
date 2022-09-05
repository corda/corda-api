package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

/**
 * Represents a transaction state, composed of a [ContractState] and associated [TransactionStateInformation].
 *
 * @constructor Creates a new instance of the [TransactionState] data class.
 * @param T The underlying type of the [ContractState] instance.
 * @property contractState The [ContractState] component of the current [TransactionState] instance.
 * @property information The [TransactionStateInformation] component of the current [TransactionState] instance.
 */
@CordaSerializable
data class TransactionState<out T : ContractState>(val contractState: T, val information: TransactionStateInformation) {

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     * @param constraint The [CpkConstraint] associated with the transaction state.
     */
    constructor(contractState: T, contract: String, notary: PublicKey, encumbrance: Int?, constraint: CpkConstraint) :
            this(contractState, TransactionStateInformation(contract, notary, encumbrance, constraint))

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     * @param constraint The [CpkConstraint] associated with the transaction state.
     */
    constructor(contractState: T, notary: PublicKey, encumbrance: Int?, constraint: CpkConstraint) :
            this(contractState, contractState.getContractClassNameOrThrow(), notary, encumbrance, constraint)

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     */
    constructor(contractState: T, notary: PublicKey) :
            this(contractState, notary, null, CpkConstraint.automaticPlaceholderCpkConstraint)

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     */
    constructor(contractState: T, notary: PublicKey, encumbrance: Int) :
            this(contractState, notary, encumbrance, CpkConstraint.automaticPlaceholderCpkConstraint)

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param constraint The [CpkConstraint] associated with the transaction state.
     */
    constructor(contractState: T, notary: PublicKey, constraint: CpkConstraint) :
            this(contractState, notary, null, constraint)

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     */
    constructor(contractState: T, contract: String, notary: PublicKey) :
            this(contractState, contract, notary, null, CpkConstraint.automaticPlaceholderCpkConstraint)

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     */
    constructor(contractState: T, contract: String, notary: PublicKey, encumbrance: Int) :
            this(contractState, contract, notary, encumbrance, CpkConstraint.automaticPlaceholderCpkConstraint)

    /**
     * Creates a new instance of the [TransactionState] data class.
     *
     * @param contractState The [ContractState] component of the current [TransactionState] instance.
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param constraint The [CpkConstraint] associated with the transaction state.
     */
    constructor(contractState: T, contract: String, notary: PublicKey, constraint: CpkConstraint) :
            this(contractState, contract, notary, null, constraint)
}

/**
 * Casts the current [TransactionState] to the specified type.
 *
 * @param T The underlying type of the [ContractState].
 * @param type The type of the [ContractState] to cast to.
 * @return Returns a new [TransactionState] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [TransactionState] cannot be cast to the specified type.
 */
fun <T : ContractState> TransactionState<*>.cast(type: Class<T>): TransactionState<T> {
    return TransactionState(contractState.cast(type), information)
}

/**
 * Casts the current [TransactionState] to the specified type.
 *
 * @param T The underlying type of the [ContractState].
 * @return Returns a new [TransactionState] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [TransactionState] cannot be cast to the specified type.
 */
inline fun <reified T : ContractState> TransactionState<*>.cast(): TransactionState<T> {
    return cast(T::class.java)
}
