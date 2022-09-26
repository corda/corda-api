package net.corda.v5.ledger.utxo;

import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import org.mockito.Mockito;

import java.security.PublicKey;
import java.util.Set;

public class AbstractMockTestHarness {

    // Mocked APIs
    protected final PublicKey aliceKey = Mockito.mock(PublicKey.class);
    protected final PublicKey bobKey = Mockito.mock(PublicKey.class);
    protected final ContractState contractState = Mockito.mock(ContractState.class);
    protected final Contract contract = Mockito.mock(Contract.class);
    protected final UtxoLedgerTransaction utxoLedgerTransaction = Mockito.mock(UtxoLedgerTransaction.class);

    // Mocked Data
    protected final Set<PublicKey> participants = Set.of(aliceKey, bobKey);

    protected AbstractMockTestHarness() {
        initializeContractState();
        initializeContract();
    }

    private void initializeContractState() {
        Mockito.when(contractState.getParticipants()).thenReturn(participants);
    }

    private void initializeContract() {
        Mockito.doNothing().when(contract).verify(utxoLedgerTransaction);
    }
}
