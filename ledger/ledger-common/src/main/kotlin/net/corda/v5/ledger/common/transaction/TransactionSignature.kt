package net.corda.v5.ledger.common.transaction

import net.corda.v5.application.crypto.DigitalSignatureMetadata
import java.security.PublicKey
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.DigitalSignature
import net.corda.v5.crypto.merkle.MerkleProof

/**
 * A wrapper over the signature output accompanied by signer's public key and signature metadata.
 *
 * @property signature The output signature.
 * @property metadata Attached [DigitalSignatureMetadata] for this signature.
 * @property batchProof The signed [MerkleProof] for batch signing cases.
 * @property by The [PublicKey] that created the signature.
 *
 * @constructor Creates a [TransactionSignature].
 */
@CordaSerializable
data class TransactionSignature(
    val signature: DigitalSignature.WithKey,
    val metadata: DigitalSignatureMetadata,
    val batchProof: MerkleProof?
) {
    constructor(signature: DigitalSignature.WithKey, metadata: DigitalSignatureMetadata):
            this( signature, metadata, null)

    val by: PublicKey = signature.by
}
