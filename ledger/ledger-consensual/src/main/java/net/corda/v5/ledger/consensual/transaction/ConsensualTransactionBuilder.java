package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.ledger.consensual.ConsensualState;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

/**
 * Defines a builder for {@link ConsensualSignedTransaction}s.
 *
 * It is a transaction class that's mutable (unlike the others which are all immutable). It is
 * intended to be passed around parties that may edit it by adding new states. Then, once the states
 * are right, this class can be used as a holding bucket to gather signatures from multiple parties.
 */
@DoNotImplement
public interface ConsensualTransactionBuilder {
    /**
     * @return states The output {@link ConsensualState}s of the {@link ConsensualTransactionBuilder}.
     */
    @NotNull
    List<ConsensualState> getStates();

    /**
     * Adds the specified {@link ConsensualState}s to the {@link ConsensualTransactionBuilder}.
     *
     * @param states The states of the output to add to the current {@link ConsensualTransactionBuilder}.
     * @return Returns a new {@link ConsensualTransactionBuilder} with the specified output states.
     */
    @NotNull
    ConsensualTransactionBuilder withStates(@NotNull ConsensualState... states);

    /**
     * 1. Verifies the content of the {@link ConsensualTransactionBuilder}
     * 2.a Creates
     * 2.b signs
     * 2.c and returns a {@link ConsensualSignedTransaction}
     *
     * Calling this function once consumes the {@link ConsensualTransactionBuilder}, so it cannot be used again.
     * Therefore, if you want to build two transactions you need two builders.
     *
     * @param publicKey The private counterpart of the specified public key will be used for signing the
     *      {@link ConsensualSignedTransaction}.
     * @return Returns a new {@link ConsensualSignedTransaction} with the specified details.
     *
     * @throws UnsupportedOperationException when called second time on the same object to prevent duplicate
     *      transactions accidentally.
     */
    @NotNull
    ConsensualSignedTransaction signInitial(@NotNull PublicKey publicKey);
}