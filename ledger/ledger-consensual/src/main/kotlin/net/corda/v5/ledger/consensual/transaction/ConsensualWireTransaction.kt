package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import net.corda.v5.crypto.merkle.HASH_DIGEST_PROVIDER_TWEAKABLE_NAME
import net.corda.v5.ledger.common.transactions.PrivacySalt
import net.corda.v5.ledger.consensual.ConsensualState
import java.security.PublicKey
import java.time.Instant

@DoNotImplement
interface ConsensualWireTransaction {
    val id: SecureHash
    val privacySalt: PrivacySalt

    val metadata: ByteArray
    val timestamp: ByteArray
    val requiredSigners: List<ByteArray>
    val consensualStates: List<ByteArray>
    val consensualStateTypes: List<ByteArray>

}