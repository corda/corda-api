package net.corda.v5.application.services

data class TokenClaimResult(
    val wasClaimed: Boolean,
    val claim: TokenClaim?
)

