package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.PublicKey;

public class FungibleStateJavaApiTests extends AbstractMockTestHarness {

    @Test
    @SuppressWarnings("unchecked")
    public void canCreateFungibleStateOfTypeByte() {
        FungibleState<Byte> state = Mockito.mock(FungibleState.class);
        Mockito.when(state.getQuantity()).thenReturn(Byte.MAX_VALUE);
        byte quantity = state.getQuantity();

        Assertions.assertEquals(Byte.MAX_VALUE, quantity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canCreateFungibleStateOfTypeShort() {
        FungibleState<Short> state = Mockito.mock(FungibleState.class);
        Mockito.when(state.getQuantity()).thenReturn(Short.MAX_VALUE);
        short quantity = state.getQuantity();

        Assertions.assertEquals(Short.MAX_VALUE, quantity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canCreateFungibleStateOfTypeInteger() {
        FungibleState<Integer> state = Mockito.mock(FungibleState.class);
        Mockito.when(state.getQuantity()).thenReturn(Integer.MAX_VALUE);
        int quantity = state.getQuantity();

        Assertions.assertEquals(Integer.MAX_VALUE, quantity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canCreateFungibleStateOfTypeLong() {
        FungibleState<Long> state = Mockito.mock(FungibleState.class);
        Mockito.when(state.getQuantity()).thenReturn(Long.MAX_VALUE);
        long quantity = state.getQuantity();

        Assertions.assertEquals(Long.MAX_VALUE, quantity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canCreateFungibleStateOfTypeBigInteger() {
        FungibleState<BigInteger> state = Mockito.mock(FungibleState.class);
        Mockito.when(state.getQuantity()).thenReturn(BigInteger.TEN);
        BigInteger quantity = state.getQuantity();

        Assertions.assertEquals(BigInteger.TEN, quantity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canCreateFungibleStateOfTypeBigDecimal() {
        FungibleState<BigDecimal> state = Mockito.mock(FungibleState.class);
        Mockito.when(state.getQuantity()).thenReturn(BigDecimal.TEN);
        BigDecimal quantity = state.getQuantity();

        Assertions.assertEquals(BigDecimal.TEN, quantity);
    }

    @Test
    public void getParticipantsShouldReturnTheCorrectValue() {
        Iterable<PublicKey> value = fungibleState.getParticipants();
        Assertions.assertEquals(keys, value);
    }

    @Test
    public void getQuantityShouldReturnTheCorrectValue() {
        BigDecimal value = fungibleState.getQuantity();
        Assertions.assertEquals(quantity, value);
    }
}
