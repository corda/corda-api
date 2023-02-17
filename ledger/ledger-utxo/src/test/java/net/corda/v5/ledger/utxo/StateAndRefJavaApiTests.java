package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public final class StateAndRefJavaApiTests extends AbstractMockTestHarness {

    public StateAndRefJavaApiTests() throws IOException {
    }

    @Test
    public void getStateShouldReturnTheExpectedValue() {
        TransactionState<ContractState> value = contractStateAndRef.getState();
        Assertions.assertEquals(contractTransactionState, value);
    }

    @Test
    public void getRefShouldReturnTheExpectedValue() {
        StateRef value = contractStateAndRef.getRef();
        Assertions.assertEquals(stateRef, value);
    }
}
