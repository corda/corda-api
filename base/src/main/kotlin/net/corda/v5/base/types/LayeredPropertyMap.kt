package net.corda.v5.base.types

/**
 * Interface for supporting Map<String, String> structure.
 * Has the required functions for converting and parsing the String values to Objects.
 */
interface LayeredPropertyMap {

    /**
     * Returns the value of the given key or null if the key doesn't exist.
     */
    operator fun get(key: String): String?

    /**
     * Returns [Set] of all entries in the underlying map.
     */
    val entries: Set<Map.Entry<String, String?>>

    /**
     * Converts the value of the given key to the specified type.
     * @throws [ValueNotFoundException] if the key is not found.
     * @throws [IllegalStateException]  if the value for the key is null.
     * @throws [ClassCastException] as the result of the conversion is cached, it'll be thrown if the second time around
     * the [T] is different from it was called for the first time.
     */
    fun <T> parse(key: String, clazz: Class<out T>): T

    /**
     * Converts the value of the given key to the specified type or returns null if the key is not found or the value
     * itself is null.
     * @throws [ClassCastException] as the result of the conversion is cached, it'll be thrown if the second time around
     * the [T] is different from it was called for the first time.
     * */
    fun <T> parseOrNull(key: String, clazz: Class<out T>): T?

    /**
     * Converts several items with the given prefix to the list.
     *
     * Here is an example how a list will look like
     * (the [itemKeyPrefix] have to be "corda.endpoints" or "corda.endpoints."):
     *  corda.endpoints.1.url = localhost
     *  corda.endpoints.1.protocolVersion = 1
     *  corda.endpoints.2.url = localhost
     *  corda.endpoints.2.protocolVersion = 1
     *  corda.endpoints.3.url = localhost
     *  corda.endpoints.3.protocolVersion = 1
     */
    fun <T> parseList(itemKeyPrefix: String, clazz: Class<out T>): List<T>
}


