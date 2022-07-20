package net.corda.v5.ledger.common.transactions

//TODO: Mainly from [net.corda.libs.cpiupload.endpoints.v1.CpkIdentifier] Clarify their relationship
data class CpkIdentifier(
    val name : String,
    val version : String,
    val signerSummaryHash : String?
)