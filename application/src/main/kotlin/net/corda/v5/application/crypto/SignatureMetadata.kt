package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.CordaSerializable
import java.time.Instant

/*
JH: This is referred to elsewhere as a ledger concept but it could be made more generic. I think we may want to think a
bit about platform versioning too. What version is this? It could be a ledger version, in which case this lives in
ledger, but if it's not that then it might be useful to discuss what it is instead.
 */
/**
 * Metadata attached to a signature.
 *
 * The use of this metadata is decided by API layers above application. For example, the ledger implementation may
 * populate some properties when transaction signatures are requested.
 *
 * Note that the metadata itself is not signed over.
 *
 * @property timestamp The timestamp at which the signature was applied
 * @property properties A set of properties for this signature. Content depends on API layers above application.
 */
@CordaSerializable
data class SignatureMetadata(val timestamp: Instant,
                             val properties: Map<String, String>)
