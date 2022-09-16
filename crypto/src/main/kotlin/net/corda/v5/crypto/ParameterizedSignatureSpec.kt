package net.corda.v5.crypto

import java.security.spec.AlgorithmParameterSpec

/**
 * This class is used to define a digital signature scheme which has the additional algorithm parameters,
 * such as RSASSA-PSS
 *
 * @param signatureName a signature-scheme name as required to create [java.security.Signature]
 * objects (e.g. "SHA256withECDSA")
 * @param params signature parameters, like if RSASSA-PSS is being used then in order to avoid
 * using the default SHA1 you must specify the signature parameters explicitly.
 *
 * When used for signing the [signatureName] must match the corresponding key scheme, e.g. you cannot use
 * "SHA256withECDSA" with "RSA" keys.
 */
class ParameterizedSignatureSpec(
    signatureName: String,
    /**
     * The signature parameters, like if RSASSA-PSS is being used then in order to avoid
     * using the default SHA1 you must specify the signature parameters explicitly.
     */
    val params: AlgorithmParameterSpec
) : SignatureSpec(signatureName) {
    /**
     * Converts a [ParameterizedSignatureSpec] object to a string representation.
     */
    override fun toString(): String = "$signatureName:${params::class.java.simpleName}"
}