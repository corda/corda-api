package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

public class ContractStateJavaApiTests extends Mock {

    @Test
    public void getParticipantsShouldReturnTheCorrectValue() {
        Iterable<PublicKey> value = contractState.getParticipants();
        Assertions.assertEquals(participants, value);
    }
}
