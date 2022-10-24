package net.corda.v5.ledger.notary.pluggable

import net.corda.v5.ledger.common.Party
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction

/**
 * This interface provides a service that will be used in notary selection logic to select the proper notary plugin.
 */
interface PluggableNotaryClientFlowFactory {
    fun create(notary: Party, type: String, stx: UtxoSignedTransaction): PluggableNotaryClientFlow
}