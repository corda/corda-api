package net.corda.v5.application.marshalling;

import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.flows.RPCRequestData;
import net.corda.v5.application.flows.RPCStartableFlow;

/**
 * KDoc example compilation test
 */
class MarshallingServiceFlowJavaExample implements RPCStartableFlow {

    @CordaInject
    public JsonMarshallingService jsonMarshallingService;

    @Override
    public String call(RPCRequestData requestBody) {
        MyFlowRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, MyFlowRequest.class);
        return request.name;
    }

    class MyFlowRequest {
        public String name;
    }
}

