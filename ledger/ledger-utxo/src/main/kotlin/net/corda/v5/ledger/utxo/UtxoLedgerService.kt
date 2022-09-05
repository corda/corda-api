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

    // TODO : As per ledger conversation - we should have a service to resolve StateRef -> StateAndRef<T>
    @Suspendable
    fun <T : ContractState> resolve(stateRefs: List<StateRef>): List<StateAndRef<T>>

    // TODO : As per ledger conversation - we should have a service to verify state refs
    @Suspendable
    fun <T : ContractState> verify(stateAndRefs: List<StateAndRef<T>>)
}
