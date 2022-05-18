package net.corda.v5.cipher.suite.schemes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KeySchemeTemplatesJavaApiTest {
    @Test
    public void constantsTests() {
        assertNotNull(KeySchemeTemplates.ID_CURVE_25519PH);
        assertNotNull(KeySchemeTemplates.SHA512_256);
        assertNotNull(KeySchemeTemplates.RSA_TEMPLATE);
        assertNotNull(KeySchemeTemplates.ECDSA_SECP256K1_TEMPLATE);
        assertNotNull(KeySchemeTemplates.ECDSA_SECP256R1_TEMPLATE);
        assertNotNull(KeySchemeTemplates.EDDSA_ED25519_TEMPLATE);
        assertNotNull(KeySchemeTemplates.SPHINCS256_TEMPLATE);
        assertNotNull(KeySchemeTemplates.SM2_TEMPLATE);
        assertNotNull(KeySchemeTemplates.GOST3410_GOST3411_TEMPLATE);
        assertNotNull(KeySchemeTemplates.COMPOSITE_KEY_TEMPLATE);
    }
}
