package net.corda.v5.ledger.utxo

import java.util.*

interface IdentifiableState : ContractState {
    val id: UUID
}
