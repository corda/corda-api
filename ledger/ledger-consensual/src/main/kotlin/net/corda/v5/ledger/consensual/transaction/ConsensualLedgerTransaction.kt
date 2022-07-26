package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.ledger.common.transactions.LedgerTransaction
import net.corda.v5.ledger.consensual.ConsensualState
import net.corda.v5.ledger.consensual.Party
import java.time.Instant

@DoNotImplement
interface ConsensualLedgerTransaction: LedgerTransaction {
    val metadata: ConsensualTransactionMetaData
    val timestamp: Instant
    val requiredSigners: List<Party>
    val consensualStates: List<ConsensualState>
    val consensualStateTypes: List<String>
}