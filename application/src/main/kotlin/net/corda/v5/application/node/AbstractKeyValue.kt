package net.corda.v5.application.node

import net.corda.data.WireKeyValuePair
import net.corda.v5.application.identity.CordaX500Name
import java.security.PublicKey

val keyEncodingService = DummyKeyEncodingService()

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
        Int::class -> get(key)?.toInt() as T
        Long::class -> get(key)?.toLong() as T
        Short::class -> get(key)?.toShort() as T
        Float::class -> get(key)?.toFloat() as T
        Double::class -> get(key)?.toDouble() as T
        String::class -> get(key) as T
        CordaX500Name::class -> get(key)?.let { CordaX500Name.parse(it) } as T
        PublicKey::class -> get(key)?.let { keyEncodingService.decodePublicKey(it) } as T
        else -> throw IllegalStateException("Unknown Generic Type")
    }
}