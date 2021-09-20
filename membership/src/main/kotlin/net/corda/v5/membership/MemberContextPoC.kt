package net.corda.v5.membership

import net.corda.data.WireKeyValuePair
import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
interface MemberContextPoC {
    operator fun get(key: String): String?

    val keys: Set<String>

    val map: Map<String, String>
}

inline fun <reified T> MemberContextPoC.getValue(key: String): T = this[key] as T

fun MemberContextPoC.convertToListOfWireKeyValuePair(): List<WireKeyValuePair> {
    val result = mutableListOf<WireKeyValuePair>()
    this.keys.forEach { result.add(WireKeyValuePair(it, this.get(it))) }
    return result
}