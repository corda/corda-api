package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public final class IdentifiablePointerJavaApiTests extends AbstractMockTestHarness {

    @Test
    public void getIdentifierShouldReturnTheExpectedValue() {
        UUID value = identifiablePointer.getIdentifier();
        Assertions.assertEquals(id, value);
    }

    @Test
    public void getTypeShouldReturnTheExpectedValue() {
        Class<IdentifiableState> value = identifiablePointer.getType();
        Assertions.assertEquals(IdentifiableState.class, value);
    }

    @Test
    public void isResolvedShouldReturnTheExpectedValue() {
        boolean value = identifiablePointer.isResolved();
        Assertions.assertFalse(value);
    }
}
