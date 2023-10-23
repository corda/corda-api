package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.ledger.utxo.StateRef;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Defines a claimed set of tokens returned by a call to {@link TokenSelection#tryClaim(TokenClaimCriteria)}.
 * <p>
 * The claimed {@link ClaimedToken} list is exclusively locked by the flow that made the claim and are
 * unavailable to any other flows. Any unconsumed token that has been claimed by the flow is released and made
 * available to other flows once the flow terminates either successfully or unsuccessfully.
 */
@DoNotImplement
public interface TokenClaim {

    /**
     * Gets a list of claimed tokens.
     *
     * @return Returns a list of claimed tokens.
     */
    @NotNull
    List<ClaimedToken> getClaimedTokens();

    /**
     * Removes any used tokens from the cache and unlocks any remaining tokens for other flows to claim.
     * This method is now deprecated. Claimed tokens are now managed differently which makes the method
     * irrelevant. From release 5.1, nothing will happen if the method is called.
     *
     * @param usedTokensRefs The {@link List} of {@link StateRef}s to mark as used.
     */
    @Suspendable
    @Deprecated(since = "5.1", forRemoval = true)
    void useAndRelease(@NotNull List<StateRef> usedTokensRefs);
}
