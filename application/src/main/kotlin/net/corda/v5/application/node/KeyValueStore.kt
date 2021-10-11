package net.corda.v5.application.node

import net.corda.data.WireKeyValuePair
import net.corda.v5.application.identity.CordaX500Name
import net.corda.v5.base.exceptions.CordaRuntimeException
import java.time.Instant

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

    /*fun <T> parseList(
        itemKeyPrefix: String, clazz: Class<out T>,
        itemFactory: (converter: StringValueConverter, keys: List<Pair<String, String>>) -> T
    ): List<T>


    fun <T> parsePrimitiveList(
        itemKeyPrefix: String, clazz: Class<out T>,
        itemFactory: (converter: StringValueConverter, value: String) -> T
    ): List<T>
     */
}

/**
 * Extension function for converting the content of [KeyValueStore] to a list of [WireKeyValuePair].
 * This conversion is required, because of the avro serialization done on the P2P layer.
 */
fun KeyValueStore.toWireKeyValuePairList(): List<WireKeyValuePair> = entries.map { WireKeyValuePair(it.key, it.value) }

/**
 * Extension function for reading and parsing the String values stored in the values to actual objects.
 *
 * @throws IllegalStateException when unknown generic type is used.
 */
inline fun <reified T> KeyValueStore.readAs(key: String): T {
    val value = this[key] ?: throw ValueNotFoundException("There is no value for '$key' key.")
    return when (T::class) {
        Int::class -> value.toInt() as T
        Long::class -> value.toLong() as T
        Short::class -> value.toShort() as T
        Float::class -> value.toFloat() as T
        Double::class -> value.toDouble() as T
        String::class -> value as T
        CordaX500Name::class -> CordaX500Name.parse(value) as T
        Instant::class -> Instant.parse(value) as T
        else -> throw IllegalStateException("Unknown Generic Type")
    }
}

/**
 * Extension function for reading and parsing the String values stored in the values to actual objects.
 *
 * @param key Name of the value we want to retrieve.
 *
 * @throws IllegalStateException when unknown generic type is used.
 */
/*
inline fun <reified T> KeyValueStore.parse(key: String): T {
    return parse(key, T::class.java)
}

 */

/**
 * Extension function for reading and parsing the String values stored in the values to actual objects.
 *
 * @param key Name of the value we want to retrieve.
 *
 * @throws IllegalStateException when unknown generic type is used.
 */
/*
inline fun <reified T> KeyValueStore.parseOrNull(key: String): T? {
    return parseOrNull(key, T::class.java)
}
 */

/**
 * Extension function for reading and parsing the String values stored in the values to an actual list of objects.
 *
 * @param itemKeyPrefix Prefix for the name of the value we want to retrieve.
 *
 * @throws IllegalStateException when unknown generic type is used.
 */
/*inline fun <reified T> KeyValueStore.parseList(itemKeyPrefix: String): List<T> {
    return parseList(itemKeyPrefix, T::class.java)
}*/

/*inline fun <reified T> KeyValueStore.parseList(
    itemKeyPrefix: String,
    noinline itemFactory: (converter: StringValueConverter, keys: List<Pair<String, String>>) -> T
): List<T> {
    return parseList(itemKeyPrefix, T::class.java, itemFactory)
}

inline fun <reified T> KeyValueStore.parsePrimitiveList(
    itemKeyPrefix: String,
    noinline itemFactory: (converter: StringValueConverter, value: String) -> T
): List<T> {
    return parsePrimitiveList(itemKeyPrefix, T::class.java, itemFactory)
}*/

/**
 * Exception, being thrown when a value for a specific key cannot be found in [KeyValueStore].
 */
class ValueNotFoundException(message: String?) : CordaRuntimeException(message)

/**
 * Exception, being thrown when a null value is stored for a specific key in [KeyValueStore].
 */
class ValueIsNullException(message: String?) : CordaRuntimeException(message)