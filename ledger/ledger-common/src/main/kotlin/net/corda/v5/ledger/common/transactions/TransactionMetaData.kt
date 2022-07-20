package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
interface TransactionMetaData {
    val ledgerModel: String
    val ledgerVersion: String
    val CpkIdentifiers: Set<CpkIdentifier>
}