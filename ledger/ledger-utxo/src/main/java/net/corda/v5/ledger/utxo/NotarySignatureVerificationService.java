package net.corda.v5.ledger.utxo;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.ledger.common.transaction.TransactionSignatureException;
import net.corda.v5.ledger.utxo.transaction.filtered.UtxoFilteredTransaction;

@DoNotImplement
public interface NotarySignatureVerificationService {

    /**
     * Verifies the notary signatures attached to the current {@link UtxoFilteredTransaction}.
     *
     * @throws TransactionSignatureException if the current {@link UtxoFilteredTransaction} fails to verify correctly.
     */
    void verifyNotarySignatures();
}
