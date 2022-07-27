package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.application.crypto.SigningService
import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.DigestService
import net.corda.v5.crypto.merkle.MerkleTreeFactory
import net.corda.v5.ledger.common.transactions.SignedTransaction
import net.corda.v5.ledger.consensual.ConsensualState
import java.security.PublicKey
import java.security.SecureRandom
import java.time.Instant

@DoNotImplement
interface ConsensualTransactionBuilder {
    val merkleTreeFactory: MerkleTreeFactory
    val digestService: DigestService
    val secureRandom: SecureRandom
    val serializer: SerializationService
    val signingService: SigningService

    val timeStamp: Instant?
    fun withTimeStamp(timeStamp: Instant): ConsensualTransactionBuilder

    val consensualStates: List<ConsensualState>
    fun withConsensualState(consensualState: ConsensualState) : ConsensualTransactionBuilder

    fun sign(publicKey: PublicKey): SignedTransaction
}