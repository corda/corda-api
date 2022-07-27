package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.common.transactions.PrivacySalt

@DoNotImplement
@CordaSerializable
interface ConsensualWireTransaction {
    val id: SecureHash
    val privacySalt: PrivacySalt

    val metadata: ByteArray
    val timestamp: ByteArray
    val requiredSigners: List<ByteArray>
    val consensualStates: List<ByteArray>
    val consensualStateTypes: List<ByteArray>

}