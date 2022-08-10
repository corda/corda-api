package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.ledger.consensual.ConsensualState
import java.security.PublicKey
import java.time.Instant

@DoNotImplement
interface ConsensualTransactionBuilder {
    val timeStamp: Instant?
    fun withTimeStamp(timeStamp: Instant): ConsensualTransactionBuilder

    val states: List<ConsensualState>
    fun withState(state: ConsensualState) : ConsensualTransactionBuilder

    fun signInitial(publicKey: PublicKey): ConsensualSignedTransaction
}