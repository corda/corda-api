package net.corda.v5.ledger.common.transaction;

import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Indicates that some aspect of the transaction named by {@link #txId} violates some rules.
 */
public final class TransactionVerificationException extends CordaRuntimeException {
    private final SecureHash txId;

    public TransactionVerificationException(@NotNull SecureHash txId, @NotNull String message, @Nullable Throwable cause) {
        super(message + ", transaction: " + txId, cause);
        this.txId = txId;
    }

    /**
     * getTxId.
     * @return the Merkle root hash (identifier) of the transaction that failed verification.
     */
    @NotNull
    public SecureHash getTxId() {
        return txId;
    }
}
