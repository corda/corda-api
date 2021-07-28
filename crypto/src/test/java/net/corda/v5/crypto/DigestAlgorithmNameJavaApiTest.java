package net.corda.v5.crypto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DigestAlgorithmNameJavaApiTest {
    @Test
    @Timeout(5)
    public void constantsTests() {
        assertEquals(8, DigestAlgorithmName.NONCE_SIZE);
        assertEquals("SHA-256", DigestAlgorithmName.SHA2_256.getName());
        assertEquals("SHA-384", DigestAlgorithmName.SHA2_384.getName());
        assertEquals("SHA-512", DigestAlgorithmName.SHA2_512.getName());
        assertEquals("SHA-256", DigestAlgorithmName.DEFAULT_ALGORITHM_NAME.getName());
    }
}
