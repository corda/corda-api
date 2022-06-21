package net.corda.v5.ledger.models

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

@DoNotImplement
interface ConsensualStatesLedger {
    @Suspendable
    fun double(n: Int): Int
}