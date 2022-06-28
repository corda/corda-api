package net.corda.v5.ledger.consensual

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

/**
 * A service providing a ledger that operates based on consensual states rather than
 * the UTXO model.
 */
@DoNotImplement
interface ConsensualLedgerService {
    @Suspendable
    fun double(n: Int): Int
}
