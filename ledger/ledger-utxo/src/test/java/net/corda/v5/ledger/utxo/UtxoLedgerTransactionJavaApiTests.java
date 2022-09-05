package net.corda.v5.ledger.utxo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class UtxoLedgerTransactionJavaApiTests extends Mock {

    @Test
    public void getTimeWindowShouldReturnTheCorrectValue() {
        TimeWindow value = utxoLedgerTransaction.getTimeWindow();
        Assertions.assertEquals(timeWindow, value);
    }

    @Test
    public void getAttachmentsShouldReturnTheCorrectValue() {
        List<Attachment> value = utxoLedgerTransaction.getAttachments();
        Assertions.assertEquals(List.of(attachment), value);
    }

    @Test
    public void getCommandsShouldReturnTheCorrectValue() {
        List<CommandAndSignatories<?>> value = utxoLedgerTransaction.getCommands();
        Assertions.assertEquals(commands, value);
    }

    @Test
    public void getInputStateAndRefsShouldReturnTheCorrectValue() {
        List<StateAndRef<?>> value = utxoLedgerTransaction.getInputStateAndRefs();
        Assertions.assertEquals(contractStateAndRefs, value);
    }

    @Test
    public void getInputTransactionStatesShouldReturnTheCorrectValue() {
        List<TransactionState<?>> value = utxoLedgerTransaction.getInputTransactionStates();
        Assertions.assertEquals(getTransactionStates(contractStateAndRefs), value);
    }

    @Test
    public void getInputContractStatesShouldReturnTheCorrectValue() {
        List<ContractState> value = utxoLedgerTransaction.getInputContractStates();
        Assertions.assertEquals(contractStates, value);
    }

    @Test
    public void getReferenceInputStateAndRefsShouldReturnTheCorrectValue() {
        List<StateAndRef<?>> value = utxoLedgerTransaction.getReferenceInputStateAndRefs();
        Assertions.assertEquals(contractStateAndRefs, value);
    }

    @Test
    public void getReferenceInputTransactionStatesShouldReturnTheCorrectValue() {
        List<TransactionState<?>> value = utxoLedgerTransaction.getReferenceInputTransactionStates();
        Assertions.assertEquals(getTransactionStates(contractStateAndRefs), value);
    }

    @Test
    public void getReferenceInputContractStatesShouldReturnTheCorrectValue() {
        List<ContractState> value = utxoLedgerTransaction.getReferenceInputContractStates();
        Assertions.assertEquals(getContractStates(contractStateAndRefs), value);
    }

    @Test
    public void getOutputStateAndRefsShouldReturnTheCorrectValue() {
        List<StateAndRef<?>> value = utxoLedgerTransaction.getOutputStateAndRefs();
        Assertions.assertEquals(contractStateAndRefs, value);
    }

    @Test
    public void getOutputTransactionStatesShouldReturnTheCorrectValue() {
        List<TransactionState<?>> value = utxoLedgerTransaction.getOutputTransactionStates();
        Assertions.assertEquals(getTransactionStates(contractStateAndRefs), value);
    }

    @Test
    public void getOutputContractStatesShouldReturnTheCorrectValue() {
        List<ContractState> value = utxoLedgerTransaction.getOutputContractStates();
        Assertions.assertEquals(getContractStates(contractStateAndRefs), value);
    }

    @Test
    public void getAttachmentByIdShouldReturnTheCorrectValue() {
        Attachment value = utxoLedgerTransaction.getAttachment(secureHash);
        Assertions.assertEquals(attachment, value);
    }

    @Test
    public void getCommandAndSignatoriesOfTypeCreateShouldReturnTheCorrectValue() {
        CommandAndSignatories<Create> value = utxoLedgerTransaction.getCommandAndSignatories(Create.class);
        Assertions.assertEquals(createCommandAndSignatories, value);
    }

    @Test
    public void getCommandAndSignatoriesOfTypeUpdateShouldReturnTheCorrectValue() {
        CommandAndSignatories<Update> value = utxoLedgerTransaction.getCommandAndSignatories(Update.class);
        Assertions.assertEquals(updateCommandAndSignatories, value);
    }

    @Test
    public void getCommandsAndSignatoriesOfTypeCreateShouldReturnTheCorrectValue() {
        List<CommandAndSignatories<Create>> value = utxoLedgerTransaction.getCommandsAndSignatories(Create.class);
        Assertions.assertEquals(List.of(createCommandAndSignatories), value);
    }

    @Test
    public void getCommandsAndSignatoriesOfTypeUpdateShouldReturnTheCorrectValue() {
        List<CommandAndSignatories<Update>> value = utxoLedgerTransaction.getCommandsAndSignatories(Update.class);
        Assertions.assertEquals(List.of(updateCommandAndSignatories), value);
    }

    @Test
    public void getInputStateAndRefsOfTypeContractStateShouldReturnTheCorrectValue() {
        List<StateAndRef<ContractState>> value = utxoLedgerTransaction.getInputStateAndRefs(ContractState.class);
        Assertions.assertEquals(contractStateAndRefs, value);
    }

    @Test
    public void getInputStateAndRefsOfTypeFungibleStateShouldReturnTheCorrectValue() {
        List<StateAndRef<FungibleState>> value = utxoLedgerTransaction.getInputStateAndRefs(FungibleState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(fungibleState, 1)), value);
    }

    @Test
    public void getInputStateAndRefsOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<IdentifiableState>> value = utxoLedgerTransaction.getInputStateAndRefs(IdentifiableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(identifiableState, 2)), value);
    }

    @Test
    public void getInputStateAndRefsOfTypeIssuableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<IssuableState>> value = utxoLedgerTransaction.getInputStateAndRefs(IssuableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(issuableState, 3)), value);
    }

    @Test
    public void getInputStateAndRefsOfTypeBearableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<BearableState>> value = utxoLedgerTransaction.getInputStateAndRefs(BearableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(bearableState, 4)), value);
    }

    @Test
    public void getInputStateAndRefOfTypeContractStateShouldReturnTheCorrectValue() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> utxoLedgerTransaction.getInputStateAndRef(ContractState.class));
        Assertions.assertEquals("Failed to obtain a single ledger transaction input state and ref with the specified type: net.corda.v5.ledger.utxo.ContractState.", exception.getMessage());
    }

    @Test
    public void getInputStateAndRefOfTypeFungibleStateShouldReturnTheCorrectValue() {
        StateAndRef<FungibleState> value = utxoLedgerTransaction.getInputStateAndRef(FungibleState.class);
        Assertions.assertEquals(makeStateAndRef(fungibleState, 1), value);
    }

    @Test
    public void getInputStateAndRefOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        StateAndRef<IdentifiableState> value = utxoLedgerTransaction.getInputStateAndRef(IdentifiableState.class);
        Assertions.assertEquals(makeStateAndRef(identifiableState, 2), value);
    }

    @Test
    public void getInputStateAndRefOfTypeIssuableStateShouldReturnTheCorrectValue() {
        StateAndRef<IssuableState> value = utxoLedgerTransaction.getInputStateAndRef(IssuableState.class);
        Assertions.assertEquals(makeStateAndRef(issuableState, 3), value);
    }

    @Test
    public void getInputStateAndRefOfTypeBearableStateShouldReturnTheCorrectValue() {
        StateAndRef<BearableState> value = utxoLedgerTransaction.getInputStateAndRef(BearableState.class);
        Assertions.assertEquals(makeStateAndRef(bearableState, 4), value);
    }

    @Test
    public void getInputStatesOfTypeContractStateShouldReturnTheCorrectValue() {
        List<ContractState> value = utxoLedgerTransaction.getInputStates(ContractState.class);
        Assertions.assertEquals(contractStates, value);
    }

    @Test
    public void getInputStatesOfTypeFungibleStateShouldReturnTheCorrectValue() {
        List<FungibleState> value = utxoLedgerTransaction.getInputStates(FungibleState.class);
        Assertions.assertEquals(List.of(fungibleState), value);
    }

    @Test
    public void getInputStatesOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        List<IdentifiableState> value = utxoLedgerTransaction.getInputStates(IdentifiableState.class);
        Assertions.assertEquals(List.of(identifiableState), value);
    }

    @Test
    public void getInputStatesOfTypeIssuableStateShouldReturnTheCorrectValue() {
        List<IssuableState> value = utxoLedgerTransaction.getInputStates(IssuableState.class);
        Assertions.assertEquals(List.of(issuableState), value);
    }

    @Test
    public void getInputStatesOfTypeBearableStateShouldReturnTheCorrectValue() {
        List<BearableState> value = utxoLedgerTransaction.getInputStates(BearableState.class);
        Assertions.assertEquals(List.of(bearableState), value);
    }

    @Test
    public void getInputStateOfTypeContractStateShouldReturnTheCorrectValue() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> utxoLedgerTransaction.getInputState(ContractState.class));
        Assertions.assertEquals("Failed to obtain a single ledger transaction input state with the specified type: net.corda.v5.ledger.utxo.ContractState.", exception.getMessage());
    }

    @Test
    public void getInputStateOfTypeFungibleStateShouldReturnTheCorrectValue() {
        FungibleState<BigDecimal> value = utxoLedgerTransaction.getInputState(FungibleState.class);
        Assertions.assertEquals(fungibleState, value);
    }

    @Test
    public void getInputStateOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        IdentifiableState value = utxoLedgerTransaction.getInputState(IdentifiableState.class);
        Assertions.assertEquals(identifiableState, value);
    }

    @Test
    public void getInputStateOfTypeIssuableStateShouldReturnTheCorrectValue() {
        IssuableState value = utxoLedgerTransaction.getInputState(IssuableState.class);
        Assertions.assertEquals(issuableState, value);
    }

    @Test
    public void getInputStateOfTypeBearableStateShouldReturnTheCorrectValue() {
        BearableState value = utxoLedgerTransaction.getInputState(BearableState.class);
        Assertions.assertEquals(bearableState, value);
    }


    @Test
    public void getReferenceInputStateAndRefsOfTypeContractStateShouldReturnTheCorrectValue() {
        List<StateAndRef<ContractState>> value = utxoLedgerTransaction.getReferenceInputStateAndRefs(ContractState.class);
        Assertions.assertEquals(contractStateAndRefs, value);
    }

    @Test
    public void getReferenceInputStateAndRefsOfTypeFungibleStateShouldReturnTheCorrectValue() {
        List<StateAndRef<FungibleState>> value = utxoLedgerTransaction.getReferenceInputStateAndRefs(FungibleState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(fungibleState, 1)), value);
    }

    @Test
    public void getReferenceInputStateAndRefsOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<IdentifiableState>> value = utxoLedgerTransaction.getReferenceInputStateAndRefs(IdentifiableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(identifiableState, 2)), value);
    }

    @Test
    public void getReferenceInputStateAndRefsOfTypeIssuableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<IssuableState>> value = utxoLedgerTransaction.getReferenceInputStateAndRefs(IssuableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(issuableState, 3)), value);
    }

    @Test
    public void getReferenceInputStateAndRefsOfTypeBearableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<BearableState>> value = utxoLedgerTransaction.getReferenceInputStateAndRefs(BearableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(bearableState, 4)), value);
    }


    @Test
    public void getReferenceInputStateOfTypeBearableStateShouldReturnTheCorrectValue() {
        BearableState value = utxoLedgerTransaction.getReferenceInputState(BearableState.class);
        Assertions.assertEquals(bearableState, value);
    }

    @Test
    public void getReferenceInputStateAndRefOfTypeContractStateShouldReturnTheCorrectValue() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> utxoLedgerTransaction.getReferenceInputStateAndRef(ContractState.class));
        Assertions.assertEquals("Failed to obtain a single ledger transaction reference input state and ref with the specified type: net.corda.v5.ledger.utxo.ContractState.", exception.getMessage());
    }

    @Test
    public void getReferenceInputStateAndRefOfTypeFungibleStateShouldReturnTheCorrectValue() {
        StateAndRef<FungibleState> value = utxoLedgerTransaction.getReferenceInputStateAndRef(FungibleState.class);
        Assertions.assertEquals(makeStateAndRef(fungibleState, 1), value);
    }

    @Test
    public void getReferenceInputStateAndRefOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        StateAndRef<IdentifiableState> value = utxoLedgerTransaction.getReferenceInputStateAndRef(IdentifiableState.class);
        Assertions.assertEquals(makeStateAndRef(identifiableState, 2), value);
    }

    @Test
    public void getReferenceInputStateAndRefOfTypeIssuableStateShouldReturnTheCorrectValue() {
        StateAndRef<IssuableState> value = utxoLedgerTransaction.getReferenceInputStateAndRef(IssuableState.class);
        Assertions.assertEquals(makeStateAndRef(issuableState, 3), value);
    }

    @Test
    public void getReferenceInputStateAndRefOfTypeBearableStateShouldReturnTheCorrectValue() {
        StateAndRef<BearableState> value = utxoLedgerTransaction.getReferenceInputStateAndRef(BearableState.class);
        Assertions.assertEquals(makeStateAndRef(bearableState, 4), value);
    }

    @Test
    public void getReferenceInputStatesOfTypeContractStateShouldReturnTheCorrectValue() {
        List<ContractState> value = utxoLedgerTransaction.getReferenceInputStates(ContractState.class);
        Assertions.assertEquals(contractStates, value);
    }

    @Test
    public void getReferenceInputStatesOfTypeFungibleStateShouldReturnTheCorrectValue() {
        List<FungibleState> value = utxoLedgerTransaction.getReferenceInputStates(FungibleState.class);
        Assertions.assertEquals(List.of(fungibleState), value);
    }

    @Test
    public void getReferenceInputStatesOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        List<IdentifiableState> value = utxoLedgerTransaction.getReferenceInputStates(IdentifiableState.class);
        Assertions.assertEquals(List.of(identifiableState), value);
    }

    @Test
    public void getReferenceInputStatesOfTypeIssuableStateShouldReturnTheCorrectValue() {
        List<IssuableState> value = utxoLedgerTransaction.getReferenceInputStates(IssuableState.class);
        Assertions.assertEquals(List.of(issuableState), value);
    }

    @Test
    public void getReferenceInputStatesOfTypeBearableStateShouldReturnTheCorrectValue() {
        List<BearableState> value = utxoLedgerTransaction.getReferenceInputStates(BearableState.class);
        Assertions.assertEquals(List.of(bearableState), value);
    }

    @Test
    public void getReferenceInputStateOfTypeContractStateShouldReturnTheCorrectValue() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> utxoLedgerTransaction.getReferenceInputState(ContractState.class));
        Assertions.assertEquals("Failed to obtain a single ledger transaction reference input state with the specified type: net.corda.v5.ledger.utxo.ContractState.", exception.getMessage());
    }

    @Test
    public void getReferenceInputStateOfTypeFungibleStateShouldReturnTheCorrectValue() {
        FungibleState<BigDecimal> value = utxoLedgerTransaction.getReferenceInputState(FungibleState.class);
        Assertions.assertEquals(fungibleState, value);
    }

    @Test
    public void getReferenceInputStateOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        IdentifiableState value = utxoLedgerTransaction.getReferenceInputState(IdentifiableState.class);
        Assertions.assertEquals(identifiableState, value);
    }

    @Test
    public void getReferenceInputStateOfTypeIssuableStateShouldReturnTheCorrectValue() {
        IssuableState value = utxoLedgerTransaction.getReferenceInputState(IssuableState.class);
        Assertions.assertEquals(issuableState, value);
    }

    @Test
    public void getOutputStateAndRefsOfTypeContractStateShouldReturnTheCorrectValue() {
        List<StateAndRef<ContractState>> value = utxoLedgerTransaction.getOutputStateAndRefs(ContractState.class);
        Assertions.assertEquals(contractStateAndRefs, value);
    }

    @Test
    public void getOutputStateAndRefsOfTypeFungibleStateShouldReturnTheCorrectValue() {
        List<StateAndRef<FungibleState>> value = utxoLedgerTransaction.getOutputStateAndRefs(FungibleState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(fungibleState, 1)), value);
    }

    @Test
    public void getOutputStateAndRefsOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<IdentifiableState>> value = utxoLedgerTransaction.getOutputStateAndRefs(IdentifiableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(identifiableState, 2)), value);
    }

    @Test
    public void getOutputStateAndRefsOfTypeIssuableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<IssuableState>> value = utxoLedgerTransaction.getOutputStateAndRefs(IssuableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(issuableState, 3)), value);
    }

    @Test
    public void getOutputStateAndRefsOfTypeBearableStateShouldReturnTheCorrectValue() {
        List<StateAndRef<BearableState>> value = utxoLedgerTransaction.getOutputStateAndRefs(BearableState.class);
        Assertions.assertEquals(List.of(makeStateAndRef(bearableState, 4)), value);
    }

    @Test
    public void getOutputStateAndRefOfTypeContractStateShouldReturnTheCorrectValue() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> utxoLedgerTransaction.getOutputStateAndRef(ContractState.class));
        Assertions.assertEquals("Failed to obtain a single ledger transaction output state and ref with the specified type: net.corda.v5.ledger.utxo.ContractState.", exception.getMessage());
    }

    @Test
    public void getOutputStateAndRefOfTypeFungibleStateShouldReturnTheCorrectValue() {
        StateAndRef<FungibleState> value = utxoLedgerTransaction.getOutputStateAndRef(FungibleState.class);
        Assertions.assertEquals(makeStateAndRef(fungibleState, 1), value);
    }

    @Test
    public void getOutputStateAndRefOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        StateAndRef<IdentifiableState> value = utxoLedgerTransaction.getOutputStateAndRef(IdentifiableState.class);
        Assertions.assertEquals(makeStateAndRef(identifiableState, 2), value);
    }

    @Test
    public void getOutputStateAndRefOfTypeIssuableStateShouldReturnTheCorrectValue() {
        StateAndRef<IssuableState> value = utxoLedgerTransaction.getOutputStateAndRef(IssuableState.class);
        Assertions.assertEquals(makeStateAndRef(issuableState, 3), value);
    }

    @Test
    public void getOutputStateAndRefOfTypeBearableStateShouldReturnTheCorrectValue() {
        StateAndRef<BearableState> value = utxoLedgerTransaction.getOutputStateAndRef(BearableState.class);
        Assertions.assertEquals(makeStateAndRef(bearableState, 4), value);
    }

    @Test
    public void getOutputStatesOfTypeContractStateShouldReturnTheCorrectValue() {
        List<ContractState> value = utxoLedgerTransaction.getOutputStates(ContractState.class);
        Assertions.assertEquals(contractStates, value);
    }

    @Test
    public void getOutputStatesOfTypeFungibleStateShouldReturnTheCorrectValue() {
        List<FungibleState> value = utxoLedgerTransaction.getOutputStates(FungibleState.class);
        Assertions.assertEquals(List.of(fungibleState), value);
    }

    @Test
    public void getOutputStatesOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        List<IdentifiableState> value = utxoLedgerTransaction.getOutputStates(IdentifiableState.class);
        Assertions.assertEquals(List.of(identifiableState), value);
    }

    @Test
    public void getOutputStatesOfTypeIssuableStateShouldReturnTheCorrectValue() {
        List<IssuableState> value = utxoLedgerTransaction.getOutputStates(IssuableState.class);
        Assertions.assertEquals(List.of(issuableState), value);
    }

    @Test
    public void getOutputStatesOfTypeBearableStateShouldReturnTheCorrectValue() {
        List<BearableState> value = utxoLedgerTransaction.getOutputStates(BearableState.class);
        Assertions.assertEquals(List.of(bearableState), value);
    }

    @Test
    public void getOutputStateOfTypeContractStateShouldReturnTheCorrectValue() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> utxoLedgerTransaction.getOutputState(ContractState.class));
        Assertions.assertEquals("Failed to obtain a single ledger transaction output state with the specified type: net.corda.v5.ledger.utxo.ContractState.", exception.getMessage());
    }

    @Test
    public void getOutputStateOfTypeFungibleStateShouldReturnTheCorrectValue() {
        FungibleState<BigDecimal> value = utxoLedgerTransaction.getOutputState(FungibleState.class);
        Assertions.assertEquals(fungibleState, value);
    }

    @Test
    public void getOutputStateOfTypeIdentifiableStateShouldReturnTheCorrectValue() {
        IdentifiableState value = utxoLedgerTransaction.getOutputState(IdentifiableState.class);
        Assertions.assertEquals(identifiableState, value);
    }

    @Test
    public void getOutputStateOfTypeIssuableStateShouldReturnTheCorrectValue() {
        IssuableState value = utxoLedgerTransaction.getOutputState(IssuableState.class);
        Assertions.assertEquals(issuableState, value);
    }

    @Test
    public void getOutputStateOfTypeBearableStateShouldReturnTheCorrectValue() {
        BearableState value = utxoLedgerTransaction.getOutputState(BearableState.class);
        Assertions.assertEquals(bearableState, value);
    }
}
