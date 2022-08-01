package net.corda.v5.ledger.common.transactions

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.serialization.SerializationService
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash

@DoNotImplement
@CordaSerializable
interface SignedTransaction {
    val id: SecureHash
    val sigs: List<DigitalSignatureAndMetadata>

    fun <T: LedgerTransaction>toLedgerTransaction(serializer: SerializationService): T

    /** Returns the same transaction but with an additional (unchecked) signature. */
    fun withAdditionalSignature(sig: DigitalSignatureAndMetadata): SignedTransaction

    /** Returns the same transaction but with an additional (unchecked) signatures. */
    fun withAdditionalSignatures(sigList: Iterable<DigitalSignatureAndMetadata>): SignedTransaction

    //TODO(DP1: missingSigningKeys() and missingSigningKeys()
}

/** Alias for [SignedTransaction.withAdditionalSignature] to let you use Kotlin operator overloading. */
operator fun SignedTransaction.plus(sig: DigitalSignatureAndMetadata) = withAdditionalSignature(sig)

/** Alias for [SignedTransaction.withAdditionalSignatures] to let you use Kotlin operator overloading. */
operator fun SignedTransaction.plus(sigList: Collection<DigitalSignatureAndMetadata>) = withAdditionalSignatures(sigList)
