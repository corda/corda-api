package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.CordaSerializable

/**
 * SignatureMeta is required to add extra meta-data to a transaction's signature.
 * It currently supports platformVersion only, but it can be extended to support a universal digital
 * signature model enabling partial signatures and attaching extra information, such as a user's timestamp or other
 * application-specific fields.
 *
 * @param platformVersion current DLT version.
 */
/*
JH: This is referred to elsewhere as a ledger concept but it could be made more generic. I think we may want to think a
bit about platform versioning too. What version is this? It could be a ledger version, in which case this lives in
ledger, but if it's not that then it might be useful to discuss what it is instead.
 */
@CordaSerializable
data class SignatureMetadata(val platformVersion: Int)
