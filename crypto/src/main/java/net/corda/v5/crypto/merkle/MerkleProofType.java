package net.corda.v5.crypto.merkle;

import net.corda.v5.base.annotations.CordaSerializable;

/**
 * [MerkleProofType] represents what type of [MerkleProof] was created.
 */
@CordaSerializable
public enum MerkleProofType {
    AUDIT,
    SIZE;
}