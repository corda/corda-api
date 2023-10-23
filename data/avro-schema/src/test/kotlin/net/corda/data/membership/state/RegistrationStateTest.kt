package net.corda.data.membership.state

import net.corda.data.identity.HoldingIdentity
import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegistrationStateTest {

    @Test
    fun `Changes in RegistrationState between Corda 5_0 and 5_1 are compatible`() {
        val oldSchemaJson = """
            {
              "type": "record",
              "name": "RegistrationState",
              "namespace": "net.corda.data.membership.state",
              "doc": "State for a registration.",
              "fields": [
                {
                  "name": "registrationId",
                  "doc": "UUID identifying this registration request",
                  "type": {
                    "type": "string",
                    "logicalType": "uuid"
                  }
                },
                {
                  "name": "registeringMember",
                  "doc": "Holding identity of the registering member as provided during P2P communication. Used to verify the registration request.",
                  "type": "net.corda.data.identity.HoldingIdentity"
                },
                {
                  "name": "mgm",
                  "doc": "Holding identity of the MGM.",
                  "type": "net.corda.data.identity.HoldingIdentity"
                }
              ]
            }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(mapOf(HoldingIdentity::class.java.name to HoldingIdentity.`SCHEMA$`))
            .parse(oldSchemaJson)
        val newSchema = RegistrationState.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }

}