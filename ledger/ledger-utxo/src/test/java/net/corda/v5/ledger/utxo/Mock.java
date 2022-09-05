package net.corda.v5.ledger.utxo;

import net.corda.v5.crypto.SecureHash;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

abstract class Mock {

    // Mocked API Types
    protected final PublicKey alice = Mockito.mock(PublicKey.class);
    protected final PublicKey bob = Mockito.mock(PublicKey.class);
    protected final PublicKey notary = Mockito.mock(PublicKey.class);
    protected final Attachment attachment = Mockito.mock(Attachment.class);
    protected final ContractState contractState = Mockito.mock(ContractState.class);
    protected final FungibleState<BigDecimal> fungibleState = Mockito.mock(FungibleState.class);
    protected final IdentifiableState identifiableState = Mockito.mock(IdentifiableState.class);
    protected final IssuableState issuableState = Mockito.mock(IssuableState.class);
    protected final BearableState bearableState = Mockito.mock(BearableState.class);
    protected final UtxoLedgerTransaction utxoLedgerTransaction = Mockito.mock(UtxoLedgerTransaction.class);

    // TODO : Mocks
    protected final UtxoLedgerService utxoLedgerService = Mockito.mock(UtxoLedgerService.class);
    protected final UtxoSignedTransaction utxoSignedTransaction = Mockito.mock(UtxoSignedTransaction.class);
    protected final UtxoTransactionBuilder utxoTransactionBuilder = Mockito.mock(UtxoTransactionBuilder.class);
    protected final VerifiableCommand verifiableCommand = Mockito.mock(VerifiableCommand.class); // TODO ???

    // Mock Data
    protected final Set<PublicKey> participants = Set.of(alice, bob);
    protected final BigDecimal quantity = BigDecimal.valueOf(123.45);
    protected final UUID id = UUID.fromString("66870685-0e1e-43a1-88c9-0aeb6cc10b18");
    protected final Instant from = Instant.MIN;
    protected final Instant until = Instant.MAX;
    protected final Instant midpoint = from.plus(Duration.between(from, until).dividedBy(2));
    protected final Duration duration = Duration.between(from, until);
    protected final TimeWindow timeWindow = TimeWindow.between(from, until);
    protected final SecureHash secureHash = SecureHash.parse("SHA256:0000000000000000000000000000000000000000000000000000000000000000");
    protected final Create createCommand = new Create();
    protected final Update updateCommand = new Update();
    protected final CommandAndSignatories<Create> createCommandAndSignatories = new CommandAndSignatories<>(createCommand, Set.of(alice));
    protected final CommandAndSignatories<Update> updateCommandAndSignatories = new CommandAndSignatories<>(updateCommand, Set.of(bob));
    protected final List<CommandAndSignatories<?>> commands = List.of(createCommandAndSignatories, updateCommandAndSignatories);
    protected final List<ContractState> contractStates = List.of(contractState, fungibleState, identifiableState, issuableState, bearableState);
    protected final List<StateAndRef<?>> contractStateAndRefs = createStateAndRefs();

    protected Mock() {
        initializeAttachment();
        initializeContractState();
        initializeFungibleState();
        initializeIdentifiableState();
        initializeIssuableState();
        initializeBearableState();
        initializeUtxoLedgerTransaction();
    }

    protected <T extends ContractState> TransactionState<T> makeTransactionState(T state) {
        return new TransactionState<>(state, "TEST_CONTRACT_ID", notary);
    }

    protected <T extends ContractState> StateAndRef<T> makeStateAndRef(T state, int index) {
        TransactionState<T> transactionState = makeTransactionState(state);
        StateRef ref = new StateRef(secureHash, index);
        return new StateAndRef<>(transactionState, ref);
    }

    protected List<StateAndRef<?>> createStateAndRefs() {
        List<StateAndRef<?>> result = new ArrayList<>();

        for (int index = 0; index < contractStates.size(); index++) {
            result.add(makeStateAndRef(contractStates.get(index), index));
        }

        return result;
    }

    protected List<TransactionState<?>> getTransactionStates(List<StateAndRef<?>> stateAndRefs) {
        List<TransactionState<?>> result = new ArrayList<>();

        for (StateAndRef<?> stateAndRef : stateAndRefs) {
            result.add(stateAndRef.getState());
        }

        return result;
    }

    protected List<ContractState> getContractStates(List<StateAndRef<?>> stateAndRefs) {
        List<ContractState> result = new ArrayList<>();

        for (StateAndRef<?> stateAndRef : stateAndRefs) {
            result.add(stateAndRef.getState().getContractState());
        }

        return result;
    }

    private void initializeAttachment() {
        Mockito.when(attachment.getId()).thenReturn(secureHash);
    }

    private void initializeContractState() {
        Mockito.when(contractState.getParticipants()).thenReturn(participants);
    }

    private void initializeFungibleState() {
        Mockito.when(fungibleState.getParticipants()).thenReturn(participants);
        Mockito.when(fungibleState.getQuantity()).thenReturn(quantity);
    }

    private void initializeIdentifiableState() {
        Mockito.when(identifiableState.getParticipants()).thenReturn(participants);
        Mockito.when(identifiableState.getId()).thenReturn(id);
    }

    private void initializeIssuableState() {
        Mockito.when(issuableState.getParticipants()).thenReturn(participants);
        Mockito.when(issuableState.getIssuer()).thenReturn(alice);
    }

    private void initializeBearableState() {
        Mockito.when(bearableState.getParticipants()).thenReturn(participants);
        Mockito.when(bearableState.getBearer()).thenReturn(bob);
    }

    private void initializeUtxoLedgerTransaction() {
        Mockito.when(utxoLedgerTransaction.getTimeWindow()).thenReturn(timeWindow);
        Mockito.when(utxoLedgerTransaction.getAttachments()).thenReturn(List.of(attachment));
        Mockito.when(utxoLedgerTransaction.getCommands()).thenReturn(commands);

        Mockito.when(utxoLedgerTransaction.getInputStateAndRefs()).thenReturn(contractStateAndRefs);
        Mockito.when(utxoLedgerTransaction.getInputTransactionStates()).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputContractStates()).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRefs()).thenReturn(contractStateAndRefs);
        Mockito.when(utxoLedgerTransaction.getReferenceInputTransactionStates()).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputContractStates()).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getOutputStateAndRefs()).thenReturn(contractStateAndRefs);
        Mockito.when(utxoLedgerTransaction.getOutputTransactionStates()).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputContractStates()).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getAttachment(secureHash)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getCommandAndSignatories(Create.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getCommandAndSignatories(Update.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getCommandsAndSignatories(Create.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getCommandsAndSignatories(Update.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getInputState(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputState(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputState(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputState(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputState(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getInputStateAndRef(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRef(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRef(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRef(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRef(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getInputStates(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStates(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStates(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStates(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStates(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getInputStateAndRefs(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRefs(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRefs(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRefs(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getInputStateAndRefs(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getReferenceInputState(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputState(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputState(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputState(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputState(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRef(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRef(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRef(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRef(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRef(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getReferenceInputStates(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStates(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStates(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStates(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStates(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRefs(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRefs(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRefs(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRefs(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getReferenceInputStateAndRefs(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getOutputState(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputState(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputState(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputState(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputState(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getOutputStateAndRef(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRef(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRef(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRef(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRef(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getOutputStates(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStates(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStates(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStates(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStates(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getOutputStateAndRefs(ContractState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRefs(FungibleState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRefs(IdentifiableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRefs(IssuableState.class)).thenCallRealMethod();
        Mockito.when(utxoLedgerTransaction.getOutputStateAndRefs(BearableState.class)).thenCallRealMethod();

        Mockito.when(utxoLedgerTransaction.getGroupedStates(IdentifiableState.class, it -> it.getState().getContractState().getId())).thenCallRealMethod();
    }

    protected static class Create implements Command {
    }

    protected static class Update implements Command {
    }
}
