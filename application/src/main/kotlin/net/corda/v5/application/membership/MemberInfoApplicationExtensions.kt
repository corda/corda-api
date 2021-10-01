package net.corda.v5.application.membership

import net.corda.v5.application.identity.Party

interface MemberInfoApplicationExtensions {
    /**
     * Member identity, which includes X.500 name and identity key.
     * Party name is unique within the group and cannot be changed while the membership exists.
     */
    val party: Party
}