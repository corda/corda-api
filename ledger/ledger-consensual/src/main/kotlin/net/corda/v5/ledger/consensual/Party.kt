package net.corda.v5.ledger.consensual

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.types.MemberX500Name
import net.corda.v5.crypto.toStringShort
import java.security.PublicKey

/**
 * Representation of a Party.
 */
@CordaSerializable
class Party(
    /**
     * @property name X500 Name of the party.
     */
    val name: MemberX500Name,
    /**
     * @property owningKey The [PublicKey] owned by the party.
     */
    val owningKey: PublicKey
) {

    /**
     * Concise, readable String representation of a Party.
     */
    val description: String get() = "$name (owningKey = ${owningKey.toStringShort()})"

    override fun toString() = name.toString()
    override fun equals(other: Any?): Boolean =
        other === this ||
                other is Party &&
                other.owningKey == owningKey &&
                other.name == name
    override fun hashCode(): Int = owningKey.hashCode()
}