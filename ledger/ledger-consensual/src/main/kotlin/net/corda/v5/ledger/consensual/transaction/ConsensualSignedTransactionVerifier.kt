package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.Suspendable

@CordaSerializable
fun interface ConsensualSignedTransactionVerifier {

    // Should this return a Try.Success/Error? Or control purely through exceptions, but this means that should not
    // be used to determine behaviour in the peer flow.
    @Suspendable
    fun verify(signedTransaction: ConsensualSignedTransaction)
}