package net.corda.v5.crypto

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.types.OpaqueBytes
import java.security.PublicKey

/**
 * A representation of a digital signature.
 *
 * @property bytes The signature.
 * @param spec The signature specification, which is a boxed string such as "SHA256withRSA".
 * @param publicKeyHash The hash of the public key of the key pair used to produce the signature. The reason
 *                      this is not the plain public key is that people should never just pick up the key from
 *                      a digital signature itself to check the signature is valid. Instead, the only safe thing
 *                      to do is to get hold of the set of valid public keys by some other means. Since there may
 *                      in general have been a large number of key pairs which were used, we include the hash so
 *                      you don't have to check a large population of keys.
 */

@CordaSerializable
class DigitalSignature(
    val bytes: ByteArray,
    val spec: SignatureSpec,
    val publicKeyHash: SecureHash
)

