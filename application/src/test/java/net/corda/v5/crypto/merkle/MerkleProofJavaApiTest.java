package net.corda.v5.crypto.merkle;

import net.corda.v5.crypto.SecureHash;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MerkleProofJavaApiTest {

    private final MerkleProof merkleProof = mock(MerkleProof.class);

    @Test
    void verify() {
        when(merkleProof.verify(any(), any())).thenReturn(true);

        final SecureHash hash = new SecureHash("SHA-256", "123".getBytes());
        final MerkleTreeHashDigest digest = mock(MerkleTreeHashDigest.class);
        final boolean result = merkleProof.verify(hash, digest);

        Assertions.assertThat(result).isEqualTo(true);
    }
    @Test
    void calculateRoot() {
        final SecureHash rootHash = new SecureHash("SHA-256", "456".getBytes());
        when(merkleProof.calculateRoot(any())).thenReturn(rootHash);

        final SecureHash hash = new SecureHash("SHA-256", "123".getBytes());
        final MerkleTreeHashDigest digest = mock(MerkleTreeHashDigest.class);
        final SecureHash result = merkleProof.calculateRoot(digest);

        Assertions.assertThat(result).isEqualTo(rootHash);
    }
}
