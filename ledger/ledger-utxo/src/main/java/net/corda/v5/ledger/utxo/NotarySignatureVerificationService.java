package net.corda.v5.ledger.utxo;

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.common.transaction.TransactionSignatureException;
import org.jetbrains.annotations.NotNull;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@DoNotImplement
public interface NotarySignatureVerificationService {

    /**
     * Verifies given transaction by id is signed by the notary signatures.
     *
     * @param transactionId of signatures
     * @param notaryKey that is expected to be signed with
     * @param signatures to verify
     * @param keyIdToNotaryKeys a map of keyId to notaryKeys
     * @throws TransactionSignatureException if verification of given signatures failed.
     */
    void verifyNotarySignatures(
            @NotNull SecureHash transactionId,
            @NotNull PublicKey notaryKey,
            @NotNull List<DigitalSignatureAndMetadata> signatures,
            @NotNull Map<String, Map<SecureHash, PublicKey>> keyIdToNotaryKeys
    );

    /**
     * Get notary public key by key id of signature.
     *
     * @param keyId of notary signature
     * @param notaryKey that is expected to be signed with
     * @param keyIdToNotaryKeys a map of keyId to notaryKeys
     */
    PublicKey getNotaryPublicKeyByKeyId(
            @NotNull SecureHash keyId,
            @NotNull PublicKey notaryKey,
            @NotNull Map<String, Map<SecureHash, PublicKey>> keyIdToNotaryKeys
    );
}
