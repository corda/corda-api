package net.corda.v5.application.crypto

import java.security.PublicKey
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.DigitalSignature

/**
 * A wrapper over the signature output accompanied by signer's public key and signature metadata.
 *
 * @property signature The signature that was applied.
 * @property metadata Attached [DigitalSignatureMetadata] for this signature.
 * @property by The [PublicKey] that created the signature.
 *
 * @constructor Creates a [DigitalSignatureAndMetadata].
 */
// TODO - remove this since public key should be handled separately from the signature, and not simply extracted from it
@Deprecated
@CordaSerializable
data class DigitalSignatureAndMetadata(
    val signature: DigitalSignature.WithKey,
    val metadata: DigitalSignatureMetadata
) {
    val by: PublicKey = signature.by
}
