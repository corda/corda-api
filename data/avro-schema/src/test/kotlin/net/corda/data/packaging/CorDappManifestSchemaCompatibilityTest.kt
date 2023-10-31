package net.corda.data.packaging

import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CorDappManifestSchemaCompatibilityTest {

    @Test
    fun `CorDappManifest schema changes between Corda 5_0 and 5_1 are compatible`() {
        val oldSchemaJson = """
        {
          "type": "record",
          "namespace": "net.corda.data.packaging",
          "name": "CorDappManifest",
          "fields": [
            {
              "name": "bundleSymbolicName",
              "type" : "string"
            },
            {
              "name": "bundleVersion",
              "type" : "string"
            },
            {
              "name": "minPlatformVersion",
              "type" : "int"
            },
            {
              "name": "targetPlatformVersion",
              "type" : "int"
            },
            {
              "name": "type",
              "type" : "net.corda.data.packaging.CorDappType"
            },
            {
              "name": "shortName",
              "type": ["null", "string"]
            },
            {
              "name": "vendor",
              "type": ["null", "string"]
            },
            {
              "name": "versionId",
              "type": ["null", "int"]
            },
            {
              "name": "license",
              "type": ["null", "string"]
            },
            {
              "name": "attributes",
              "type" : { "type":  "map", "values" :  "string", "default": {}}
            }
          ]
        }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(
                mapOf(
                    CorDappType::class.java.name to CorDappType.`SCHEMA$`
                )
            )
            .parse(oldSchemaJson)

        val newSchema = CorDappManifest.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}
