package net.corda.v5.ledger.notary.plugin.core

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.exceptions.CordaRuntimeException
import net.corda.v5.crypto.SecureHash

/**
 * Representation of errors that can be returned by the notary (plugin). This is only a marker interface, the plugins
 * can define their own errors by implementing this interface. Please refer to the non-validating notary plugin for
 * a more detailed example.
 *
 * @property notaryErrorMessage The specific error message produced by the notary
 * @property txId Id of the transaction to be notarised. Can be _null_ if an error occurred before the id could be
 * resolved.
 */
@CordaSerializable
sealed class NotaryException(
    val notaryErrorMessage: String,
    val txId: SecureHash?
) : CordaRuntimeException("Unable to notarise transaction ${txId ?: "<Unknown>"} : $notaryErrorMessage") {
    abstract class NotaryExceptionFatal(
        notaryErrorMessage: String,
        txId: SecureHash?
    ) : NotaryException(notaryErrorMessage, txId)

    abstract class NotaryExceptionUnknown(
        notaryErrorMessage: String,
        txId: SecureHash?
    ) : NotaryException(notaryErrorMessage, txId)
}
