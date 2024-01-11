package net.corda.data.flow.state.checkpoint

import checkpoint.RetryState
import net.corda.data.ExceptionEnvelope
import net.corda.data.crypto.SecureHash
import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PipelineStateSchemaCompatibilityTest {

    @Test
    fun `Pipeline state schema changes between Corda 5_1 and 5_2 are compatible`() {
        val oldSchemaJson = """
        {
          "type": "record",
          "name": "PipelineState",
          "namespace": "net.corda.data.flow.state.checkpoint",
          "doc": "State used by the flow engine to track pipeline details and provide diagnostics.",
          "fields": [
            {
              "name": "retryState",
              "type": ["null", "net.corda.data.flow.state.checkpoint.RetryState"],
              "default": null,
              "doc": "Optional retry information for a failed flow event. Setting this field marks the flow as retrying."
            },
            {
              "name": "maxFlowSleepDuration",
              "type": "int",
              "doc": "The maximum time a flow can sleep, before a Wakeup event is generated (milliseconds)"
            },
            {
              "name": "pendingPlatformError",
              "type": ["null", "net.corda.data.ExceptionEnvelope"],
              "default": null,
              "doc": "Used for platform generated errors reported back to user code."
            },
            {
              "name": "cpkFileHashes",
              "type": {
                "type": "array",
                "items": "net.corda.data.crypto.SecureHash"
              },
              "doc": "Array of stored cpkFileHashes from the Virtual Node."
            }
          ]
        }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(
                mapOf(
                    SecureHash::class.java.name to SecureHash.`SCHEMA$`,
                    RetryState::class.java.name to RetryState.`SCHEMA$`,
                    ExceptionEnvelope::class.java.name to ExceptionEnvelope.`SCHEMA$`,
                )
            )
            .parse(oldSchemaJson)

        val newSchema = PipelineState.`SCHEMA$`
        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}
