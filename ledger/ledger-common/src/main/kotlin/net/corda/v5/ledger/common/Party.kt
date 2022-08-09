package net.corda.v5.ledger.common

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.types.MemberX500Name
import java.security.PublicKey

/**
 * Represents a well-known identity.
 *
 * @param name The well known [MemberX500Name] that presents the current identity.
 * @param owningKey The [PublicKey]
 */
@CordaSerializable
data class Party(val name: MemberX500Name, val owningKey: PublicKey) {

    override fun toString(): String {
        return name.toString()
    }
}
