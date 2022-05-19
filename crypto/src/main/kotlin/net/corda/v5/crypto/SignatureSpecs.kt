@file:JvmName("SignatureSpecs")

package net.corda.v5.crypto

import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec

@JvmField
val NaSignatureSpec: SignatureSpec = SignatureSpec(
    signatureName = "na"
)

@JvmField
val RSA_SHA256_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA256withRSA"
)

@JvmField
val RSA_SHA384_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA384withRSA"
)

@JvmField
val RSA_SHA512_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA512withRSA"
)

@JvmField
val RSASSA_PSS_SHA256_SIGNATURE_SPEC = SignatureSpec(
    "RSASSA-PSS",
    PSSParameterSpec(
        "SHA-256",
        "MGF1",
        MGF1ParameterSpec.SHA256,
        32,
        1
    )
)

@JvmField
val RSASSA_PSS_SHA384_SIGNATURE_SPEC = SignatureSpec(
    "RSASSA-PSS",
    PSSParameterSpec(
        "SHA-384",
        "MGF1",
        MGF1ParameterSpec.SHA384,
        32,
        1
    )
)

@JvmField
val RSASSA_PSS_SHA512_SIGNATURE_SPEC = SignatureSpec(
    "RSASSA-PSS",
    PSSParameterSpec(
        "SHA-512",
        "MGF1",
        MGF1ParameterSpec.SHA512,
        32,
        1
    )
)

@JvmField
val ECDSA_SHA256_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA256withECDSA"
)

@JvmField
val ECDSA_SHA384_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA384withECDSA"
)

@JvmField
val ECDSA_SHA512_SIGNATURE_SPEC = SignatureSpec(
    signatureName = "SHA512withECDSA"
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

