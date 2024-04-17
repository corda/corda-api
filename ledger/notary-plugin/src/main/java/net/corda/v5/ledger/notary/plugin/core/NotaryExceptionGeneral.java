package net.corda.v5.ledger.notary.plugin.core;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A notary exception  for scenarios that were unexpected, or couldn't be mapped. A different result might be returned
 * when retried. These type of exceptions will not invalidate the transaction immediately.
 */
@CordaSerializable
public class NotaryExceptionGeneral extends NotaryExceptionUnknown {
    public NotaryExceptionGeneral(@NotNull String notaryErrorMessage, @Nullable SecureHash txId) {
        super(notaryErrorMessage, txId);
    }
}
