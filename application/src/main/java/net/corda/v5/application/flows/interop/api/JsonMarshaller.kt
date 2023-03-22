package net.corda.v5.application.flows.interop.api

/**
 * Interface through which JSON serialisation and deserialisation services are provided to Facade client/server proxies.
 */
interface JsonMarshaller {

    fun serialize(value: Any): String
    fun <T : Any> deserialize(value: String, type: Class<T>): T

}