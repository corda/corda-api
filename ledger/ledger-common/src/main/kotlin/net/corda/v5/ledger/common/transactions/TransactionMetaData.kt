package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
@CordaSerializable
interface TransactionMetaData : Map<String, Any>

const val TRANSACTION_META_DATA_LEDGER_MODEL_KEY = "ledgerModel"
const val TRANSACTION_META_DATA_LEDGER_VERSION_KEY = "ledgerVersion"
const val TRANSACTION_META_DATA_CPK_IDENTIFIERS_KEY = "cpkIdentifiers"
