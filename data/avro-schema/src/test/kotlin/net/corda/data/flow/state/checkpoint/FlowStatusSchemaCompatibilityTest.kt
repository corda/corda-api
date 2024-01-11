package net.corda.data.flow.state.checkpoint

import net.corda.data.ExceptionEnvelope
import net.corda.data.flow.FlowInitiatorType
import net.corda.data.flow.FlowKey
import net.corda.data.flow.output.FlowStatus
import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FlowStatusSchemaCompatibilityTest {

    @Test
    fun `Flow status schema changes between Corda 5_1 and 5_2 are compatible`() {
        val oldSchemaJson = """
        {
          "type": "record",
          "name": "FlowStatus",
          "namespace": "net.corda.data.flow.output",
          "doc": "The Flow Status represents the current processing state of a flow at a given point in time",
          "fields": [
            {
              "name": "key",
              "type": "net.corda.data.flow.FlowKey",
              "doc": "The unique ID for the status"
            },
            {
              "name": "initiatorType",
              "type": "net.corda.data.flow.FlowInitiatorType",
              "doc": "The type of initiator that started the flow"
            },
            {
              "name": "flowId",
              "type": ["null", "string"],
              "doc": "The unique flow ID"
            },
            {
              "name": "flowClassName",
              "type": "string",
              "doc": "The fully qualified class name of the flow"
            },
            {
              "name": "flowStatus",
              "doc": "The current processing status of a flow" ,
              "type": {
                "name": "FlowStates",
                "type": "enum",
                "symbols": [
                  "START_REQUESTED",
                  "RUNNING",
                  "RETRYING",
                  "COMPLETED",
                  "FAILED",
                  "KILLED"
                ]
              }
            },
            {
              "name": "result",
              "type": ["null", "string"],
              "doc": "Optional result, this contains the result from the flow will only be set if the flow status is 'COMPLETED'"
            },
            {
              "name": "error",
              "type": ["null", "net.corda.data.ExceptionEnvelope"],
              "doc": "Optional error message, this will be set if the flow status is 'FAILED'"
            },
            {
              "name": "processingTerminatedReason",
              "type": ["null", "string"],
              "doc": "Optional message indicating reasoning why processing a flow has been terminated."
            },
            {
              "name": "createdTimestamp",
              "type": {
                "type": "long",
                "logicalType": "timestamp-millis"
              },
              "doc": "The date the flow was created."
            },
            {
              "name": "lastUpdateTimestamp",
              "type": {
                "type": "long",
                "logicalType": "timestamp-millis"
              },
              "doc": "The date and time this status update was published."
            }
          ]
        }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(
                mapOf(
                    FlowKey::class.java.name to FlowKey.`SCHEMA$`,
                    FlowInitiatorType::class.java.name to FlowInitiatorType.`SCHEMA$`,
                    ExceptionEnvelope::class.java.name to ExceptionEnvelope.`SCHEMA$`,
                )
            )
            .parse(oldSchemaJson)

        val newSchema = FlowStatus.`SCHEMA$`
        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}
