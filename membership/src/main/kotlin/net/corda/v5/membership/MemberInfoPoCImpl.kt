package net.corda.v5.membership

import net.corda.impl.application.identity.PartyImpl
import net.corda.v5.application.identity.CordaX500Name
import net.corda.v5.application.identity.Party
import net.corda.v5.base.annotations.CordaSerializable
import org.apache.commons.lang3.builder.HashCodeBuilder
import java.security.PublicKey

@CordaSerializable
class MemberInfoPoCImpl(
    override val memberProvidedContext: MemberContextPoC,
    override val mgmProvidedContext: MemberContextPoC,
    val keyEncodingService: DummyKeyEncodingService
) : MemberInfoPoC {

    override val party: Party = readParty()

    private fun readParty() = PartyImpl(
        memberProvidedContext.map.readAs(MemberInfoExtensionPoC.PARTY_NAME),
        memberProvidedContext.map.readAs(MemberInfoExtensionPoC.PARTY_KEY)
    )

    private inline fun <reified T> Map<String, String>.readAs(key: String): T {
        return when (T::class) {
            Int::class -> getValue(key).toInt() as T
            Long::class -> getValue(key).toLong() as T
            Short::class -> getValue(key).toShort() as T
            Float::class -> getValue(key).toFloat() as T
            Double::class -> getValue(key).toDouble() as T
            String::class -> getValue(key) as T
            CordaX500Name::class -> CordaX500Name.parse(getValue(key)) as T
            PublicKey::class -> keyEncodingService.decodePublicKey(getValue(key)) as T
            else -> throw IllegalStateException("Unknown Generic Type")
        }
    }

    private fun Map<String, String>.readIdentityKeys(): List<PublicKey> = this.filterKeys { it.startsWith(MemberInfoExtensionPoC.IDENTITY_KEYS) }
                                                                            .values
                                                                            .map { keyEncodingService.decodePublicKey(it) }
                                                                            .toList()

    override val identityKeys: List<PublicKey> get() = memberProvidedContext.map.readIdentityKeys()

    override val platformVersion: Int get() = memberProvidedContext.map.readAs(MemberInfoExtensionPoC.PLATFORM_VERSION)

    override val serial: Long get() = memberProvidedContext.map.readAs(MemberInfoExtensionPoC.SERIAL)

    override val isActive: Boolean get() = ( mgmProvidedContext.map.readAs(MemberInfoExtensionPoC.STATUS) as String == MemberInfoExtensionPoC.MEMBER_STATUS_ACTIVE)

    override fun toString(): String = "MemberInfo { \n memberProvidedContext { \n$memberProvidedContext\n} " +
            "mgmProvidedContext { \n$mgmProvidedContext \n}\n}"

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MemberInfoPoCImpl) return false
        if (this === other) return true
        return this.memberProvidedContext == other.memberProvidedContext && this.mgmProvidedContext == other.mgmProvidedContext
    }

    override fun hashCode() = HashCodeBuilder(71, 97)
        .append(memberProvidedContext)
        .append(mgmProvidedContext)
        .toHashCode()
}