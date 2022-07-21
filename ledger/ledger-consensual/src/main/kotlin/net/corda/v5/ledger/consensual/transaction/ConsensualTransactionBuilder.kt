package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.DigestService
import net.corda.v5.crypto.merkle.MerkleTreeFactory
import net.corda.v5.ledger.common.transactions.PrivacySalt
import net.corda.v5.ledger.consensual.ConsensualState
import net.corda.v5.ledger.consensual.Party
import java.security.PublicKey
import java.security.SecureRandom
import java.time.Instant

@DoNotImplement
interface ConsensualTransactionBuilder {
    val metadata: ConsensualTransactionMetaData?
    fun withMetadata(metadata: ConsensualTransactionMetaData): ConsensualTransactionBuilder

    val timeStamp: Instant?
    fun withTimeStamp(timeStamp: Instant): ConsensualTransactionBuilder

    val consensualStates: List<ConsensualState>
    fun withConsensualState(consensualState: ConsensualState) : ConsensualTransactionBuilder

    fun build(
        merkleTreeFactory: MerkleTreeFactory,
        digestService: DigestService,
        secureRandom: SecureRandom,
        serializer: SerializationService
    ): ConsensualWireTransaction
}