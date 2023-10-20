package net.corda.data.membership.command.registration.mgm

import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DeclineRegistrationCompatibilityTest {

    @Test
    fun `Changes in DeclineRegistration command between Corda 5_0 and 5_1 are compatible`() {
        val oldSchemaJson = """
            {
              "type": "record",
              "name": "DeclineRegistration",
              "namespace": "net.corda.data.membership.command.registration.mgm",
              "doc": "Command issued when a member registration has been declined and needs to be updated to declined status.",
              "fields": [
                {
                  "name": "reason",
                  "doc": "Reason that the request was declined. This contains data that will remain internal in the MGM's system for record keeping.",
                  "type": "string"
                }
              ]
            }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .parse(oldSchemaJson)

        val newSchema = DeclineRegistration.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }

}