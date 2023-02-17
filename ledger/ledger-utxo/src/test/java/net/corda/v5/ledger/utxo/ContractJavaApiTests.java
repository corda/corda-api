package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public final class ContractJavaApiTests extends AbstractMockTestHarness {

    public ContractJavaApiTests() throws IOException {
    }

    @Test
    public void verifyShouldBeCallable() {
        contract.verify(utxoLedgerTransaction);
    }
}
