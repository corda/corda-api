package net.corda.v5.application.crypto;

import net.corda.v5.crypto.DigitalSignature;
import net.corda.v5.crypto.SecureHash;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.PublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;

class KeyManagementServiceJavaApiTest {

    private final KeyManagementService keyManagementService = mock(KeyManagementService.class);
    private final PublicKey publicKey = mock(PublicKey.class);

    @Test
    void signWithByteArrayTest() {
        final DigitalSignature.WithKey signatureWithKey = new DigitalSignature.WithKey(publicKey, "test".getBytes());
        Mockito.when(keyManagementService.sign((byte[]) any(), any())).thenReturn(signatureWithKey);

        Assertions.assertThat(keyManagementService.sign("test".getBytes(), publicKey)).isNotNull();
        Assertions
                .assertThat(keyManagementService.sign("test".getBytes(), publicKey))
                .isEqualTo(signatureWithKey);
    }
}
