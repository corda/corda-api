package net.corda.v5.cipher.suite

/**
 * Holding class for the private key material.
 *
 * @property keyMaterial The encoded and encrypted private key.
 * @property masterKeyAlias The wrapping key's alias which was used for wrapping.
 * @property encodingVersion The encoding version which was used to encode the private key.
 */
class SigningWrappedSpec(
    val keyMaterial: ByteArray,
    val masterKeyAlias: String,
    val encodingVersion: Int
) : SigningSpec