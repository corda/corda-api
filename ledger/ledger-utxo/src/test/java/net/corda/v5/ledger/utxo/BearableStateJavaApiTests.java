package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

public class BearableStateJavaApiTests extends Mock {

    @Test
    public void getParticipantsShouldReturnTheCorrectValue() {
        Iterable<PublicKey> value = bearableState.getParticipants();
        Assertions.assertEquals(participants, value);
    }

    @Test
    public void getBearerShouldReturnTheCorrectValue() {
        PublicKey value = bearableState.getBearer();
        Assertions.assertEquals(bob, value);
    }
}
