package net.corda.v5.application.marshalling

import net.corda.v5.base.annotations.DoNotImplement

/**
 * An optional service CorDapps and other services may use to marshall arbitrary content in and out of JSON format using standard/approved
 * mappers.
 */
@DoNotImplement
interface JsonMarshallingService : MarshallingService {

    /**
     * Deserializes the [input] JSON into a list of instances of [T].
     *
     * @param input The JSON to deserialize.
     * @param clazz The [Class] type to deserialize into.
     *
     * @return A new list of [T].
     */
    fun <T> parseList(input: String, clazz: Class<T>): List<T>
}

/**
 * Deserializes the [input] JSON into a list of instances of [T].
 *
 * @param input The JSON to deserialize.
 *
 * @return A new list of [T].
 */
inline fun <reified T> JsonMarshallingService.parseList(input: String): List<T> {
    return parseList(input, T::class.java)
}