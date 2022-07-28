package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

@CordaSerializable
interface ContractState {
    val participants: Set<PublicKey>
}
