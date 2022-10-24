package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.Suspendable
import java.io.Serializable

fun interface ConsensualSignedTransactionVerifier : Serializable {

    // TODO [CORE-7027] Document that this API should throw to fail verification
    @Suspendable
    fun verify(signedTransaction: ConsensualSignedTransaction)
}