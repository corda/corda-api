package net.corda.v5.ledger.utxo

/**
 * Defines a contract state that is fungible.
 *
 * The expectation is that fungible state implementations can be split, merged, and are mutually interchangeable with
 * other instances of the same fungible state type.
 *
 * @param T The underlying numeric type of the quantity.
 * @property quantity The quantity of the current fungible state.
 */
interface FungibleState<T : Number> : ContractState {
    val quantity: T
}
