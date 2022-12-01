package net.corda.v5.ledger.utxo.transaction;

import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.StateRef;
import net.corda.v5.ledger.utxo.TimeWindow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class UtxoFilteredTransactionJavaApiTest {

    protected final SecureHash hash = SecureHash.parse("SHA256:0000000000000000000000000000000000000000000000000000000000000000");

    @Test
    void canReadFilteredTransaction() {
        // Set up Filtered TX mock
        UtxoFilteredTransaction filteredTx = Mockito.mock(UtxoFilteredTransaction.class);

        // Notary, time window commands
        Mockito.when(filteredTx.getNotary()).thenReturn(null);
        Mockito.when(filteredTx.getTimeWindow()).thenReturn(Mockito.mock(TimeWindow.class));
        Mockito.when(filteredTx.getCommands()).thenReturn(Mockito.mock(UtxoFilteredData.UtxoFilteredDataRemoved.class));

        // Outputs are size only
        UtxoFilteredData.UtxoFilteredDataSizeOnly<StateAndRef<?>> outputs
                = Mockito.mock(UtxoFilteredData.UtxoFilteredDataSizeOnly.class);
        Mockito.when(outputs.getSize()).thenReturn(5);
        Mockito.when(filteredTx.getOutputStateAndRefs()).thenReturn(outputs);

        // Inputs is a filtered Audit proof
        FilteredEntry<StateRef> inputData = Mockito.mock(FilteredEntry.class);
        Mockito.when(inputData.getIndex()).thenReturn(1);
        Mockito.when(inputData.getValue()).thenReturn(new StateRef(hash, 0));
        UtxoFilteredData.UtxoFilteredDataAudit<StateRef> inputs =
                Mockito.mock(UtxoFilteredData.UtxoFilteredDataAudit.class);
        Mockito.when(inputs.getSize()).thenReturn(2);
        Mockito.when(inputs.getValues()).thenReturn(List.of(inputData));
        Mockito.when(filteredTx.getInputStateRefs()).thenReturn(inputs);


        UtxoFilteredData<Command> commands = filteredTx.getCommands();
        Assertions.assertThat(commands).isInstanceOf(UtxoFilteredData.UtxoFilteredDataRemoved.class);

        Assertions.assertThat(filteredTx.getNotary()).isNull();

        Assertions.assertThat(filteredTx.getTimeWindow()).isInstanceOf(TimeWindow.class);

        UtxoFilteredData<StateAndRef<?>> txOutputs = filteredTx.getOutputStateAndRefs();
        Assertions.assertThat(txOutputs).isInstanceOf(UtxoFilteredData.UtxoFilteredDataSizeOnly.class);
        UtxoFilteredData.UtxoFilteredDataSizeOnly<StateAndRef<?>> sizeOnlyOutputs = (UtxoFilteredData.UtxoFilteredDataSizeOnly<StateAndRef<?>>) txOutputs;
        Assertions.assertThat(sizeOnlyOutputs.getSize()).isEqualTo(5);

        UtxoFilteredData<StateRef> txInputs = filteredTx.getInputStateRefs();
        Assertions.assertThat(txInputs).isInstanceOf(UtxoFilteredData.UtxoFilteredDataAudit.class);
        UtxoFilteredData.UtxoFilteredDataAudit<StateRef> auditInputs
                = (UtxoFilteredData.UtxoFilteredDataAudit<StateRef>) txInputs;
        Assertions.assertThat(auditInputs.getSize()).isEqualTo(2);
        Assertions.assertThat(auditInputs.getValues()).hasSize(1);
        Assertions.assertThat(auditInputs.getValues().get(0).getIndex()).isEqualTo(1);
        Assertions.assertThat(auditInputs.getValues().get(0).getValue().getTransactionHash()).isEqualTo(hash);
    }
}
