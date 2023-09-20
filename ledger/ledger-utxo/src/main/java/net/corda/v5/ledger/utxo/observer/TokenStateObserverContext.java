package net.corda.v5.ledger.utxo.observer;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.ledger.utxo.ContractState;
import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.token.selection.TokenSelection;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: THIS COMMENT NEEDS UPDATING
 * Defines a mechanism to observe produced contract states of type {@link T} when they are committed to the ledger.
 * <p>
 * Users should implement this interface for any states that need to be selectable via the {@link TokenSelection} API.
 * <p>
 * The Corda platform will discover and invoke implementations of this interface for all produced states that match
 * the type specified by {@link TokenStateObserverContext#getStateType()}.
 * <p>
 * Example usage:
 * <ul>
 * <li>Java:<pre>{@code
 * public class ExampleStateJ implements ContractState {
 * public List<PublicKey> participants;
 * public SecureHash issuer;
 * public String currency;
 * public BigDecimal amount;
 *
 * @NotNull
 * @Override public List<PublicKey> getParticipants() {
 * return participants;
 * }
 * }
 *
 * public class UtxoTokenTransactionStateObserverJavaExample implements UtxoTokenTransactionStateObserver<ExampleStateJ> {
 * @NotNull
 * @Override public Class<ExampleStateJ> getStateType() {
 * return ExampleStateJ.class;
 * }
 * @NotNull
 * @Override public UtxoToken onCommit(
 * @NotNull StateAndRef<ExampleStateJ> stateAndRef,
 * @NotNull UtxoLedgerTransaction transaction
 * )
 * {
 * return new UtxoToken(
 * new UtxoTokenPoolKey(ExampleStateK.class.getName(), stateAndRef.state.issuer, stateAndRef.state.currency),
 * state.amount,
 * new UtxoTokenFilterFields()
 * );
 * }
 * }
 * }</pre></li>
 * <li>Kotlin:<pre>{@code
 * data class ExampleStateK(
 * override val participants: List<PublicKey>,
 * val issuer: SecureHash,
 * val currency: String,
 * val amount: BigDecimal
 * ) : ContractState
 *
 * class UtxoTokenTransactionStateObserverKotlinExample : UtxoTokenTransactionStateObserver<ExampleStateK> {
 *
 * override val stateType = ExampleStateK::class.java
 *
 * override fun onCommit(stateAndRef: StateAndRef<ExampleStateK>, transaction: UtxoLedgerTransaction): UtxoToken {
 * return UtxoToken(
 * UtxoTokenPoolKey(ExampleStateK::class.java.name, stateAndRef.state.issuer, stateAndRef.state.currency),
 * state.amount,
 * UtxoTokenFilterFields()
 * )
 * }
 * }
 * }</pre></li></ul>
 */
public interface TokenStateObserverContext<T extends ContractState> {

    /**
     * TODO: THIS COMMENT NEEDS UPDATING
     *
     * Gets the {@link ContractState} type that the current observer is intended for.
     * @return Returns the {@link ContractState} type that the current observer is intended for.
     */
    @NotNull
    StateAndRef<T> getStateAndRef();

    /**
     * TODO: THIS COMMENT NEEDS UPDATING
     *
     *  The action to be performed when a {@link ContractState} of type {@link T} is committed to the ledger.
     *
     * @param stateAndRef The {@link StateAndRef<T>} that will be committed to the ledger.
     * @param transaction The {@link UtxoLedgerTransaction} that will be committed to the ledger.
     * @return Returns a {@link UtxoToken}.
     */
    @NotNull
    DigestService getDigestService();
}
