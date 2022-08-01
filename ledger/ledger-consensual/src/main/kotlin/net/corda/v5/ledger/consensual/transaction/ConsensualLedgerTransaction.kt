package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.ledger.common.transactions.LedgerTransaction
import net.corda.v5.ledger.consensual.ConsensualState
import java.time.Instant

@DoNotImplement
interface ConsensualLedgerTransaction: LedgerTransaction {
    val timestamp: Instant
    val states: List<ConsensualState>
}