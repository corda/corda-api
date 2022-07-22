package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
@CordaSerializable
interface TransactionMetaData {
    val ledgerModel: String
    val ledgerVersion: String
    val CpkIdentifiers: Set<CpkIdentifier>
}