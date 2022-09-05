package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.util.UUID;

public class IdentifiableStateJavaApiTests extends Mock {

    @Test
    public void getParticipantsShouldReturnTheCorrectValue() {
        Iterable<PublicKey> value = identifiableState.getParticipants();
        Assertions.assertEquals(participants, value);
    }

    @Test
    public void getIdShouldReturnTheCorrectValue() {
        UUID value = identifiableState.getId();
        Assertions.assertEquals(id, value);
    }
}
