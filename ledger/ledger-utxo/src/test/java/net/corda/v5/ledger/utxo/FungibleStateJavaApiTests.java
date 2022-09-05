package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.PublicKey;

public class FungibleStateJavaApiTests extends Mock {

    @Test
    public void getParticipantsShouldReturnTheCorrectValue() {
        Iterable<PublicKey> value = fungibleState.getParticipants();
        Assertions.assertEquals(participants, value);
    }

    @Test
    public void getQuantityShouldReturnTheCorrectValue() {
        BigDecimal value = fungibleState.getQuantity();
        Assertions.assertEquals(quantity, value);
    }
}
