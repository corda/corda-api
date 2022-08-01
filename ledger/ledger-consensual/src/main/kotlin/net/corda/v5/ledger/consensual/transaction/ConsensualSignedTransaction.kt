package net.corda.v5.ledger.consensual.transaction

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash

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

    //TODO(DP1: missingSigningKeys() and missingSigningKeys()
}

/** Alias for [ConsensualSignedTransaction.withAdditionalSignature] to let you use Kotlin operator overloading. */
operator fun ConsensualSignedTransaction.plus(sig: DigitalSignatureAndMetadata) = withAdditionalSignature(sig)

/** Alias for [ConsensualSignedTransaction.withAdditionalSignatures] to let you use Kotlin operator overloading. */
operator fun ConsensualSignedTransaction.plus(sigList: Collection<DigitalSignatureAndMetadata>) = withAdditionalSignatures(sigList)
