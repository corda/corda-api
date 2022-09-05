package net.corda.v5.application.marshalling

import net.corda.v5.application.flows.CordaInject
import net.corda.v5.application.flows.RPCRequestData
import net.corda.v5.application.flows.RPCStartableFlow
import net.corda.v5.application.flows.getRequestBodyAs

/**
 * KDoc example compilation test
 */
class MarshallingServiceFlowKotlinExample : RPCStartableFlow {

    @CordaInject
    lateinit var jsonMarshallingService: JsonMarshallingService

    override fun call(requestBody: RPCRequestData): String {
        val request = requestBody.getRequestBodyAs<MyFlowRequest>(jsonMarshallingService)

        return request.name
    }

    data class MyFlowRequest(val name: String)
}
