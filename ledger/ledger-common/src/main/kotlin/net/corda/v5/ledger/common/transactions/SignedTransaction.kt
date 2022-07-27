package net.corda.v5.ledger.common.transactions

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.base.annotations.DoNotImplement
import java.security.PublicKey

@DoNotImplement
interface SignedTransaction {
    val wireTransaction: WireTransaction
    val ledgerTransaction: LedgerTransaction //TODO(do we need this? Or rather delegate some properties through?)

    val sigs: List<DigitalSignatureAndMetadata>

    /** Specifies all the public keys that require signatures for the transaction to be valid. */
    val requiredSigningKeys: List<PublicKey> //TODO(was a set previously.)

    /** Returns the same transaction but with an additional (unchecked) signature. */
    fun withAdditionalSignature(sig: DigitalSignatureAndMetadata): SignedTransaction

    /** Returns the same transaction but with an additional (unchecked) signatures. */
    fun withAdditionalSignatures(sigList: Iterable<DigitalSignatureAndMetadata>): SignedTransaction

    /** Alias for [withAdditionalSignature] to let you use Kotlin operator overloading. */
    operator fun plus(sig: DigitalSignatureAndMetadata) = withAdditionalSignature(sig)

    /** Alias for [withAdditionalSignatures] to let you use Kotlin operator overloading. */
    operator fun plus(sigList: Collection<DigitalSignatureAndMetadata>) = withAdditionalSignatures(sigList)

    //TODO(DP1: missingSigningKeys() and missingSigningKeys()

}