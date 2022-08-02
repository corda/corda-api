package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.consensual.ConsensualState
import java.security.PublicKey
import java.time.Instant

@DoNotImplement
interface ConsensualLedgerTransaction {
    val id: SecureHash
    val requiredSigningKeys: Set<PublicKey>
    val timestamp: Instant
    val states: List<ConsensualState>
}