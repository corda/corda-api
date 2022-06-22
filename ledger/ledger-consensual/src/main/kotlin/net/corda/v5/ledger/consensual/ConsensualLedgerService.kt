package net.corda.v5.ledger.consensual

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

@DoNotImplement
interface ConsensualLedgerService {
    @Suspendable
    fun double(n: Int): Int
}
