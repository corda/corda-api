package net.corda.v5.application.flows

import java.util.UUID
import net.corda.v5.application.messaging.FlowSession
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.types.MemberX500Name

/**
 * [FlowEngine] provides core flow related functionality.
 *
 * This service can be injected using [CordaInject].
 */
@DoNotImplement
interface FlowEngine {

    /**
     * Gets the flow id that identifies this flow.
     *
     * A subFlow shares the same flow id as the flow that invoked it via [FlowEngine.subFlow].
     */
    val flowId: UUID

    /**
     * Gets the [MemberX500Name] of the current virtual node executing the flow.
     */
    val virtualNodeName: MemberX500Name

    /**
     * Gets the context properties of the current flow.
     */
    val flowContextProperties: FlowContextProperties

    /**
     * TODO
     *
     * Executes the given [SubFlow].
     *
     * This function returns once the [SubFlow] completes, returning either:
     *
     * - The result executing of [SubFlow.call].
     * - An exception thrown by [SubFlow.call].
     *
     * Any open [FlowSession]s created within an initiating [SubFlow] are sent:
     *
     * - Session close messages after successfully completing the [SubFlow].
     * - Session error messages when an exception is thrown from the [SubFlow].
     *
     *
     * If the subflow is not an initiating flow (i.e. not annotated with [InitiatingFlow]) then it will continue to use
     * the existing sessions this flow has created with its counterparties. This allows for subflows which can act as
     * building blocks for other flows, for example removing the boilerplate of common sequences of sends and receives.
     *
     * @param subFlow The [SubFlow] to execute.
     */
    @Suspendable
    fun <R> subFlow(subFlow: SubFlow<R>): R
}

