package net.corda.v5.application.node

import net.corda.data.WireKeyValuePair
import net.corda.v5.application.identity.CordaX500Name
import java.time.Instant

interface AbstractKeyValue {
    operator fun get(key: String): String?

    val keys: Set<String>

    val entries: Set<Map.Entry<String, String>>
}


fun AbstractKeyValue.convertToListOfWireKeyValuePair(): List<WireKeyValuePair> {
    val result = mutableListOf<WireKeyValuePair>()
    this.entries.forEach { result.add(WireKeyValuePair(it.key, it.value)) }
    return result
}

inline fun <reified T> AbstractKeyValue.readAs(key: String): T {
    return when (T::class) {
        Int::class -> this[key]?.toInt() as T
        Long::class -> this[key]?.toLong() as T
        Short::class -> this[key]?.toShort() as T
        Float::class -> this[key]?.toFloat() as T
        Double::class -> this[key]?.toDouble() as T
        String::class -> this[key] as T
        CordaX500Name::class -> this[key]?.let { CordaX500Name.parse(it) } as T
        Instant::class -> this[key]?.let { Instant.parse(it) } as T
        else -> throw IllegalStateException("Unknown Generic Type")
    }
}