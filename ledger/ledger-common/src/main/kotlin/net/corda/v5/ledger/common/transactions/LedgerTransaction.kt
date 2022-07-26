package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash

@DoNotImplement
@CordaSerializable // TODO(???)
interface LedgerTransaction {
    val id: SecureHash
    val privacySalt: PrivacySalt
    val wireTransaction: WireTransaction
    val metadata: TransactionMetaData
}