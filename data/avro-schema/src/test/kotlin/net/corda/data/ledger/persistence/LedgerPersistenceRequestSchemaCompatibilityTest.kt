package net.corda.data.ledger.persistence

import net.corda.data.flow.event.external.ExternalEventContext
import net.corda.data.identity.HoldingIdentity
import net.corda.data.persistence.FindWithNamedQuery
import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class LedgerPersistenceRequestSchemaCompatibilityTest {

    @Test
    fun `LedgerPersistenceRequest schema changes between Corda 5_0 and 5_1 are compatible`() {
        val oldSchemaJson = """
        {
          "type": "record",
          "name": "LedgerPersistenceRequest",
          "doc": "Make a persistence request on a ledger. The action is determined by the type of the {@link LedgerPersistenceRequest#request} payload.  See also: {@link EntityResponse}, {@link PersistTransaction}",
          "namespace": "net.corda.data.ledger.persistence",
          "fields": [
            {
              "name": "timestamp",
              "type": {
                "type": "long",
                "logicalType": "timestamp-millis"
              },
              "doc": "Time ({@link java.time.Instant}) in milliseconds when the record was created."
            },
            {
              "name": "holdingIdentity",
              "type": "net.corda.data.identity.HoldingIdentity",
              "doc": "The holding identity of the user making the DB request."
            },
            {
              "name": "ledgerType",
              "type": {
                "name": "LedgerTypes",
                "type": "enum",
                "symbols": ["UTXO","CONSENSUAL"]
              },
              "doc": "The type of ledger the request is for."
            },
            {
              "name": "request",
              "doc": "The 'request' that we wish to make to the ledger persistence API.",
              "type": [
                "net.corda.data.ledger.persistence.PersistTransaction",
                "net.corda.data.ledger.persistence.PersistTransactionIfDoesNotExist",
                "net.corda.data.ledger.persistence.FindTransaction",
                "net.corda.data.ledger.persistence.FindUnconsumedStatesByType",
                "net.corda.data.ledger.persistence.FindUnconsumedStatesByExactType",
                "net.corda.data.ledger.persistence.ResolveStateRefs",
                "net.corda.data.ledger.persistence.UpdateTransactionStatus",
                "net.corda.data.persistence.FindWithNamedQuery",
                "net.corda.data.ledger.persistence.FindSignedGroupParameters",
                "net.corda.data.ledger.persistence.PersistSignedGroupParametersIfDoNotExist"
              ]
            },
            {
              "name": "flowExternalEventContext",
              "type": "net.corda.data.flow.event.external.ExternalEventContext",
              "doc": "The context of the external event that this request was sent from."
            }
          ]
        }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(
                mapOf(
                    HoldingIdentity::class.java.name to HoldingIdentity.`SCHEMA$`,
                    PersistTransaction::class.java.name to PersistTransaction.`SCHEMA$`,
                    PersistTransactionIfDoesNotExist::class.java.name to PersistTransactionIfDoesNotExist.`SCHEMA$`,
                    FindTransaction::class.java.name to FindTransaction.`SCHEMA$`,
                    FindUnconsumedStatesByType::class.java.name to FindUnconsumedStatesByType.`SCHEMA$`,
                    FindUnconsumedStatesByExactType::class.java.name to FindUnconsumedStatesByExactType.`SCHEMA$`,
                    ResolveStateRefs::class.java.name to ResolveStateRefs.`SCHEMA$`,
                    UpdateTransactionStatus::class.java.name to UpdateTransactionStatus.`SCHEMA$`,
                    FindWithNamedQuery::class.java.name to FindWithNamedQuery.`SCHEMA$`,
                    FindSignedGroupParameters::class.java.name to FindSignedGroupParameters.`SCHEMA$`,
                    PersistSignedGroupParametersIfDoNotExist::class.java.name to PersistSignedGroupParametersIfDoNotExist.`SCHEMA$`,
                    FindSignedLedgerTransaction::class.java.name to FindSignedLedgerTransaction.`SCHEMA$`,
                    ExternalEventContext::class.java.name to ExternalEventContext.`SCHEMA$`,
                )
            )
            .parse(oldSchemaJson)

        val newSchema = LedgerPersistenceRequest.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}