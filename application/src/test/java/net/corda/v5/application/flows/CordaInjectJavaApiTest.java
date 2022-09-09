package net.corda.v5.application.flows;

import net.corda.v5.application.messaging.FlowMessaging;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CordaInjectJavaApiTest {

    private static final String DONE = "done";

    @Test
    public void cordaInject() {
        assertEquals(DONE, new MyFlow().call(mock(RPCRequestData.class)));
    }

    static class MyFlow implements RPCStartableFlow {

        @CordaInject
        FlowEngine flowEngine;

        @CordaInject
        FlowMessaging flowMessaging;

        @NotNull
        @Override
        public String call(@NotNull RPCRequestData requestBody) {
            return DONE;
        }
    }
}
