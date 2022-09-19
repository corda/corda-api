package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class StaticPointerJavaApiTests extends AbstractMockTestHarness {

    @Test
    public void getIdentifierShouldReturnTheExpectedValue() {
        StateRef value = staticPointer.getIdentifier();
        Assertions.assertEquals(stateRef, value);
    }

    @Test
    public void getTypeShouldReturnTheExpectedValue() {
        Class<ContractState> value = staticPointer.getType();
        Assertions.assertEquals(ContractState.class, value);
    }

    @Test
    public void isResolvedShouldReturnTheExpectedValue() {
        boolean value = staticPointer.isResolved();
        Assertions.assertFalse(value);
    }
}
