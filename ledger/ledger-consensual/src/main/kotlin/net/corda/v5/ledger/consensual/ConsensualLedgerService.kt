package net.corda.v5.ledger.consensual

import net.corda.v5.application.crypto.SigningService
import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.DigestService
import net.corda.v5.crypto.merkle.MerkleTreeFactory
import net.corda.v5.ledger.consensual.transaction.ConsensualTransactionBuilder
import java.security.SecureRandom

/**
 * It provides access to the different Consensual Ledger Services
 */
@DoNotImplement
interface ConsensualLedgerService {
    @Suspendable
    fun getTransactionBuilder(
        merkleTreeFactory: MerkleTreeFactory,
        digestService: DigestService,
        secureRandom: SecureRandom,
        serializer: SerializationService,
        signingService: SigningService
    ): ConsensualTransactionBuilder

    /* TODO
    fun fetchTransaction(id: SecureHash)
    fun finality(sessions)
    fun receiveFinality( ()-> ... )
    ... Vault ...
    */
}