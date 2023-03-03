package net.corda.v5.ledger.utxo;

import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a mechanism for implementing contracts, which perform transaction verification.
 * <p>
 * All participants run this code for every input and output state for every transaction they see on the network.
 * All contracts must verify and accept the associated transaction for it to be finalized and persisted to the ledger.
 * Failure of any contract constraint aborts the transaction, resulting in the transaction not being finalized.
 */
public interface Contract {

    /**
     * Determines whether the specified state is visible to a node observing, or recording the associated transaction.
     * <p>
     * The default implementation determines that a state should be visible to its participants.
     * </p>
     *
     * @param checker Provides a mechanism to determine visibility of the specified {@link ContractState}.
     * @param state   The {@link ContractState} for which to determine visibility.
     * @return Returns true if the specified state is visible to the current node; otherwise, false.
     */
    default boolean isVisible(@NotNull final VisibilityChecker checker, @NotNull final ContractState state) {
        return checker.containsMySigningKeys(state.getParticipants());
    }

    /**
     * Verifies the specified transaction associated with the current contract.
     *
     * @param transaction The transaction to verify.
     * @throws RuntimeException if the specified transaction fails verification.
     */
    void verify(@NotNull UtxoLedgerTransaction transaction);
}
