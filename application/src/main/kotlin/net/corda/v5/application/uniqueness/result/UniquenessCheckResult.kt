package net.corda.v5.application.uniqueness.result

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata

/** The outcome of committing and signing a transaction. */
sealed class UniquenessCheckResult {
    /** Indicates that all states have been committed successfully. */
    data class Success(val signature: DigitalSignatureAndMetadata) : UniquenessCheckResult()

    /** Indicates that the transaction has not been committed. */
    // TODO, this should not be a `String` in the future, however,
    //  we'd need to move the `UniquenessCheckInternalError` class
    //  from corda-runtime-os to API for a proper representation
    data class Failure(val error: String) : UniquenessCheckResult()
}