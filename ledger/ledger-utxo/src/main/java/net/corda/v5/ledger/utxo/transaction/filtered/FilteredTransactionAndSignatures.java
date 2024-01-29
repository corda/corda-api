package net.corda.v5.ledger.utxo.transaction.filtered;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CordaSerializable
@DoNotImplement
public interface FilteredTransactionAndSignatures {

    @NotNull
    UtxoFilteredTransaction getFilteredTransaction();

    @NotNull
    List<DigitalSignatureAndMetadata> getSignatures();
}
