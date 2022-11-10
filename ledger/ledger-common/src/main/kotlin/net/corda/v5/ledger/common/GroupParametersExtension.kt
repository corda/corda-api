@file:JvmName("GroupParametersExtension")

package net.corda.v5.ledger.common

import net.corda.v5.membership.GroupParameters

private const val NOTARIES_KEY = "corda.notaries"

/**
 * A list of all available notary services in the group.
 * This will parse the available notary services information from the group parameters internal property map.
 */
val GroupParameters.notaries: List<NotaryInfo>
    get() = parseList(
        NOTARIES_KEY,
        NotaryInfo::class.java
    )