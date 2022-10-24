package net.corda.v5.crypto

class DigitalSignatureWithSpec(
    val bytes: ByteArray,
    val spec: SignatureSpec,
    val publicKeyHash: SecureHash
)