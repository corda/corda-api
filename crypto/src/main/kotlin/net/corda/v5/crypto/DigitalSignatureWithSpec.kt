package net.corda.v5.crypto

class DigitalSignatureWithSpec(
    val signature: DigitalSignature.WithKeyHash,
    val signatureSpec: SignatureSpec,
)