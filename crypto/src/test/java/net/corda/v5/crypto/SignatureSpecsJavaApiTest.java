package net.corda.v5.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SignatureSpecsJavaApiTest {
    @Test
    public void constantsTests() {
        assertNotNull(SignatureSpecs.NaSignatureSpec);
        assertNotNull(SignatureSpecs.SM2_SM3_SIGNATURE_SPEC);
        assertNotNull(SignatureSpecs.ECDSA_SECP256K1_SHA256_SIGNATURE_SPEC);
        assertNotNull(SignatureSpecs.ECDSA_SECP256R1_SHA256_SIGNATURE_SPEC);
        assertNotNull(SignatureSpecs.EDDSA_ED25519_NONE_SIGNATURE_SPEC);
        assertNotNull(SignatureSpecs.GOST3410_GOST3411_SIGNATURE_SPEC);
        assertNotNull(SignatureSpecs.RSA_SHA256_SIGNATURE_SPEC);
        assertNotNull(SignatureSpecs.SPHINCS256_SHA512_SIGNATURE_SPEC);
    }
}
