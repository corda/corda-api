package net.corda.v5.application.membership

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.types.MemberX500Name
import net.corda.v5.membership.MemberInfo
import java.security.PublicKey

/**
 * The [MemberLookup] API allows flows to lookup the [MemberInfo] for any member of the network, including itself.
 */
@DoNotImplement
interface MemberLookup {

    /**
     * Gets the [MemberInfo] for the calling Flow.
     */
    @Suspendable
    fun myInfo(): MemberInfo


    /**
     * Gets the [MemberInfo] by X500 name.
     *
     * @param name the [MemberX500Name] name of the member to lookup.
     * @return an instance of [MemberInfo] for the given [MemberX500Name] name or null if no member found.
     */
    @Suspendable
    fun lookup(name: MemberX500Name): MemberInfo?

    /**
     * Gets the [MemberInfo] by members public key.
     *
     * @param key the [PublicKey] of the member to lookup.
     * @return an instance of [MemberInfo] for the given [PublicKey] name or null if no member found.
     */
    @Suspendable
    fun lookup(key: PublicKey): MemberInfo?

    /**
     * Gets a list [MemberInfo] for all members in the network
     * @return a list of [MemberInfo].
     */
    @Suspendable
    fun lookup(): List<MemberInfo>
}

