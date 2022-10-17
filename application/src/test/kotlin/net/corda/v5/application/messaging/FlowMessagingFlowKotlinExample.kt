package net.corda.v5.application.messaging

import net.corda.v5.application.flows.CordaInject
import net.corda.v5.application.flows.RPCRequestData
import net.corda.v5.application.flows.RPCStartableFlow
import net.corda.v5.base.types.MemberX500Name

/**
 * KDoc example compilation test
 */
class FlowMessagingFlowKotlinExample : RPCStartableFlow  {

    @CordaInject
    lateinit var flowMessaging: FlowMessaging

    override fun call(requestBody: RPCRequestData): String {
        val counterparty = MemberX500Name.parse("CN=Alice, O=Alice Corp, L=LDN, C=GB")

        val session = flowMessaging.initiateFlow(counterparty)

        val result = session.sendAndReceive(String::class.java, "hello")

        session.close()

        return result
    }
}
