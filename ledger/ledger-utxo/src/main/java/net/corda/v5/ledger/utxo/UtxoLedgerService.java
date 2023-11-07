package net.corda.v5.ledger.utxo;

import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.application.persistence.PagedQuery;
import net.corda.v5.application.persistence.PagedQuery.ResultSet;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.common.transaction.TransactionVerificationException;
import net.corda.v5.ledger.utxo.query.VaultNamedParameterizedQuery;
import net.corda.v5.ledger.utxo.query.VaultNamedQueryFactory;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import net.corda.v5.ledger.utxo.transaction.UtxoTransactionBuilder;
import net.corda.v5.ledger.utxo.transaction.UtxoTransactionValidator;
import net.corda.v5.ledger.utxo.transaction.filtered.UtxoFilteredTransaction;
import net.corda.v5.ledger.utxo.transaction.filtered.UtxoFilteredTransactionBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;

/**
 * Defines UTXO ledger services.
 */
@DoNotImplement
public interface UtxoLedgerService {

    /**
     * Gets a UTXO transaction builder.
     *
     * @return Returns a new {@link UtxoTransactionBuilder} instance.
     */
    @NotNull
    @Suspendable
    UtxoTransactionBuilder createTransactionBuilder();

    /**
     * Resolves the specified {@link StateRef} instances into {@link StateAndRef} instances of the specified {@link ContractState} type.
     *
     * @param <T>       The underlying {@link ContractState} type.
     * @param stateRefs The {@link StateRef} instances to resolve.
     * @return Returns a {@link List} of {@link StateAndRef} of the specified {@link ContractState} type.
     */
    @NotNull
    @Suspendable
    <T extends ContractState> List<StateAndRef<T>> resolve(@NotNull Iterable<StateRef> stateRefs);

    /**
     * Resolves the specified {@link StateRef} instance into a {@link StateAndRef} instance of the specified {@link ContractState} type.
     *
     * @param <T>      The underlying {@link ContractState} type.
     * @param stateRef The {@link StateRef} instances to resolve.
     * @return Returns a {@link StateAndRef} of the specified {@link ContractState} type.
     */
    @NotNull
    @Suspendable
    <T extends ContractState> StateAndRef<T> resolve(@NotNull StateRef stateRef);

    /**
     * Finds a {@link UtxoSignedTransaction} in the vault by the specified transaction ID.
     *
     * @param id The ID of the {@link UtxoSignedTransaction} to find.
     * @return Returns the {@link UtxoSignedTransaction} if it has been recorded previously, or null if no transaction could be found.
     */
    @Nullable
    @Suspendable
    UtxoSignedTransaction findSignedTransaction(@NotNull SecureHash id);

    /**
     * Finds a {@link UtxoLedgerTransaction} in the vault by the specified transaction ID.
     *
     * @param id The ID of the {@link UtxoLedgerTransaction} to find.
     * @return Returns the {@link UtxoLedgerTransaction} if it has been recorded previously, or null if no transaction could be found.
     */
    @Nullable
    @Suspendable
    UtxoLedgerTransaction findLedgerTransaction(@NotNull SecureHash id);

    /**
     * Filters a {@link UtxoSignedTransaction} to create a {@link UtxoFilteredTransaction} that only contains the components specified by the
     * {@link UtxoFilteredTransactionBuilder} output from this method.
     *
     * @param transaction The {@link UtxoSignedTransaction} to filter.
     * @return Returns the {@link UtxoFilteredTransactionBuilder} that filters the {@link UtxoSignedTransaction}.
     */
    @NotNull
    @Suspendable
    UtxoFilteredTransactionBuilder filterSignedTransaction(@NotNull UtxoSignedTransaction transaction);

    /**
     * Finds unconsumed states that are concrete implementations or subclasses of {@code type}.
     *
     * @deprecated This method should no longer be used due to its lack of paging support, which can result in
     *             serious performance issues and / or out of memory errors if the query returns many states.
     *             {@link #findUnconsumedStatesByExactType(Class, Integer, Instant)} should be used instead. Note
     *             that this will not return subclasses of the specified class. If this functionality is required,
     *             it is recommended to write a custom query and invoke this via the {@link #query(String, Class)}
     *             API instead.
     *
     * @param <T>  The underlying {@link ContractState} type.
     * @param type The {@link ContractState} type to find in the vault.
     * @return Returns a {@link List} of {@link StateAndRef} of unconsumed states of the specified type, or an empty list if no states could be found.
     */
    @Deprecated(since = "5.1", forRemoval = true)
    @NotNull
    @Suspendable
    <T extends ContractState> List<StateAndRef<T>> findUnconsumedStatesByType(@NotNull Class<T> type);

    /**
     * Finds unconsumed states of the specified {@link ContractState} type in the vault.
     * <p>
     * This version supports paging, limiting the number of results returned in a single query call by setting the
     * `limit` argument.
     * <p>
     * Example usage:
     * <ul>
     * <li>Kotlin:<pre>{@code
     * val resultSet = utxoLedgerService.findUnconsumedStatesByExactType(MyState::class.java, 10, Instant.now())
     *
     * processResultsWithApplicationLogic(resultSet.results)
     *
     * while (resultSet.hasNext()) {
     *     val results = resultSet.next()
     *     processResultsWithApplicationLogic(results)
     * }
     * }</pre></li>
     * <li>Java:<pre>{@code
     * PagedQuery.ResultSet<StateAndRef<MyState>> resultSet = utxoLedgerService.query(MyState.class, 10, Instant.now())
     *
     * processResultsWithApplicationLogic(resultSet.getResults());
     *
     * while (resultSet.hasNext()) {
     *     List<Integer> results = resultSet.next();
     *     processResultsWithApplicationLogic(results);
     * }
     * }</pre></li>
     *
     * @param <T>   The underlying {@link ContractState} type.
     * @param type  The {@link ContractState} type to find in the vault.
     * @param limit The size of each page.
     * @param createdTimestampLimit The timestamp limit the underlying query enforces.
     * @return Returns a {@link ResultSet} of {@link StateAndRef} of unconsumed states of the specified type.
     */
    @NotNull
    @Suspendable
    <T extends ContractState> ResultSet<StateAndRef<T>> findUnconsumedStatesByExactType(
            @NotNull Class<T> type,
            @NotNull Integer limit,
            @NotNull Instant createdTimestampLimit
    );

    /**
     * Verifies, signs, collects signatures, records and broadcasts a {@link UtxoSignedTransaction} to participants and observers.
     *
     * @param transaction The {@link UtxoSignedTransaction} to verify, finalize and record.
     * @param sessions    The {@link FlowSession} instances of the participants or observers of the transaction.
     * @return Returns the {@link FinalizationResult} containing a fully signed {@link UtxoSignedTransaction} that was
     * recorded.
     * @throws ContractVerificationException if the transaction fails contract verification.
     */
    @NotNull
    @Suspendable
    FinalizationResult finalize(
            @NotNull UtxoSignedTransaction transaction,
            @NotNull List<FlowSession> sessions
    );

    /**
     * Verifies, signs and records a {@link UtxoSignedTransaction}.
     * <p>
     * This method should be called in response to {@link #finalize(UtxoSignedTransaction, List)}.
     *
     * @param session   The {@link FlowSession} of the counter-party finalizing the {@link UtxoSignedTransaction}.
     * @param validator Validates the received {@link UtxoSignedTransaction}.
     * @return Returns the {@link FinalizationResult} containing a fully signed {@link UtxoSignedTransaction} that was
     * received and recorded.
     * @throws ContractVerificationException if the transaction failed contract verification.
     */
    @NotNull
    @Suspendable
    FinalizationResult receiveFinality(
            @NotNull FlowSession session,
            @NotNull UtxoTransactionValidator validator
    );

    /**
     * Sends a transaction builder to another session, waits for the other side to propose transaction builder components, applies
     * the proposed components to a copy of the original builder, and returns that new builder.
     * <p>
     * It supports similar workflows to the following:
     * <p>
     * Initiator:
     * <pre>{@code
     * val updatedTxBuilder = utxoLedgerService.sendAndReceiveTransactionBuilder(txBuilder, session)
     * }</pre>
     * <p>
     * The notary and time window from the proposal will get discarded and the original will be kept if both the original and
     * the proposal have these components set.
     * Duplications of input staterefs, reference staterefs, and signatories will be discarded.
     * <p>
     * Receiver:
     * <pre>{@code
     * val proposedTxBuilder = utxoLedgerService.receiveTransactionBuilder(session)
     * proposedTxBuilder.add...(...)
     * proposedTxBuilder.add...(...)
     * proposedTxBuilder.add...(...)
     * utxoLedgerService.replyTransactionBuilderProposal(proposedTxBuilder, session)
     * }</pre>
     *
     * @param transactionBuilder The {@link UtxoTransactionBuilder} to send.
     * @param session            The receiver {@link FlowSession}.
     * @return A new merged builder of the original and proposed components.
     */
    @NotNull
    @Suspendable
    UtxoTransactionBuilder sendAndReceiveTransactionBuilder(
            @NotNull UtxoTransactionBuilder transactionBuilder,
            @NotNull FlowSession session
    );

    /**
     * Receives a transaction builder from another session.
     *
     * @param session The {@link FlowSession} to receive the {@link UtxoTransactionBuilder} from.
     */
    @NotNull
    @Suspendable
    UtxoTransactionBuilder receiveTransactionBuilder(
            @NotNull FlowSession session
    );

    /**
     * Sends the difference between the current transaction builder and the originally received one to another session
     * with all dependent back chains.
     * It works only with {@link UtxoTransactionBuilder}s created from {@link #receiveTransactionBuilder(FlowSession)}
     * which tracks the differences internally.
     * If it is called with anything else, it throws InvalidParameterException.
     * <p>
     *
     * @param transactionBuilder The {@link UtxoTransactionBuilder} to send.
     * @param session            The receiver {@link FlowSession}.
     */
    @Suspendable
    void sendUpdatedTransactionBuilder(
            @NotNull UtxoTransactionBuilder transactionBuilder,
            @NotNull FlowSession session
    );

    /**
     * Sends transaction to counterparty whose session is open for.
     *
     * @param sessions The counterparty who are connected to {@link FlowSession} to send transaction.
     * @param signedTransaction The {@link UtxoSignedTransaction} by signatories.
     * @throws IllegalArgumentException if transaction fails verification before sending transaction.
     */
    @Suspendable
    void sendTransaction(
            @NotNull List<FlowSession> sessions,
            @NotNull UtxoSignedTransaction signedTransaction
    );

    /**
     * Receives verified transaction from counterparty whose session is open for and persists it to a vault.
     *
     * @param session The counterparty who are connected to {@link FlowSession} to receive transaction from.
     * @return Returns {@link UtxoSignedTransaction} received from counterparty through session.
     * @throws TransactionVerificationException if the transaction received fails verification.
     */
    @Suspendable
    UtxoSignedTransaction receiveTransaction(
            @NotNull FlowSession session
    );

    /**
     * Creates a query object for a vault named query with the given name. This query can be executed later by calling
     * {@link PagedQuery#execute()}.
     * <p>
     * The vault named queries executed by this method must be defined within a {@link VaultNamedQueryFactory}.
     * <p>
     * Example usage:
     * <ul>
     * <li>Kotlin:<pre>{@code
     * val query = utxoLedgerService.query("FIND_BY_TEST_FIELD", Int::class.java)
     *     .setParameter("testField", "value")
     *     .setParameter("participants", listOf("something"))
     *     .setParameter("contractStateType", ContractState::class.java.name)
     *     .setParameter("in-memory-filter-parameter", "parameter")
     *     .setCreatedTimestampLimit(Instant.now())
     *     .setOffset(0)
     *     .setLimit(100)
     *
     * val resultSet = query.execute()
     *
     * processResultsWithApplicationLogic(resultSet.results)
     *
     * while (resultSet.hasNext()) {
     *     val results = resultSet.next()
     *     processResultsWithApplicationLogic(results)
     * }
     * }</pre></li>
     * <li>Java:<pre>{@code
     * ParameterizedQuery<Integer> query = utxoLedgerService.query("FIND_BY_TEST_FIELD", Integer.class)
     *         .setParameter("testField", "value")
     *         .setParameter("participants", List.of("something"))
     *         .setParameter("contractStateType", ContractState.class.getName())
     *         .setParameter("in-memory-filter-parameter", "parameter")
     *         .setTimestampLimit(Instant.now())
     *         .setOffset(0)
     *         .setLimit(100);
     *
     * PagedQuery.ResultSet<Integer> resultSet = query.execute();
     *
     * processResultsWithApplicationLogic(resultSet.getResults());
     *
     * while (resultSet.hasNext()) {
     *     List<Integer> results = resultSet.next();
     *     processResultsWithApplicationLogic(results);
     * }
     * }</pre></li>
     *
     * @param queryName   The name of the named ledger query to use.
     * @param resultClass Type that the query should return when executed.
     * @return A {@link VaultNamedParameterizedQuery} query object that can be executed or modified further on.
     * @see VaultNamedParameterizedQuery
     * @see ResultSet
     * @see VaultNamedQueryFactory
     */
    @Suspendable
    @NotNull
    <R> VaultNamedParameterizedQuery<R> query(@NotNull String queryName, @NotNull Class<R> resultClass);
}
