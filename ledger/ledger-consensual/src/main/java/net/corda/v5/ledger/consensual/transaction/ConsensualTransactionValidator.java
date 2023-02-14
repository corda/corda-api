package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.base.annotations.Suspendable;

import java.io.Serializable;

/**
 * Defines a functional interface that validates a ConsensualLedgerTransaction.
 * <p>
 * An implementation of ConsensualTransactionValidator can be passed to ConsensualLedgerService.receiveFinality to
 * perform custom validation on the ConsensualLedgerTransaction received from the initiator of finality.
 * <p>
 * When validating a ConsensualLedgerTransaction throw either an IllegalArgumentException, IllegalStateException or
 * CordaRuntimeException to indicate that the transaction is invalid.
 * <p>
 * This will lead to the termination of finality for the caller of ConsensualLedgerService.receiveFinality and all
 * participants included in finalizing the transaction.
 * <p>
 * Other exceptions will still stop the progression of finality; however, the reason for the failure will not be
 * communicated to the initiator of finality.
 */
@FunctionalInterface
public interface ConsensualTransactionValidator extends Serializable {

    /**
     * Checks a ConsensualLedgerTransaction for validity.
     * <p>
     * Throw an IllegalArgumentException, IllegalStateException or CordaRuntimeException to indicate that the
     * transaction is invalid.
     *
     * @param transaction The transaction to check.
     * @throws Throwable if the ConsensualLedgerTransaction fails validation.
     */
    @Suspendable
    void checkTransaction(final ConsensualLedgerTransaction transaction);
}
