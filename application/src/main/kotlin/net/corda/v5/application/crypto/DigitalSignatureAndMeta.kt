package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.DigitalSignature

/**
 * A wrapper over the signature output accompanied by signer's public key and signature metadata.
 *
 * @property signature The signature that was applied.
 * @property metadata attached [SignatureMetadata] for this signature.
 */
@CordaSerializable
data class DigitalSignatureAndMeta(
    val signature: DigitalSignature.WithKey,
    val metadata: SignatureMetadata
) {
    val by = signature.by
}
