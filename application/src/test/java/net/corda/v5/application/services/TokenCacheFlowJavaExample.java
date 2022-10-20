package net.corda.v5.application.services;

import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.flows.RPCRequestData;
import net.corda.v5.application.flows.RPCStartableFlow;
import net.corda.v5.application.messaging.FlowMessaging;
import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * This tests validates the code example in the KDoc comments will compile
 */
public class TokenCacheFlowJavaExample implements RPCStartableFlow {
    @CordaInject
    public TokenCache tokenCache;

    @Override
    @NotNull
    public String call(@NotNull RPCRequestData requestBody) {

        // Create a criteria describing the tokens to be selected and
        // the target amount to be claimed.
        TokenClaimCriteria criteria = new TokenClaimCriteria
                (
                        "Currency",
                        getIssuerHash(),
                        getNotaryX500Name(),
                        "USD",
                        new BigDecimal(100)
                );

        // Call the token selection API to try and claim the tokens.
        TokenClaim claim = tokenCache.tryClaim(criteria);

        if (claim == null) {
            // Not enough tokens could be claimed to satisfy the request.
            // take alternative action.
        } else {
            // Tokens we successfully claimed and can now be spent.
            List<String> spentTokenRefs = spendTokens(claim.getClaimedTokens());

            // Release the claim by notifying the cache which tokens where spent. Any unspent tokens will be released
            // for other flows to claim.
            claim.useAndRelease(spentTokenRefs);
        }

        return "Done";
    }

    @NotNull
    private SecureHash getIssuerHash() {
        return null;
    }

    @NotNull
    private MemberX500Name getNotaryX500Name() {
        return null;
    }

    @NotNull
    private List<String> spendTokens(List<ClaimedToken> claimedTokens) {
        return null;
    }
}