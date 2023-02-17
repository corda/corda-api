package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;

public final class ContractStateJavaApiTests extends AbstractMockTestHarness {

    public ContractStateJavaApiTests() throws IOException {
    }

    @Test
    public void getParticipantsShouldReturnTheExpectedValue() {
        List<PublicKey> value = contractState.getParticipants();
        Assertions.assertEquals(keys, value);
    }
}
