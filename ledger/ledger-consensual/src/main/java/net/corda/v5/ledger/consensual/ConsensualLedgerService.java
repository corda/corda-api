package net.corda.v5.ledger.consensual;

import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.consensual.transaction.ConsensualLedgerTransaction;
import net.corda.v5.ledger.consensual.transaction.ConsensualSignedTransaction;
import net.corda.v5.ledger.consensual.transaction.ConsensualTransactionBuilder;
import net.corda.v5.ledger.consensual.transaction.ConsensualTransactionValidator;
import org.jetbrains.annotations.NotNull;

/**
 * Defines the consensual ledger service.
 */
@DoNotImplement
public interface ConsensualLedgerService {

    /**
     * Gets a ConsensualTransactionBuilder instance.
     *
     * @return Returns a new ConsensualTransactionBuilder instance.
     */
    @NotNull
    @Suspendable
    ConsensualTransactionBuilder getTransactionBuilder();

    /**
     * Finds a ConsensualSignedTransaction in the vault by its transaction ID.
     *
     * @param id The transaction ID of the ConsensualSignedTransaction to find in the vault.
     * @return Returns the ConsensualSignedTransaction if it has been recorded, or null if the transaction could not be found.
     */
    @NotNull
    @Suspendable
    ConsensualSignedTransaction findSignedTransaction(@NotNull final SecureHash id);

    /**
     * Finds a ConsensualLedgerTransaction in the vault by its transaction ID.
     *
     * @param id The transaction ID of the ConsensualLedgerTransaction to find in the vault.
     * @return Returns the ConsensualLedgerTransaction if it has been recorded, or null if the transaction could not be found.
     */
    @NotNull
    @Suspendable
    ConsensualLedgerTransaction findLedgerTransaction(@NotNull final SecureHash id);

    /**
     * Finalizes a transaction by collecting any remaining required signatures from counter-parties, and broadcasts the
     * fully signed transaction to all participants involved in the transaction to be recorded in the vault.
     *
     * @param transaction The transaction to finalize.
     * @param sessions    The sessions representing the counter-party participants of the transaction.
     * @return Returns the fully signed and recorded transaction.
     */
    @NotNull
    @Suspendable
    ConsensualSignedTransaction finalize(
            @NotNull final ConsensualSignedTransaction transaction,
            @NotNull final Iterable<FlowSession> sessions
    );

    /**
     * Verifies, signs and records the fully signed ConsensualSignedTransaction.
     *
     * @param session   The session from which the ConsensualSignedTransaction was received.
     * @param validator Validates the received ConsensualSignedTransaction.
     * @return Returns the fully signed ConsensualSignedTransaction.
     */
    @NotNull
    @Suspendable
    ConsensualSignedTransaction receiveFinality(
            @NotNull final FlowSession session,
            @NotNull final ConsensualTransactionValidator validator
    );
}
