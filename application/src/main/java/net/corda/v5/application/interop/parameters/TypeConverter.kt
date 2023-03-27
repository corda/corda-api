package net.corda.v5.application.interop.parameters

import net.corda.v5.application.flows.interop.api.JsonMarshaller
import java.math.BigDecimal
import java.nio.ByteBuffer

class TypeConverter(private val jsonMarshaller: JsonMarshaller) {

    /**
     * Convert a facade parameter value (in or out) to the JVM type declared for a JVM interface method parameter.
     *
     * @param parameterType The [KotlinParameterType] to convert from
     * @param parameterValue The value to convert
     * @param jvmType The target JVM type
     */
    fun convertFacadeToJvm(
        parameterType: KotlinParameterType<*>,
        parameterValue: Any,
        jvmType: Class<*>): Any = when(parameterType) {
        KotlinParameterType.DecimalType -> {
            parameterValue as? BigDecimal ?: throw IllegalArgumentException(
                    "Parameter value $parameterValue expected to be BigDecimal")
            when (jvmType) {
                Int::class.javaObjectType -> parameterValue.toInt()
                Int::class.javaPrimitiveType -> parameterValue.toInt()
                Long::class.javaObjectType -> parameterValue.toLong()
                Long::class.javaPrimitiveType -> parameterValue.toLong()
                Double::class.javaObjectType -> parameterValue.toDouble()
                Double::class.javaPrimitiveType -> parameterValue.toDouble()
                else -> parameterValue
            }
        }
        KotlinParameterType.ByteBufferType -> {
            parameterValue as? ByteBuffer ?: throw IllegalArgumentException(
                "Parameter value $parameterValue expected to be ByteBuffer")
            when (jvmType) {
                ByteArray::class.java -> parameterValue.array()
                else -> parameterValue
            }
        }
        KotlinParameterType.JsonType -> jsonMarshaller.deserialize(parameterValue as String, jvmType)
        else -> parameterValue
    }

    /*
    We are making the significant assumption here that the binding will only have succeeded if the parameter types agree
    so we don't need to check them, we just need to perform conversions in a few cases:

    * Int, Long and Double get automatically converted to BigDecimal.
    * ByteArrays get automatically wrapped as ByteBuffers.
    * Anything at all gets serialised to a JSON blob.
     */
    fun convertJvmToFacade(value: Any, expectedType: KotlinParameterType<*>): Any = when(expectedType) {
        KotlinParameterType.DecimalType -> when(value) {
            is Int -> BigDecimal(value)
            is Long -> BigDecimal(value)
            is Double -> BigDecimal(value)
            else -> value
        }
        KotlinParameterType.ByteBufferType -> when(value) {
            is ByteArray -> ByteBuffer.wrap(value)
            else -> value
        }
        KotlinParameterType.JsonType -> jsonMarshaller.serialize(value)
        else -> value
    }
}