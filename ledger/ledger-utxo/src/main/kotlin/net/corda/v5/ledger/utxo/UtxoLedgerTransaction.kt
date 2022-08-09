package net.corda.v5.ledger.utxo

/**
 *
 */
interface UtxoLedgerTransaction {
    fun <T : ContractState> getInputsOfType(type: Class<T>): List<T>
    fun <T : ContractState> getOutputsOfType(type: Class<T>): List<T>
}
