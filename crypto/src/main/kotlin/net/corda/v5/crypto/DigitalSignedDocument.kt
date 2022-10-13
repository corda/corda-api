package net.corda.v5.crypto

import net.corda.v5.base.annotations.CordaSerializable


@CordaSerializable
/** A representation of a signed document,
 *
 * @param payload the payload that is to be signed, which must be Corda serializable
 * @param metadata additional records. Needs to be Corda serializable..
 */
class DigitalSignedDocument<T, M>(
    val payload: T,
    val metadata: M,
    val signatures: List<DigitalSignature>
)
