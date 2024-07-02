package net.corda.data.p2p

import net.corda.data.identity.HoldingIdentity
import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HostedIdentityEntryCompatibilityTest {
    @Test
    fun `check HostedIdentityEntry schema changes between Corda 5_2 and 5_2_1 are compatible`() {
        val schemaV52Json = """
            {
              "type": "record",
              "name": "HostedIdentityEntry",
              "namespace": "net.corda.data.p2p",
              "fields": [
                {
                  "doc": "The Holding identity hosted in this node",
                  "name": "holdingIdentity",
                  "type": "net.corda.data.identity.HoldingIdentity"
                },
                {
                  "doc": "The tenant ID under which the TLS key is stored",
                  "name": "tlsTenantId",
                  "type": "string"
                },
                {
                  "doc": "The TLS certificates (in PEM format)",
                  "name": "tlsCertificates",
                  "type": {
                    "type": "array",
                    "items": "string"
                  }
                },
                {
                  "doc": "The preferred session initiation key and certificate",
                  "name": "preferredSessionKeyAndCert",
                  "type": "HostedIdentitySessionKeyAndCert"
                },
                {
                  "doc": "Alternative session initiation keys and certificates",
                  "name": "alternativeSessionKeysAndCerts",
                   "type": {
                     "type": "array",
                     "items": "HostedIdentitySessionKeyAndCert"
                   }
                 }
              ]
            }
        """.trimIndent()

        val schemaV52 = Schema.Parser().addTypes(
            mapOf(
                HoldingIdentity::class.java.name to HoldingIdentity.`SCHEMA$`,
                HostedIdentitySessionKeyAndCert::class.java.name to HostedIdentitySessionKeyAndCert.`SCHEMA$`
            )
        ).parse(schemaV52Json)
        val schemaV521 = HostedIdentityEntry.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(schemaV521, schemaV52)
        assertEquals(compatibility.type, SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE)
    }
}
