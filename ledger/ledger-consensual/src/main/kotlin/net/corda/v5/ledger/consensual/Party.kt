package net.corda.v5.ledger.consensual

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.types.MemberX500Name
import net.corda.v5.crypto.toStringShort
import java.security.PublicKey

@DoNotImplement
@CordaSerializable
interface Party {
    val name: MemberX500Name
    val owningKey: PublicKey

    val description: String get() = "$name (owningKey = ${owningKey.toStringShort()})"
}