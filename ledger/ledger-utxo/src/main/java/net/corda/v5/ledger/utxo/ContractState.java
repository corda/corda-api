package net.corda.v5.ledger.utxo;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Defines a contract state.
 * <p>
 * A contract state (or just "state") contains opaque data used by a contract program. It can be thought of as a disk
 * file that the program can use to persist data across transactions.
 * <p>
 * States are immutable. Once created they are never updated, instead, any changes must generate a new successor state.
 * States can be updated (consumed) only once. The notary is responsible for ensuring there is no "double spending" by
 * only signing a transaction if the input states are all free.
 */
@CordaSerializable
public interface ContractState {

    /**
     * Gets the public keys of any participants associated with the current contract state.
     *
     * @return Returns the public keys of any participants associated with the current contract state.
     */
    @NotNull
    List<PublicKey> getParticipants();

    /**
     * Determines to whom the current state is visible, given the transaction in which it's created, and the current node's public keys.
     * <p>
     * The default implementation behaves such that the current state is visible to its participants only.
     *
     * @param transaction The {@link UtxoLedgerTransaction} in which the current state is created.
     * @param myKeys      The collection of public keys owned by the current node.
     * @return Returns true if the current state is visible, given the transaction in which it's created, and the current node's public keys; otherwise, false.
     */
    default boolean isVisible(@NotNull UtxoLedgerTransaction transaction, @NotNull Iterable<PublicKey> myKeys) {
        return StreamSupport
                .stream(myKeys.spliterator(), false)
                .anyMatch(key -> getParticipants().contains(key));
    }
}
