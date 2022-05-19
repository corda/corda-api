package net.corda.v5.cipher.suite.schemes;

import net.corda.v5.crypto.SignatureSpec;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DefaultSignatureOIDMapJavaApiTest {
    @Test
    public void infersSignatureOIDs() {
        var oid = DefaultSignatureOIDMap.inferSignatureOID(
                new MockPublicKey(),
                new SignatureSpec("SHA256withTest", null, null));
        assertNull(oid);
    }

    @Test
    public void constantsTest() {
        assertNotNull(DefaultSignatureOIDMap.SHA256_ECDSA_K1);
    }

    static class MockPublicKey implements PublicKey {

        @Override
        public String getAlgorithm() {
            return "Test";
        }

        @Override
        public String getFormat() {
            return "DER";
        }

        @Override
        public byte[] getEncoded() {
            return new byte[0];
        }
    }
}
