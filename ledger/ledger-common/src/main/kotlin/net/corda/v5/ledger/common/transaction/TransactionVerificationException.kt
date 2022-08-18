package net.corda.v5.ledger.common.transaction

import net.corda.v5.application.flows.exceptions.FlowException
import net.corda.v5.crypto.SecureHash

/**
 * Indicates that some aspect of the transaction named by [txId] violates the platform rules. The exact type of failure
 * is expressed using a subclass. TransactionVerificationException is a [FlowException] and thus when thrown inside
 * a flow, the details of the failure will be serialised, propagated to the peer and rethrown.
 *
 * @property txId the Merkle root hash (identifier) of the transaction that failed verification.
 */
abstract class TransactionVerificationException(val txId: SecureHash, message: String, cause: Throwable?)
    : FlowException("$message, transaction: $txId", cause)