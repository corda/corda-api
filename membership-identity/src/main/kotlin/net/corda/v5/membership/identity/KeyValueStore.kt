package net.corda.v5.membership.identity

import net.corda.data.WireKeyValuePair
import net.corda.v5.base.exceptions.CordaRuntimeException

/**
 * Common interface for supporting Map<String, String> structure.
 */
interface KeyValueStore {
    operator fun get(key: String): String?

    val keys: Set<String>

    val entries: Set<Map.Entry<String, String>>

    fun <T> parse(key: String,
                  clazz: Class<out T>,
                  stringObjectConverter: StringObjectConverter<T>? = null): T

    fun <T> parseOrNull(key: String,
                        clazz: Class<out T>,
                        stringObjectConverter: StringObjectConverter<T>? = null): T?

    fun <T> parseList(itemKeyPrefix: String,
                      clazz: Class<out T>,
                      converter: StringObjectConverter<T>? = null): List<T>
}

/**
 * Extension function for converting the content of [KeyValueStore] to a list of [WireKeyValuePair].
 * This conversion is required, because of the avro serialization done on the P2P layer.
 */
fun KeyValueStore.toWireKeyValuePairList(): List<WireKeyValuePair> = entries.map { WireKeyValuePair(it.key, it.value) }

/**
 * Extension function for reading and parsing the String values stored in the values to actual objects.
 */
inline fun <reified T> KeyValueStore.parse(
    key: String,
    stringObjectConverter: StringObjectConverter<T>? = null
): T {
        return parse(key, T::class.java, stringObjectConverter)
}

/**
 * Extension function for reading and parsing the String values stored in the values to actual objects or return null.
 */
inline fun <reified T> KeyValueStore.parseOrNull(
    key: String,
    stringObjectConverter: StringObjectConverter<T>? = null
): T? {
    return parseOrNull(key, T::class.java, stringObjectConverter)
}

/**
 * Extension function for reading and parsing the String values stored in the values to an actual list of objects.
 */
inline fun <reified T> KeyValueStore.parseList(
    itemKeyPrefix: String,
    stringObjectConverter: StringObjectConverter<T>? = null
): List<T> {
    return parseList(itemKeyPrefix, T::class.java, stringObjectConverter)
}

/**
 * Exception, being thrown when a value for a specific key cannot be found in [KeyValueStore].
 */
class ValueNotFoundException(message: String?) : CordaRuntimeException(message)