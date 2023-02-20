package net.corda.v5.ledger.notary.plugin.core

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.exceptions.CordaRuntimeException
import net.corda.v5.crypto.SecureHash

/**
 * Exception thrown by the notary plugin if any issues are encountered while trying to commit a transaction. The
 * underlying [error] specifies the cause of failure.
 */
@CordaSerializable
class NotaryException(
    /** Cause of notarisation failure. */
    val error: NotaryError,
    /** Id of the transaction to be notarised. Can be _null_ if an error occurred before the id could be resolved. */
    val txId: SecureHash?
) : CordaRuntimeException("Unable to notarise transaction ${txId ?: "<Unknown>"} : $error") {
    constructor(error: NotaryError) : this(error, txId = null)
}
