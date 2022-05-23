package net.corda.schema

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.File
import kotlin.reflect.full.companionObject

class SchemaTests {
    // Setup jackson mapper to support yaml with null values
    private val mapper = ObjectMapper(YAMLFactory()).registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, true)
            .configure(KotlinFeature.NullToEmptyMap, true)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )

    @Suppress("UNCHECKED_CAST")
    private val yamlFileData by lazy {
        // Scan resources in classpath to find all the yaml files to scan
        this::class.java.classLoader.getResources("net/corda/schema")
            .toList()
            .filterNotNull()
            .map { File(it.file) }
            .filter { it.isDirectory }
            .flatMap { it.listFiles()!!.toList() }
            .filter { it.name.endsWith("yaml") || it.endsWith("yml") }
            .map { mapper.readValue(it.readText()) as Map<String, *> }
            as List<Map<String, Map<String, Map<String, *>>>>
    }

    // Scan companion objects for constant definitions
    private val memberMap by lazy {
        val schemaCompanions = Schemas::class.nestedClasses.mapNotNull {
            it.companionObject
        }
        val members = schemaCompanions.flatMap {
            it.members
        }.filter { it.isFinal }
        val memberValueMap = members.map {
            it.name to it.call(emptyList<Any>()) as String
        }

        memberValueMap.toMap()
    }

    @Test
    fun `Validate that all yaml topics have matching code value`() {
        @Suppress("UNCHECKED_CAST")
        val yamlTopics = yamlFileData.flatMap { it.values.flatMap { it.values } }.map { it["name"] } as List<String>
        val codeTopics = memberMap.values.toList()
        assertThat(yamlTopics).containsExactlyInAnyOrderElementsOf(codeTopics)
        assertThat(codeTopics).containsExactlyInAnyOrderElementsOf(yamlTopics)
    }
}
