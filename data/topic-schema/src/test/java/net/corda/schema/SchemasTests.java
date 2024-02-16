package net.corda.schema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchemasTests {
    @Test
    void cryptoTopicsShouldLookNiceInJavaApi() {
        assertNotNull(Schemas.Crypto.RPC_HSM_REGISTRATION_MESSAGE_TOPIC);
        assertNotNull(Schemas.Crypto.RPC_HSM_REGISTRATION_MESSAGE_RESPONSE_TOPIC);
        assertNotNull(Schemas.Crypto.RPC_OPS_MESSAGE_TOPIC);
        assertNotNull(Schemas.Crypto.RPC_OPS_MESSAGE_RESPONSE_TOPIC);
        assertNotNull(Schemas.Crypto.REKEY_MESSAGE_TOPIC);
        assertNotNull(Schemas.Crypto.REKEY_MESSAGE_RESPONSE_TOPIC);
        assertNotNull(Schemas.Crypto.REKEY_MESSAGE_STATUS_TOPIC);
        assertNotNull(Schemas.Crypto.REWRAP_MESSAGE_TOPIC);
        assertNotNull(Schemas.Crypto.REWRAP_MESSAGE_RESPONSE_TOPIC);
    }
}
