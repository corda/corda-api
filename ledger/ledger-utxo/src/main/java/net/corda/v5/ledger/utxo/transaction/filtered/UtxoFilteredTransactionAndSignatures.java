package net.corda.v5.ledger.utxo.transaction.filtered;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;

import java.util.List;

/**
 * An interface for storing a filtered transaction and its signatures.
 */
@CordaSerializable
@DoNotImplement
public interface UtxoFilteredTransactionAndSignatures {

    /**
     * Gets the filtered transaction.
     *
     * @return A {@link UtxoFilteredTransaction} object.
     */
    UtxoFilteredTransaction getFilteredTransaction();

    /**
     * Gets the signatures that belong to the filtered transaction.
     *
     * @return A list of signatures represented by {@link DigitalSignatureAndMetadata}s.
     */
    List<DigitalSignatureAndMetadata> getSignatures();

}
