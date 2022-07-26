package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash

@DoNotImplement
@CordaSerializable
interface WireTransaction {
    val id: SecureHash
    val privacySalt: PrivacySalt
    val componentGroupLists: List<List<ByteArray>>

    fun getComponentGroupList(componentGroupId: Int): List<ByteArray>
}