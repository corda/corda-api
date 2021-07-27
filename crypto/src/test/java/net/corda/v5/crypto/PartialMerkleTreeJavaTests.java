package net.corda.v5.crypto;

import net.corda.v5.crypto.mocks.DigestServiceMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartialMerkleTreeJavaTests {
    private static DigestService digestService;

    @BeforeAll
    public static void setup() {
        digestService = new DigestServiceMock();
    }

    @Test
    @Timeout(5)
    public void buildsPartialMerkleTree() {
        var l3 = digestService.hash("d".getBytes(), DigestAlgorithmName.SHA2_256);
        var l5 = digestService.hash("f".getBytes(), DigestAlgorithmName.SHA2_256);
        var leaves = new ArrayList<SecureHash>();
        leaves.add(l3);
        leaves.add(l5);
        var merkleTree = MerkleTree.getMerkleTree(leaves, DigestAlgorithmName.SHA2_256, digestService);
        var pmt = PartialMerkleTree.build(merkleTree, leaves);
        assertTrue(pmt.verify(merkleTree.getHash(), leaves, digestService));
    }
}
