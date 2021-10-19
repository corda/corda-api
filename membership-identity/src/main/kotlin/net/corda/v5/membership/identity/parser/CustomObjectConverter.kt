package net.corda.v5.membership.identity.parser

/**
 * Converter interface for more complex types (such as Party, EndpointInfo, etc).
 */
interface CustomObjectConverter<T> {
    /**
     * Type of the class the converter is for.
     */
    val type: Class<T>
    fun convert(context: ConversionContext): T?
}