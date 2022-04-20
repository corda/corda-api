package net.corda.v5.application.crypto

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.CompositeKey
import net.corda.v5.crypto.DigitalSignature
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*

/**
 * The [KeyManagementService] is responsible for storing and using private keys to sign things. An implementation of this may, for example,
 * call out to a hardware security module that enforces various auditing and frequency-of-use requirements.
 */
@DoNotImplement
interface KeyManagementService : CordaServiceInjectable, CordaFlowInjectable {

    /**
     * Retrieve all public keys for the current member identity.
     *
     * The filter may be used to select a subset of the possible keys - for example, to select just those keys belonging
     * to a particular account, or to ensure that only non-rotated keys are returned.
     *
     * @param filter The filter criteria to select keys by. Returned keys will match at least one of the selected tags
     *               for each parameter provided. (This API is a draft and will be expanded on later.)
     * @return A list of public keys that match the filter.
     */
    @Suspendable
    fun findKeys(filter: Map<String, Set<String>>): List<PublicKey>

    /**
     * Using the provided signing [PublicKey], internally looks up the matching [PrivateKey] and signs the data.
     *
     * @param bytes The data to sign over using the chosen key.
     *
     * @param publicKey The [PublicKey] partner to an internally held [PrivateKey], either derived from the node's primary identity, or
     * previously generated via the [freshKey] method. If the [PublicKey] is actually a [CompositeKey], the first leaf signing key hosted by
     * the node is used.
     *
     * @return A [DigitalSignature.WithKey] representing the signed data and the [PublicKey] that belongs to the same [KeyPair] as the
     * [PrivateKey] that signed the data.
     *
     * @throws IllegalArgumentException If the input key is not a member of [keys].
     */
    @Suspendable
    fun sign(bytes: ByteArray, publicKey: PublicKey): DigitalSignature.WithKey

    /**
     * Using the provided signing [PublicKey] internally looks up the matching [PrivateKey] and signs the [SignableData].
     *
     * @param signableData A wrapper over transaction id (Merkle root) and signature metadata.
     *
     * @param publicKey The [PublicKey] partner to an internally held [PrivateKey], either derived from the node's primary identity, or
     * previously generated via the [freshKey] method. If the [PublicKey] is actually a [CompositeKey], the first leaf signing key hosted by
     * the node is used.
     *
     * @return A [DigitalSignatureAndMetadata] representing the signed data and the [PublicKey] that belongs to the same [KeyPair] as the
     * [PrivateKey] that signed the data.
     *
     * @throws IllegalArgumentException If the input key is not a member of [keys].
     */
    @Suspendable
    fun sign(signableData: SignableData, publicKey: PublicKey): DigitalSignatureAndMetadata
}
