package net.corda.v5.ledger.notary.pluggable

import net.corda.v5.ledger.common.Party
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction

/**
 * This interface is used to instantiate the client-side logic (flow) of the plugin.
 * This is needed because the plugins will be installed on node startup.
 *
 * Implementers of this interface must have the [PluggableNotaryType] annotation,
 * otherwise an exception will be thrown on startup.
 *
 * The client-side of the plugin must have the same constructor parameters as the
 * [create] function (notary, stx, context).
 */
interface PluggableNotaryClientFlowProvider {
    fun create(notary: Party, stx: UtxoSignedTransaction): PluggableNotaryClientFlow
}
