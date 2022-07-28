package net.corda.v5.ledger.utxo

interface FungibleState<T : Number> : ContractState {
    val quantity: T
}
