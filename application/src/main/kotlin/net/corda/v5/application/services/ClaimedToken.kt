package net.corda.v5.application.services

import net.corda.v5.base.types.MemberX500Name
import net.corda.v5.crypto.SecureHash
import java.math.BigDecimal

/**
 * [ClaimedToken] represents a claimed token from the [TokenCache].
 *
 * @property stateRef Unique identifier of a state in the <TX_ID>:<STATE_NUM> format.
 * @property tokenType The type of token.
 * @property issuerHash The [SecureHash] of the issuer of the token.
 * @property notaryX500Name The [MemberX500Name] of the notary for the token.
 * @property symbol The symbol for the token.
 * @property tag The user defined tag for the token.
 * @property ownerHash The [SecureHash] for the owner of the token.
 * @property amount The amount of the token.
 */
data class ClaimedToken (
    val stateRef: String,
    val tokenType: String,
    val issuerHash: SecureHash,
    val notaryX500Name: MemberX500Name,
    val symbol: String,
    var tag: String?,
    var ownerHash: SecureHash?,
    val amount: BigDecimal
)

