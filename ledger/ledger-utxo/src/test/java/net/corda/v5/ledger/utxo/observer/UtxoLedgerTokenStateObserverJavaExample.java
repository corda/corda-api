package net.corda.v5.ledger.utxo.observer;

import net.corda.v5.ledger.utxo.StateAndRef;
import org.jetbrains.annotations.NotNull;

/**
 * This tests validates the code example in the KDoc comments will compile
 */

public class UtxoLedgerTokenStateObserverJavaExample implements UtxoLedgerTokenStateObserver<ExampleStateJ> {

    @NotNull
    @Override
    public Class<ExampleStateJ> getStateType() {
        return ExampleStateJ.class;
    }

    @NotNull
    @Override
    public UtxoToken onProduced(@NotNull StateAndRef<? extends ExampleStateJ> stateAndRef) {
        ExampleStateJ state = stateAndRef.getState().getContractState();

        return new UtxoToken(
                state.amount,
                new UtxoTokenFilterFields()
        );
    }

    @NotNull
    @Override
    public UtxoTokenPoolKey getTokenPoolKey(@NotNull StateAndRef<? extends ExampleStateJ> stateAndRef) {
        ExampleStateJ state = stateAndRef.getState().getContractState();
        return new UtxoTokenPoolKey(ExampleStateK.class.getName(), state.issuer, state.currency);
    }
}