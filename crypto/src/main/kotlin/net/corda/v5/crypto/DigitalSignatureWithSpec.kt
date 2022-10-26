package net.corda.v5.crypto

class DigitalSignatureWithSpec(
    val signature: DigitalSignature.WithId,
    val signatureSpec: SignatureSpec,
)