@file:Suppress("UNUSED_PARAMETER")

package net.corda.v5.ledger.utxo.observer

/**
 * This tests validates the code example in the KDoc comments will compile
 */
class UtxoLedgerTokenStateObserverKotlinExample : UtxoLedgerTokenStateObserver<ExampleState> {

    override fun getStateType(): Class<ExampleState> {
        return ExampleState::class.java
    }
    
    override fun onCommit(state: ExampleState): UtxoToken {
        return UtxoToken(
            UtxoTokenPoolKey(ExampleState::class.java.name, state.issuer, state.currency),
            state.amount,
            UtxoTokenFilterFields()
        )
    }
}
