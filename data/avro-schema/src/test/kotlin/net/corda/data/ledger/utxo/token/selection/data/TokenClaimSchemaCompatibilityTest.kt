package net.corda.data.ledger.utxo.token.selection.data

import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TokenClaimSchemaCompatibilityTest {

    @Test
    fun `Token Claim schema changes between Corda 5_0 and 5_1 are compatible`() {
        val oldSchemaJson = """
        {
  "type": "record",
  "name": "TokenClaim",
  "namespace": "net.corda.data.ledger.utxo.token.selection.data",
  "doc": "The set of tokens claimed by a query",
  "fields": [
      {
        "name": "claimId",
        "type": "string",
        "doc": "Unique identifier for the claim"
      },
      {
        "name": "claimedTokenStateRefs",
        "type": {
          "type": "array",
          "items": "string"
        },
        "default": [],
        "doc": "Deprecated. The List of state refs for the claimed tokens"
      },
      {
        "name": "claimedTokens",
        "type": {
          "type": "array",
          "items": "net.corda.data.ledger.utxo.token.selection.data.Token"
        },
        "default": [],
        "doc": "List of claimed tokens"
      }
  ]
}
""".trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(
                mapOf(
                    Token::class.java.name to Token.`SCHEMA$`,
                )
            )
            .parse(oldSchemaJson)

        val newSchema = TokenClaim.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}