package net.corda.data.flow.state.checkpoint

import net.corda.data.KeyValuePairList
import org.apache.avro.Schema
import org.apache.avro.SchemaCompatibility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CheckpointSchemaCompatibilityTest {

    @Test
    fun `Flow checkpoint schema changes between Corda 5_0 and 5_1 are compatible`() {
        val oldSchemaJson = """
        {
          "type": "record",
          "name": "Checkpoint",
          "namespace": "net.corda.data.flow.state.checkpoint",
          "doc": "Represents the current state of a flow, plus information required to operate the flow engine.",
          "fields": [
            {
              "name": "flowId",
              "type": "string",
              "doc": "Internal, globally unique key for a flow instance."
            },
            {
              "name": "initialPlatformVersion",
              "type": "int",
              "doc": "The platform version at the time the flow was started."
            },
            {
              "name": "pipelineState",
              "type": "net.corda.data.flow.state.checkpoint.PipelineState",
              "doc": "State required by the pipeline, e.g. to support retries."
            },
            {
              "name": "flowState",
              "type": [
                "null",
                "net.corda.data.flow.state.checkpoint.FlowState"
              ],
              "doc": "Current flow execution state. Null if the flow has not yet been started, for example in the face of a retry-able error."
            },
            {
              "name": "flowMetricsState",
              "type": "string",
              "default": "{}",
              "doc": "Internal storage for recording flow metrics"
            }
          ]
        }
        """.trimIndent()

        val oldSchema = Schema.Parser()
            .addTypes(
                mapOf(
                    PipelineState::class.java.name to PipelineState.`SCHEMA$`,
                    FlowState::class.java.name to FlowState.`SCHEMA$`,
                    KeyValuePairList::class.java.name to KeyValuePairList.`SCHEMA$`
                )
            )
            .parse(oldSchemaJson)

        val newSchema = Checkpoint.`SCHEMA$`

        val compatibility = SchemaCompatibility.checkReaderWriterCompatibility(newSchema, oldSchema)

        Assertions.assertEquals(
            compatibility.type,
            SchemaCompatibility.SchemaCompatibilityType.COMPATIBLE,
            "Failed due to incompatible change. ${compatibility.description}"
        )
    }
}
