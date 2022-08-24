package net.corda.v5.application.uniqueness.model

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata

/**
 * TODO Write KDocs
 */
data class UniquenessCheckResponse(
    val result: UniquenessCheckResult,
    val signatureAndMetadata: DigitalSignatureAndMetadata?
)