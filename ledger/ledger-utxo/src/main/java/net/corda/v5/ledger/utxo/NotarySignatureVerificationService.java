package net.corda.v5.ledger.utxo;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.common.transaction.TransactionSignatureException;
import net.corda.v5.ledger.common.transaction.TransactionWithMetadata;
import org.jetbrains.annotations.NotNull;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@DoNotImplement
public interface NotarySignatureVerificationService {

    /**
     * Verifies given transaction by id is signed by the notary signatures.
     *
     * @param transaction that is expected to be signed over
     * @param notaryKey that is expected to be signed with
     * @param signatures to verify
     * @param keyIdToNotaryKeys a map of keyId to notaryKeys
     * @throws TransactionSignatureException if verification of given signatures failed.
     */
    void verifyNotarySignatures(
            @NotNull TransactionWithMetadata transaction,
            @NotNull PublicKey notaryKey,
            @NotNull List<DigitalSignatureAndMetadata> signatures,
            @NotNull Map<String, Map<SecureHash, PublicKey>> keyIdToNotaryKeys
    );
}