package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
@CordaSerializable
interface TransactionMetaData{
    operator fun get(key: String): Any?
    val entries: Set<Map.Entry<String, Any>>

    companion object {
        const val LEDGER_MODEL_KEY = "ledgerModel"
        const val LEDGER_VERSION_KEY = "ledgerVersion"
        const val CPK_IDENTIFIERS_KEY = "cpkIdentifiers"
    }
}