package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

/**
 * Defines UTXO ledger services.
 */
@DoNotImplement
interface UtxoLedgerService {

    /**
     * Gets a UTXO transaction builder
     *
     * @return Returns a new [UtxoTransactionBuilder] instance.
     */
    @Suspendable
    fun getTransactionBuilder(): UtxoTransactionBuilder
}
