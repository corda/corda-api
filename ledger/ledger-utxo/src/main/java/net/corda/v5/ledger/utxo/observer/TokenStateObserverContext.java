package net.corda.v5.ledger.utxo.observer;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.ledger.utxo.ContractState;
import net.corda.v5.ledger.utxo.StateAndRef;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the context in which the token is being generated and provides services that might be required.
 */
public interface TokenStateObserverContext<T extends ContractState> {

    /**
     * Gets the {@link StateAndRef} of the transaction which is responsible for generating the new tokens.
     *
     * @return Returns the {@link StateAndRef}.
     */
    @NotNull
    StateAndRef<T> getStateAndRef();

    /**
     *  Gets the {@link DigestService} which provides hashing capabilities.
     *
     * @return Returns a {@link DigestService}.
     */
    @NotNull
    DigestService getDigestService();
}
