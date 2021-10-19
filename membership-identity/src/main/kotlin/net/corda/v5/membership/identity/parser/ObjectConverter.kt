package net.corda.v5.membership.identity.parser

/**
 * Converter class, converting from String to actual Objects.
 */
interface ObjectConverter {
    /**
     * Convert function, which does the parsing from String.
     *
     * @param context The context in which we can find the value.
     * @param clazz The class we want to convert to.
     */
    fun <T> convert(context: ConversionContext, clazz: Class<out T>): T?

    /**
     * Registering a [CustomObjectConverter], for use, to convert more complex types besides the built-in ones.
     *
     * @param converter The converter class we want to register.
     */
    fun registerConverter(converter: CustomObjectConverter)
}