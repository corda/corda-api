package net.corda.v5.ledger.consensual.transaction;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.consensual.ConsensualState;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Defines a Consensual ledger transaction.
 *
 * Comparing with {@link ConsensualSignedTransaction}:
 * <ul>
 * <li>It has access to the deserialized details.</li>
 * <li>It does not have direct access to the signatures.</li>
 * <li>It requires a serializer.</li>
 * </ul>
 *
 * {@link ConsensualLedgerTransaction} is an abstraction that is meant to be used during the transaction verification stage.
 * It needs full access to its states that might be in transactions that are encrypted and unavailable for code running
 * outside the secure enclave.
 * Also, it might need to deserialize states with code that might not be available on the classpath.
 *
 * Because of this, trying to create or use a {@link ConsensualLedgerTransaction} for any other purpose then transaction
 * verification can result in unexpected exceptions, which need de be handled.
 *
 * {@link ConsensualLedgerTransaction}s should never be instantiated directly from client code, but rather via
 * {@link ConsensualSignedTransaction#toLedgerTransaction}
 *
 */
@DoNotImplement
public interface ConsensualLedgerTransaction {
    /**
     * @return id The ID of the transaction.
     */
    @NotNull
    SecureHash getId();

    /**
     * @return requiredSigningKeys Set of {@link PublicKey} needed to make the underlying transaction valid.
     * Essentially the union of the participants of the transaction's {@link ConsensualState}s.
     */
    @NotNull
    Set<PublicKey> getRequiredSigningKeys();

    /**
     * @return timestamp The timestamp of the transaction. (When it got signed initially.)
     */
    @NotNull
    Instant getTimestamp();

    /**
     * @return states List of the output {@link ConsensualState}s of the transaction.
     */
    @NotNull
    List<ConsensualState> getStates();
}
