package net.corda.v5.membership.identity.parser

/**
 * Converter interface for more complex types (such as Party, EndpointInfo, etc).
 */
interface CustomObjectConverter {
    /**
     * Type of the class the converter is for.
     */
    val type: Class<*>
    fun convert(context: CustomConversionContext): Any?
}