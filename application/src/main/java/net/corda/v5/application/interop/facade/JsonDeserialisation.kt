package net.corda.v5.application.interop.facade

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import net.corda.v5.application.interop.parameters.KotlinParameterType
import net.corda.v5.application.interop.parameters.TypedParameter
import net.corda.v5.application.interop.parameters.TypedParameterValue
import java.io.StringWriter
import java.math.BigDecimal
import java.nio.ByteBuffer
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class FacadeRequestDeserializer : JsonDeserializer<FacadeRequest>() {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): FacadeRequest =
        deserialize(parser, ::FacadeRequest)

}

class FacadeResponseDeserializer : JsonDeserializer<FacadeResponse>() {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): FacadeResponse =
        deserialize(parser, ::FacadeResponse)

}

@SuppressWarnings("ThrowsCount")
private fun <T> deserialize(
    parser: JsonParser,
    ctor: (FacadeId, String, List<TypedParameterValue<*>>) -> T
): T {
    val node = parser.codec.readTree<JsonNode>(parser)
    val method = node["method"]?.asText() ?: throw IllegalArgumentException(
        "No 'method' field in request/response ${node.toPrettyString()}"
    )

    val facadeId = try {
        FacadeId.of(method.substringBeforeLast("/"))
    } catch (_: IllegalArgumentException) {
        throw IllegalArgumentException(
            "Invalid method id '$method' in ${node.toPrettyString()}"
        )
    }
    val methodName = method.substringAfterLast("/")

    val parameters = node["parameters"]?.fields()?.asSequence() ?: emptySequence()
    val parameterValues = parameters.map { (name, parameterValue) ->
        val typeName = parameterValue["type"]?.asText() ?: throw IllegalArgumentException(
            "No parameter type given for parameter $name in ${node.toPrettyString()}"
        )
        val type = KotlinParameterType.of<Any>(typeName)
        val value = parameterValue["value"] ?: throw IllegalArgumentException(
            "No parameter value given for parameter $name in ${node.toPrettyString()}"
        )
        val paramValue = type.readValue(name, value, parser)
        TypedParameterValue(TypedParameter(name, type), paramValue)
    }.toList()

    return ctor(facadeId, methodName, parameterValues)
}

fun KotlinParameterType<*>.readValue(name: String, node: JsonNode, parser: JsonParser): Any = when (this) {
    is KotlinParameterType.BooleanType -> if (node.isBoolean) node.asBoolean() else throw IllegalArgumentException(
        "Parameter $name expected to have a boolean value, but was ${node.toPrettyString()}"
    )
    is KotlinParameterType.StringType -> node.asText()
    is KotlinParameterType.DecimalType -> try {
        BigDecimal(node.asText())
    } catch (_: NumberFormatException) {
        throw IllegalArgumentException(
            "Parameter $name expected to have a decimal value, but was ${node.toPrettyString()}"
        )
    }
    is KotlinParameterType.UUIDType -> try {
        UUID.fromString(node.asText())
    } catch (_ : IllegalArgumentException) {
        throw IllegalArgumentException("Parameter $name expected to have a UUID value, but was ${node.toPrettyString()}")
    }
    is KotlinParameterType.TimestampType -> try {
        ZonedDateTime.parse(node.asText(), DateTimeFormatter.ISO_DATE_TIME)
    } catch (_: DateTimeParseException) {
        throw IllegalArgumentException("Parameter $name expected to have a timestamp value in ISO_DATE_TIME format " +
                "but was ${node.toPrettyString()}")
    }
    is KotlinParameterType.ByteBufferType -> ByteBuffer.wrap(
        try {
            Base64.getDecoder().decode(node.asText())
        } catch (_: IllegalArgumentException) {
            throw IllegalArgumentException("Parameter $name expected to have a Base64-encoded byte blob value, but was " +
                    node.toPrettyString()
            )
        }
    )

    is KotlinParameterType.JsonType -> {
        val writer = StringWriter()
        parser.codec.factory.createGenerator(writer).writeTree(node)
        writer.flush()
        writer.toString()
    }

    is KotlinParameterType.QualifiedType -> type.readValue(name, node, parser)
}