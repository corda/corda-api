package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.ledger.common.transaction.TransactionNoAvailableKeysException;
import net.corda.v5.ledger.consensual.ConsensualState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Defines a builder for ConsensualSignedTransactions.
 * <p>
 * The builder is designed to be mutable so that it can be passed around counter-parties that may edit it by adding
 * new states. Once the states have been collected, the builder can be used to obtain a ConsensualSignedTransaction
 * which can be used to gather signatures from the transaction's participants.
 */
public interface ConsensualTransactionBuilder {

    /**
     * Gets the output states from the current ConsensualTransactionBuilder.
     *
     * @return Returns the output states from the current ConsensualTransactionBuilder.
     */
    @NotNull
    List<ConsensualState> getStates();

    /**
     * Adds the specified states to the current ConsensualTransactionBuilder.
     *
     * @param states The output states to add to the current ConsensualTransactionBuilder.
     * @return Returns a new ConsensualTransactionBuilder with the specified output states.
     */
    @NotNull
    // TODO : Consider alignment with UTXO addState and addStates.
    ConsensualTransactionBuilder withStates(final ConsensualState... states);

    /**
     * Verifies the content of the current ConsensualTransactionBuilder and signs the transaction with any required
     * signatories that belong to the current node.
     * <p>
     * Calling this function once consumes the ConsensualTransactionBuilder, so it cannot be used again,
     * therefore if you want to build two identical transactions, you will need two ConsensualTransactionBuilders.
     *
     * @return Returns a ConsensualSignedTransaction with signatures for any required signatories that belong to the current node.
     * @throws IllegalStateException when called a second time on the same object to prevent unintentional duplicate transactions.
     * @throws TransactionNoAvailableKeysException if none of the required keys are available to sign the transaction.
     */
    @NotNull
    @Suspendable
    ConsensualSignedTransaction toSignedTransaction();
}
