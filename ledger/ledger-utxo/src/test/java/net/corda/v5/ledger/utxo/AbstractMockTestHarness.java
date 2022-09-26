package net.corda.v5.ledger.utxo;

import org.mockito.Mockito;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Set;
import java.util.UUID;

public class AbstractMockTestHarness {

    // Mocked APIs
    protected final PublicKey aliceKey = Mockito.mock(PublicKey.class);
    protected final PublicKey bobKey = Mockito.mock(PublicKey.class);
    protected final ContractState contractState = Mockito.mock(ContractState.class);

    // Mocked Data
    protected final Set<PublicKey> participants = Set.of(aliceKey, bobKey);
    protected final UUID id = UUID.fromString("00000000-0000-4000-0000-000000000000");
    protected final BigDecimal quantity = BigDecimal.valueOf(123.45);

    protected AbstractMockTestHarness() {
        initializeContractState();
    }

    private void initializeContractState() {
        Mockito.when(contractState.getParticipants()).thenReturn(participants);
    }
}
