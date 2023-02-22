package net.corda.v5.ledger.notary.plugin.core;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Representation of errors that can be returned by the notary (plugin). This is only a marker interface, the plugins
 * can define their own errors by implementing this interface. Please refer to the non-validating notary plugin for
 * a more detailed example.
 */
@CordaSerializable
public abstract class NotaryException extends CordaRuntimeException {

    @NotNull
    private final String notaryErrorMessage;
    @Nullable
    private final SecureHash txId;

    /**
     * @return the specific error message produced by the notary.
     */
    @NotNull
    public final String getNotaryErrorMessage() {
        return this.notaryErrorMessage;
    }

    /**
     * @return txId Id of the transaction to be notarised. Can be _null_ if an error occurred before the id could be
     * resolved.
     */
    @Nullable
    public final SecureHash getTxId() {
        return this.txId;
    }


    private NotaryException(@NotNull String notaryErrorMessage, @Nullable SecureHash txId) {
        super("Unable to notarise transaction " + (txId != null ? txId : "<Unknown> : ") + notaryErrorMessage);
        this.notaryErrorMessage = notaryErrorMessage;
        this.txId = txId;
    }

    public abstract static class NotaryExceptionFatal extends NotaryException {
        public NotaryExceptionFatal(@NotNull String notaryErrorMessage, @Nullable SecureHash txId) {
            super(notaryErrorMessage, txId);
        }
    }

    public abstract static class NotaryExceptionUnknown extends NotaryException {
        public NotaryExceptionUnknown(@NotNull String notaryErrorMessage, @Nullable SecureHash txId) {
            super(notaryErrorMessage, txId);
        }
    }
}
