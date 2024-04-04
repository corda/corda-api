package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Defines a mechanism to allow flows to query the token cache. The query can be either to claim a list of {@link ClaimedToken}
 * to spend, or to know the current balance, {@link TokenBalance}.
 * <p>
 * The platform will provide an instance of {@link TokenSelection} to flows via property injection.
 */
@DoNotImplement
public interface TokenSelection {

    /**
     * Attempts to claim a set of tokens from the cache that satisfies the specified {@link TokenClaimCriteria}.
     *
     * The API allows a flow to query for a target amount of a given state/token type it wishes to spend.
     * If available, a set of tokens will be selected that sum to at least the target amount specified.
     * The tokens will be locked in the cache to prevent other flows from selecting them.
     *
     * A unique deterministic identifier for a token claim is provided per API call.
     * Corda may rerun a token claim request on behalf of the calling flow in the event system instability.
     * This ID will be used to deduplicate TokenClaims in this scenario.
     * This ID needs to be unique per TokenClaim within a single flow.
     *
     * @param criteria The {@link TokenClaimCriteria} used to select tokens.
     * @return Returns a {@link TokenClaim} if enough tokens were claimed to satisfy the {@link TokenClaimCriteria#getTargetAmount()},
     * or null if the {@link TokenClaimCriteria#getTargetAmount()} could not be reached.
     * @throws IllegalArgumentException if the deduplicationId exceeds 128 characters
     * <p>
     * <p>
     * Example usage:
     * <ul>
     * <li>Kotlin:<pre>{@code
     * @CordaInject lateinit var tokenSelection: TokenSelection
     *
     * override fun call(requestBody: RestRequestBody): String {
     *
     * // Create a criteria describing the tokens to be selected and
     * // the target amount to be claimed.
     * val criteria = TokenClaimCriteria(
     * "Currency",
     * getIssuerHash(),
     * getNotaryX500Name(),
     * "USD",
     * BigDecimal(100)
     * )
     *
     * // Call the token selection API to try and claim the tokens.
     * val claim = tokenSelection.tryClaim(criteria)
     *
     * if (claim == null) {
     * // Not enough tokens could be claimed to satisfy the request.
     * // take alternative action.
     * } else {
     * // Tokens we successfully claimed and can now be spent.
     * spendTokens(claim.claimedTokens)
     * }
     *
     * return "Done"
     * }
     * }</pre></li>
     *
     * <li>Java:<pre>{@code
     * @CordaInject public TokenSelection tokenSelection;
     * @Override public String call(RestRequestBody requestBody) {
     * // Create a criteria describing the tokens to be selected and
     * // the target amount to be claimed.
     * TokenClaimCriteria criteria = new TokenClaimCriteria (
     * "Currency",
     * getIssuerHash(),
     * getNotaryX500Name(),
     * "USD",
     * new BigDecimal(100)
     * );
     *
     * // Call the token selection API to try and claim the tokens.
     * TokenClaim claim = tokenSelection.tryClaim(criteria);
     *
     * if (claim == null) {
     * // Not enough tokens could be claimed to satisfy the request.
     * // take alternative action.
     * } else {
     * // Tokens we successfully claimed and can now be spent.
     * spendTokens(claim.getClaimedTokens());
     * }
     *
     * return "Done";
     * }
     * }</pre></li></ul>
     * <p>
     */
    @Nullable
    @Suspendable
    TokenClaim tryClaim(@NotNull TokenClaimCriteria criteria);

    /**
     * Calculates the balance of a pool of tokens taking into account only the tokens that satisfy the specified
     * {@link TokenBalanceCriteria}.
     * The API allows a flow to query the balance of a set or subset of tokens. Two values are calculated when the query is executed.
     * One represents the available balance which only includes tokens that have not been spent nor claimed. While the other
     * value represents the total balance which includes all tokens that have not been spent, i.e., the total balance is
     * the available balance plus the balance of all claimed tokens.
     * @param criteria The {@link TokenBalanceCriteria} used to select the tokens that should be used to calculate the balance.
     * @return Returns the balance that was calculated as a {@link TokenBalance}.
     * <p>
     * <p>
     * Example usage:
     * <ul>
     * <li>Kotlin:<pre>{@code
     * @CordaInject lateinit var tokenSelection: TokenSelection
     *
     * override fun call(requestBody: RestRequestBody): String {
     *
     * // Create a criteria describing the tokens to be taken
     * // into consideration while calculating the balance.
     * val criteria = TokenBalanceCriteria(
     * "Currency",
     * getIssuerHash(),
     * getNotaryX500Name(),
     * "USD"
     * )
     *
     * // Call the token selection API to calculate the balance.
     * val response = tokenSelection.queryBalance(criteria)
     *
     * // Print the results
     * println("Balance available: ${response.availableBalance}, Total balance: ${response.totalBalance}")
     *
     * return "Done"
     * }
     * }</pre></li>
     *
     * <li>Java:<pre>{@code
     * @CordaInject public TokenSelection tokenSelection;
     * @Override public String call(RestRequestBody requestBody) {
     *
     * // Create a criteria describing the tokens to be taken
     * // into consideration while calculating the balance.
     * TokenBalanceCriteria criteria = new TokenBalanceCriteria(
     * "Currency",
     * getIssuerHash(),
     * getNotaryX500Name(),
     * "USD"
     * );
     *
     * // Call the token selection API to calculate the balance.
     * TokenBalance response = tokenSelection.queryBalance(criteria);
     *
     * // Print the results.
     * System.out.println("Balance available: " + response.getAvailableBalance() + "Total balance: " + response.getTotalBalance());
     *
     * return "Done";
     * }
     * }</pre></li></ul>
     * <p>
     */
    @Nullable
    @Suspendable
    TokenBalance queryBalance(@NotNull TokenBalanceCriteria criteria);
}
