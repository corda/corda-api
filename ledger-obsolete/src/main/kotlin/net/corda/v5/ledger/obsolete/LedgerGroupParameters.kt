@file:JvmName("LedgerGroupParameters")

package net.corda.v5.ledger.obsolete

import net.corda.v5.membership.GroupParameters

private const val NOTARIES_KEY = "corda.notary"

val GroupParameters.notaries: List<NotaryInfo>
    get() = parseList(
        NOTARIES_KEY,
        NotaryInfo::class.java
    )
