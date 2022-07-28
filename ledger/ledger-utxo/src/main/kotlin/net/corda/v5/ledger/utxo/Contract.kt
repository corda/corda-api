package net.corda.v5.ledger.utxo

interface Contract {
    companion object {
        val ID: String = Companion::class.java.canonicalName
    }

    fun verifyTransaction(transaction: Any)
}