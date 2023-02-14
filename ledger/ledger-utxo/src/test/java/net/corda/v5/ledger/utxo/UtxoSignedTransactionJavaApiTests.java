package net.corda.v5.ledger.utxo;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public final class UtxoSignedTransactionJavaApiTests extends AbstractMockTestHarness {

    @Test
    public void getTransactionIdShouldReturnTheExpectedValue() {
        SecureHash value = utxoSignedTransaction.getTransactionId();
        Assertions.assertEquals(hash, value);
    }

    @Test
    public void getSignaturesShouldReturnTheExpectedValue() {
        List<DigitalSignatureAndMetadata> value = utxoSignedTransaction.getSignatures();
        Assertions.assertEquals(signatures, value);
    }

    @Test
    public void toLedgerTransactionShouldReturnTheExpectedValue() {
        UtxoLedgerTransaction value = utxoSignedTransaction.toLedgerTransaction();
        Assertions.assertEquals(utxoLedgerTransaction, value);
    }
}
