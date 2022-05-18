@file:JvmName("SignatureSpecs")

package net.corda.v5.crypto

@JvmField
val NaSignatureSpec: SignatureSpec = SignatureSpec(
    signatureName = "na"
)

@JvmField
val RSA_SHA256_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA256withRSA"
)

@JvmField
val ECDSA_SECP256K1_SHA256_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA256withECDSA"
)

@JvmField
val ECDSA_SECP256R1_SHA256_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA256withECDSA"
)

@JvmField
val EDDSA_ED25519_NONE_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "EdDSA"
)

@JvmField
val SPHINCS256_SHA512_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA512withSPHINCS256"
)

@JvmField
val SM2_SM3_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SM3withSM2"
)

@JvmField
val GOST3410_GOST3411_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "GOST3411withGOST3410"
)

