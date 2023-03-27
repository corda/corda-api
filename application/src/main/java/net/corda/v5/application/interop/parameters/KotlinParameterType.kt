package net.corda.v5.application.interop.parameters

import net.corda.v5.application.flows.interop.api.HierarchicalName
import net.corda.v5.application.interop.parameters.KotlinParameterType.QualifiedType
import java.lang.reflect.ParameterizedType
import java.math.BigDecimal
import java.nio.ByteBuffer
import java.time.ZonedDateTime
import java.util.*

/**
 * A [KotlinParameterType] is the type of a [TypedParameter]. It is always one of a small set of primitive types, or
 * a [QualifiedType] qualifying a primitive type with a [FacadeTypeQualifier] which identifies a more complex type.
 */
sealed class KotlinParameterType<T> {

    companion object {

        /**
         * This pattern matches (after a whitespace prefix of any length) either a single non-whitespace string, e.g.
         * "uuid", or a pair of non-whitespace strings separated by at least one character of whitespace, with the
         * second string surrounded by parentheses.
         *
         * For example: "denomination  (org.corda.interop/finance/tokens/denomination/v1.0)".
         */
        private val facadeTypeRegex = Regex("""\s*(\S+)(\s+\((\S+)\))?.*""")

        /**
         * Parse a [KotlinParameterType] from a string in the format "type" or "type (qualifier)".
         *
         * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json".
         *
         * @param typeString The string to parse.
         */
        @JvmStatic
        fun <T : Any> of(typeString: String): KotlinParameterType<T> = of(typeString, emptyMap())

        /**
         * Parse a [KotlinParameterType] from a string in the format "type" or "type (qualifier)".
         *
         * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json", or aliases
         * defined in the supplied [Map].
         *
         * @param typeString The string to parse.
         * @param aliases A map of type aliases.
         */
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T : Any> of(typeString: String, aliases: Map<String, KotlinParameterType<*>>): KotlinParameterType<T> {
            val typeMatch = facadeTypeRegex.matchEntire(typeString)
                ?: throw IllegalArgumentException("Invalid parameter type: $typeString")

            val rawTypeName = typeMatch.groups[1]!!.value
            val qualifierString = typeMatch.groups[3]?.value
            val aliased = aliases[rawTypeName]
            if (aliased != null) {
                if (qualifierString != null) {
                    throw IllegalArgumentException("Alias $rawTypeName cannot be qualified with $qualifierString")
                }
                return aliased as KotlinParameterType<T>
            }

            val rawType = parseRawParameterType<T>(rawTypeName)
            return if (qualifierString == null) rawType
            else QualifiedType(rawType, FacadeTypeQualifier.of(qualifierString))
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T : Any> parseRawParameterType(typeName: String): KotlinParameterType<T> =
            when (typeName) {
                "boolean" -> BooleanType
                "string" -> StringType
                "decimal" -> DecimalType
                "uuid" -> UUIDType
                "timestamp" -> TimestampType
                "bytes" -> ByteBufferType
                "json" -> JsonType

                else -> throw IllegalArgumentException(
                    "Invalid raw parameter type: $typeName - " +
                            "must be one of boolean, string, decimal, uuid, timestamp, bytes or json"
                )
            } as KotlinParameterType<T>
    }

    /**
     * The expected type of a [TypedParameterValue] for this [KotlinParameterType].
     */
    @Suppress("UNCHECKED_CAST")
    open val expectedType: Class<T>
        get() {
            val superclass = this::class.java.genericSuperclass as ParameterizedType
            return superclass.actualTypeArguments[0] as Class<T>
        }

    object BooleanType : KotlinParameterType<Boolean>() {
        override fun toString() = "boolean"
    }

    object StringType : KotlinParameterType<String>() {
        override fun toString() = "string"
    }

    object DecimalType : KotlinParameterType<BigDecimal>() {
        override fun toString() = "decimal"
    }

    object UUIDType : KotlinParameterType<UUID>() {
        override fun toString() = "uuid"
    }

    object TimestampType : KotlinParameterType<ZonedDateTime>() {
        override fun toString() = "timestamp"
    }

    object ByteBufferType : KotlinParameterType<ByteBuffer>() {
        override fun toString() = "bytes"
    }

    object JsonType : KotlinParameterType<String>() {
        override fun toString() = "json"
    }

    data class QualifiedType<T>(
        val type: KotlinParameterType<T>,
        val qualifier: FacadeTypeQualifier
    ) : KotlinParameterType<T>() {
        override val expectedType: Class<T> get() = type.expectedType
        override fun toString() = "$type ($qualifier)"
    }
}