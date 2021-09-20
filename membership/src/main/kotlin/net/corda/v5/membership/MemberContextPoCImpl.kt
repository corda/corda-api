package net.corda.v5.membership

import net.corda.v5.base.annotations.CordaSerializable
import org.apache.commons.lang3.builder.HashCodeBuilder
import java.util.*

@CordaSerializable
class MemberContextPoCImpl(private val properties: SortedMap<String, String>) : MemberContextPoC {

    override operator fun get(key: String): String? = properties[key]

    @Transient
    override val keys: Set<String> = properties.keys

    override val map: Map<String, String> = properties

    override fun toString(): String = StringBuilder().apply {
        append("MemberContext {\n")
        properties.forEach { (k, v) -> append("$k=$v\n") }
        append("}")
    }.toString()

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MemberContextPoCImpl) return false
        if (this === other) return true
        return this.properties == other.properties
    }

    override fun hashCode() = HashCodeBuilder(71, 97).apply {
        properties.forEach { (k, v) -> append(k); append(v) }
    }.toHashCode()
}
