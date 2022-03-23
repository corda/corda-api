package net.corda.packaging.test.workflow

import net.corda.systemflows.ReceiveFinalityFlow
import net.corda.systemflows.SignTransactionFlow
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.messaging.FlowSession
import net.corda.v5.application.flows.core.InitiatedBy
import net.corda.v5.application.flows.core.InitiatingFlow
import net.corda.v5.application.flows.core.StartableByRPC
import net.corda.v5.application.flows.engine.FlowEngine
import net.corda.v5.application.flows.flowservices.FlowIdentity
import net.corda.v5.application.flows.messaging.FlowMessaging
import net.corda.v5.application.flows.core.CordaInject
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.ledger.services.TransactionService
import net.corda.v5.ledger.services.TransactionVerificationService
import net.corda.v5.ledger.transactions.SignedTransaction
import net.corda.v5.ledger.transactions.TransactionBuilderFactory

@InitiatingFlow
@StartableByRPC
class PackagingTestFlow : Flow<Unit> {

    @CordaInject
    lateinit var transactionBuilderFactory: TransactionBuilderFactory

    @CordaInject
    lateinit var flowIdentity: FlowIdentity

    @CordaInject
    lateinit var flowEngine: FlowEngine

    @CordaInject
    lateinit var flowMessaging: FlowMessaging

    @CordaInject
    lateinit var transactionService: TransactionService

    @Suspendable
    override fun call() {}
}

@InitiatedBy(PackagingTestFlow::class)
class PackagingTestFlowResponder(private val otherSide : FlowSession) : Flow<SignedTransaction> {

    @CordaInject
    lateinit var flowEngine: FlowEngine

    @CordaInject
    lateinit var transactionVerificationService: TransactionVerificationService

    @Suspendable
    override fun call(): SignedTransaction {
        val signTransactionFlow = object : SignTransactionFlow(otherSide) {
            override fun checkTransaction(stx: SignedTransaction) {
                transactionVerificationService.verify(stx, false)
            }
        }
        val expectedTxId = flowEngine.subFlow(signTransactionFlow).id
        return flowEngine.subFlow(ReceiveFinalityFlow(otherSide, expectedTxId))
    }
}