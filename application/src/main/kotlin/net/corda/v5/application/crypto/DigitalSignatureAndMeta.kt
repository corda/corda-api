package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.DigitalSignature
import java.security.PublicKey

/**
 * A wrapper over the signature output accompanied by signer's public key and signature metadata.
 * This is similar to [DigitalSignature.WithKey], but targeted to DLT transaction (or block of transactions) signatures.
 * This has been renamed from TransactionSignature for Corda 5.
 * @property bytes actual bytes of the cryptographic signature.
 * @property by [PublicKey] of the signer.
 * @property signatureMetadata attached [SignatureMetadata] for this signature.
 */
// I've tweaked this slightly to make it have a signature, rather than inherit from the signature class. The metadata
// has also been changed.
@CordaSerializable
data class DigitalSignatureAndMeta(
    val signature: DigitalSignature.WithKey,
    val metadata: SignatureMetadata
) {
    val by = signature.by
}

