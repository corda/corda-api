package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import java.security.PublicKey

@DoNotImplement
@CordaSerializable
interface ConsensualSignedTransaction {
    val id: SecureHash
    val sigs: List<DigitalSignatureAndMetadata>

    fun toLedgerTransaction(serializer: SerializationService): ConsensualLedgerTransaction

    /** Returns the same transaction but with an additional (unchecked) signature. */
    fun withAdditionalSignature(sig: DigitalSignatureAndMetadata): ConsensualSignedTransaction

    /** Returns the same transaction but with an additional (unchecked) signatures. */
    fun withAdditionalSignatures(sigList: Iterable<DigitalSignatureAndMetadata>): ConsensualSignedTransaction

    /** Returns the keys which have not signed the transaction */
    fun missingSigningKeys(serializer: SerializationService): Set<PublicKey>
}

/** Alias for [ConsensualSignedTransaction.withAdditionalSignature] to let you use Kotlin operator overloading. */
operator fun ConsensualSignedTransaction.plus(sig: DigitalSignatureAndMetadata) = withAdditionalSignature(sig)

/** Alias for [ConsensualSignedTransaction.withAdditionalSignatures] to let you use Kotlin operator overloading. */
operator fun ConsensualSignedTransaction.plus(sigList: Collection<DigitalSignatureAndMetadata>) = withAdditionalSignatures(sigList)
