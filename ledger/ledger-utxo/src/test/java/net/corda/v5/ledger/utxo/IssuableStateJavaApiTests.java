package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

public class IssuableStateJavaApiTests extends Mock {

    @Test
    public void getParticipantsShouldReturnTheCorrectValue() {
        Iterable<PublicKey> value = issuableState.getParticipants();
        Assertions.assertEquals(participants, value);
    }

    @Test
    public void getIssuerShouldReturnTheCorrectValue() {
        PublicKey value = issuableState.getIssuer();
        Assertions.assertEquals(alice, value);
    }
}
