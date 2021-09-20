package net.corda.v5.membership

import net.corda.v5.application.identity.Party
import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

@CordaSerializable
interface MemberInfoPoC {
    val memberProvidedContext: MemberContextPoC

    val mgmProvidedContext: MemberContextPoC

    val party: Party

    val identityKeys: List<PublicKey>

    val platformVersion: Int

    val serial: Long

    val isActive: Boolean
}