package net.corda.v5.membership.identity

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

/**
 * The member info consist of two parts:
 * Member provided context: Parameters added and signed by member as a part of initial MemberInfo proposal.
 * MGM provided context: Parameters added by MGM as a part of member acceptance.
 */
@CordaSerializable
interface MemberInfo {

    val memberProvidedContext: MemberContext

    val mgmProvidedContext: MemberContext

    /**
     * Member's X.500 name.
     * x.500 name is unique within the group and cannot be changed while the membership exists.
     */
    val name: MemberX500Name

    /**
     * ID of the membership group member is part of.
     */
    val groupID: String

    /** List of current and previous (rotated) identity keys, which member can still use to sign unspent transactions on ledger. */
    val identityKeys: List<PublicKey>

    /** Corda platform version */
    val platformVersion: Int

    /** An arbitrary number incremented each time the [MemberInfo] is changed. */
    val serial: Long

    /** Checks the status of the member. */
    val isActive: Boolean
}