@file:JvmName("LedgerMemberInfo")
package net.corda.v5.ledger

import net.corda.v5.application.identity.Party
import net.corda.v5.membership.MemberInfo
import net.corda.v5.membership.getValue

const val NOTARY_SERVICE_PARTY = "corda.notaryServiceParty"

val MemberInfo.notaryServiceParty: Party?
    get() = memberProvidedContext.getValue(NOTARY_SERVICE_PARTY)
