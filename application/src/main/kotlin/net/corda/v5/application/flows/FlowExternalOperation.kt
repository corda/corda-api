package net.corda.v5.application.flows

import net.corda.v5.application.flows.flowservices.FlowEngine

/**
 * [FlowExternalOperation] represents an external process that blocks a flow from continuing until the result of [execute]
 * has been retrieved. Examples of external processes where [FlowExternalOperation] would be useful include, triggering a long running
 * process on an external system or retrieving information from a service that might be down.
 *
 * The flow will suspend while it is blocked to free up a flow worker thread, which allows other flows to continue processing while waiting
 * for the result of this process.
 *
 * Implementations of [FlowExternalOperation] should ideally hold references to any external values required by [execute]. These references
 * should be passed into the implementation's constructor. For example, an amount or a reference to a Corda Service could be passed in.
 *
 * It is discouraged to insert into the node's database from a [FlowExternalOperation], except for keeping track of [deduplicationId]s that
 * have been processed. It is possible to interact with the database from inside a [FlowExternalOperation] but, for most operations, is not
 * currently supported.
 */
interface FlowExternalOperation<R> {

    /**
     * Executes a blocking operation.
     *
     * The execution of [execute] will be run on a thread from the node's external process thread pool when called by [FlowEngine.await].
     *
     * @param deduplicationId  If the flow restarts from a checkpoint (due to node restart, or via a visit to the flow
     * hospital following an error) the execute method might be called more than once by the Corda flow state machine.
     * For each duplicate call, the deduplicationId is guaranteed to be the same allowing duplicate requests to be
     * de-duplicated if necessary inside the execute method.
     */
    fun execute(deduplicationId: String): R
}