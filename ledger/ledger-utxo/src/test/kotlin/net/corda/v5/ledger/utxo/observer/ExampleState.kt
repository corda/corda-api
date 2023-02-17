package net.corda.v5.ledger.utxo.observer

import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.utxo.ContractState
import java.math.BigDecimal
import java.security.PublicKey

data class ExampleState(
    // TODO : JVM Clash - what are developers going to call this now?
    // Well, they shouldn't be passing participants in the constructor anyway, so I guess this is kind of a good thing!
    val _participants: List<PublicKey>,
    val issuer: SecureHash,
    val currency: String,
    val amount: BigDecimal
) : ContractState {
    override fun getParticipants(): List<PublicKey> {
        return _participants;
    }
}