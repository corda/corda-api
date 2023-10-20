package net.corda.data.virtualnode

import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VirtualNodeUpgradeRequestSchemaCompatibilityTest {

    @Test
    fun `VirtualNodeUpgradeRequest schema changes between Corda 5_0 and 5_1 are compatible`() {
        val schemaV50Json = """
        {
          "type": "record",
          "name": "VirtualNodeUpgradeRequest",
          "namespace": "net.corda.data.virtualnode",
          "fields": [
            {
              "name": "virtualNodeShortHash",
              "type": "string",
              "doc": "Short hash of the virtual node / holding identity."
            },
            {
              "name": "cpiFileChecksum",
              "type": "string",
              "doc": "The checksum of the CPI file."
            },
            {
              "name": "actor",
              "type": "string",
              "doc": "ID of RPC user that requested the virtual node creation."
            }
          ]
        }
        """.trimIndent()

        val schemaV50 = Schema.Parser().parse(schemaV50Json)
        val schemaV51 = VirtualNodeUpgradeRequest.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(schemaV51, schemaV50)
        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}