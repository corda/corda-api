package net.corda.v5.ledger.utxo;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.common.transaction.TransactionSignatureException;
import net.corda.v5.ledger.common.transaction.TransactionSignatureService;
import net.corda.v5.ledger.utxo.transaction.filtered.UtxoFilteredTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@DoNotImplement
public interface NotarySignatureVerificationService {

    /**
     * Verifies the notary signatures attached to the current {@link UtxoFilteredTransaction}.
     *
     * @param transactionId of signatures
     * @param notaryKey that is expected to be signed with
     * @param signatures to verify
     * @param keyIdToNotaryKeys a map of keyId of notary to notaryKey
     * @param transactionSignatureService to verify signature
     * @throws TransactionSignatureException if the current {@link UtxoFilteredTransaction} fails to verify correctly.
     */
    void verifyNotarySignatures(
            @NotNull SecureHash transactionId,
            @NotNull PublicKey notaryKey,
            @NotNull List<DigitalSignatureAndMetadata> signatures,
            @NotNull Map<String, Map<SecureHash, PublicKey>> keyIdToNotaryKeys,
            @NotNull TransactionSignatureService transactionSignatureService
    );
}
