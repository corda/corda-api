package net.corda.v5.application.services

import net.corda.v5.base.annotations.DoNotImplement

/**
 * [TokenClaim] is a claimed set of tokens returned by a call to [TokenCache.tryClaim].
 *
 * The claimed [ClaimedToken] list is exclusively locked by the flow that made the claim and are unavailable to any
 * other flows. Once a flow has either spent some or all of the claimed tokens it should call [TokenClaim.useAndRelease]
 * to notify the cache which tokens were used. Any unused tokens will be released and made available to other flows.
 * If the flow does not call [TokenClaim.useAndRelease] the tokens will remain locked until the cache receives a
 *
 * consumed notification from the vault or the claim timeout elapses.
 *
 * @property claimId The unique identifier of the claim.
 * @property claimedTokens List of [ClaimedToken] claimed.
 */
@DoNotImplement
interface TokenClaim {

    val claimId: String

    val claimedTokens: List<ClaimedToken>

    /**
     * Removes any used tokens from the cache and unlocks any remaining tokens for other flows to claim.
     *
     * @param usedTokensRefs List of state refs to mark as used in the <TX_ID>:<STATE_NUM> format.
     */
    fun useAndRelease(usedTokensRefs: List<String>)
}
