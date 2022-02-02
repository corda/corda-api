package net.corda.schema.configuration.validator

import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SchemaValidatorsConfig
import com.networknt.schema.SpecVersion
import com.networknt.schema.uri.URIFactory
import com.networknt.schema.urn.URNFactory
import com.worldturner.medeia.api.*
import com.worldturner.medeia.api.jackson.MedeiaJacksonApi
import com.worldturner.medeia.schema.validation.SchemaValidator
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.json.pointer.JsonPointer
import io.vertx.json.schema.SchemaParser
import io.vertx.json.schema.SchemaRouter
import io.vertx.json.schema.SchemaRouterOptions
import org.everit.json.schema.loader.SchemaClient
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import org.json.JSONTokener
import org.osgi.framework.FrameworkUtil
import java.io.InputStream
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

class SchemaValidator {

    companion object {
        private val schemas = listOf(
            "net/corda/schema/configuration/messaging/corda.messaging.json",
            "net/corda/schema/configuration/messaging/kafka-properties.json",
            "net/corda/schema/configuration/messaging/publisher.json",
            "net/corda/schema/configuration/messaging/subscription.json"
        )

        private const val URI_FRAGMENT = "https://corda.r3.com/"
    }

    val medeiaAPI = MedeiaJacksonApi()

    fun validateJsonMedeia(json: String) {
        val validator = loadSchema(schemas)
        val parser = medeiaAPI.createJsonParser(validator, StringInputSource(json))
        medeiaAPI.parseAll(parser)
    }

    fun validateJsonEverit(json: String) {
        val jsonSchema = resourceInputStream("net/corda/schema/configuration/messaging/corda.messaging.json").use {
            JSONObject(JSONTokener(it))
        }
        val schema = SchemaLoader.builder()
            .schemaClient(SchemaClient.classPathAwareClient())
            .schemaJson(jsonSchema)
            .resolutionScope("classpath://net/corda/schema/configuration/messaging")
            .build().load().build()
        schema.validate(JSONObject(json))
    }

    fun validateJsonNetworkNt(json: String) {
        val builder = JsonSchemaFactory.builder(JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7))
        builder.uriFactory(object : URIFactory {
            override fun create(uri: String?): URI {
                if (uri == null) throw IllegalArgumentException()
                val newUri = if (uri.startsWith(URI_FRAGMENT)) {
                    val subUri = uri.removePrefix(URI_FRAGMENT)
                    "resource:/$subUri"
                } else {
                    uri
                }
                return URI.create(newUri)
            }

            override fun create(baseURI: URI?, segment: String?): URI {
                return baseURI?.resolve(segment!!) ?: throw IllegalArgumentException()
            }
        }, "https", "http")
        val schemaFactory = builder.build()
        val schema = schemaFactory.getSchema(resourceInputStream("net/corda/schema/configuration/messaging/corda.messaging.json"))
        val mapper = ObjectMapper()
        val jsonNode = mapper.readTree(json)
        val errors = schema.validate(jsonNode)
        if (errors.isNotEmpty()) {
            throw RuntimeException("Found errors: $errors")
        }
    }

    fun validateJsonVertx(json: String) {
        val schemaRouter = SchemaRouter.create(Vertx.vertx(), SchemaRouterOptions())
        val schemaParser = SchemaParser.createDraft7SchemaParser(schemaRouter)
        val schemaString = resourceInputStream("net/corda/schema/configuration/messaging/corda.messaging.json").bufferedReader().use {
            it.readText()
        }
        val pointer = JsonPointer.fromURI(
            URI("resource:/net/corda/schema/configuration/messaging/"))
        val schema = schemaParser.parseFromString(schemaString, pointer)
        val jsonNode = JsonObject(json)
        val fut = CompletableFuture<Boolean>()
        schema.validateAsync(jsonNode).onComplete {
            if (it.succeeded()) {
                fut.complete(true)
            } else {
                fut.completeExceptionally(it.cause())
            }
        }
        fut.get()
    }

    private fun resourceInputStream(resource: String): InputStream {
        val bundle = FrameworkUtil.getBundle(this::class.java)
        val url = bundle?.getResource(resource) ?: this::class.java.classLoader.getResource(resource) ?: throw RuntimeException()
        return url.openStream()
    }

    private fun loadSchema(resources: List<String>): SchemaValidator {
        val sources = resources.map {
            StreamSchemaSource(resourceInputStream(it))

        }
        return medeiaAPI.loadSchemas(sources, ValidationOptions.DEFAULT)
    }
}