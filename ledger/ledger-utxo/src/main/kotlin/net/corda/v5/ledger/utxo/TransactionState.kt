package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

// TODO : Consider interface and impl

@CordaSerializable
data class TransactionState<out T : ContractState>(
    val contractState: T,
    val contractId: String,
    val notary: PublicKey,
    val encumbrance: Int?,
    val constraint: CpkConstraint
)