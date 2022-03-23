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
/*
JH: I haven't done anything to this class but I don't think it should be here in this form. Options are:
- Make it transaction specific and move it to ledger
- Make it more generic and move it to crypto.
 */
@CordaSerializable
class DigitalSignatureAndMeta(
    bytes: ByteArray,
    val by: PublicKey,
    val signatureMetadata: SignatureMetadata
) : DigitalSignature(bytes) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DigitalSignatureAndMeta) return false

        return (bytes.contentEquals(other.bytes)
                && by == other.by
                && signatureMetadata == other.signatureMetadata)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + by.hashCode()
        result = 31 * result + signatureMetadata.hashCode()
        return result
    }
}
