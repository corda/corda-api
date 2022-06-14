package net.corda.v5.application.flows

import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.exceptions.CordaRuntimeException

/**
 * A flow that should be started by another flow.
 *
 * When a flow implementing [Subflow] is called via the subflow API (see [FlowEngine]), the [call] method of this
 * interface will be invoked.
 */
interface Subflow<out T> : Flow {

    /**
     * This is where you fill out your business logic.
     *
     * @throws FlowException It can be thrown at any point of a [Flow] logic to bring it to a permanent end. The exception will be
     * propagated to all counterparty flows.
     * @throws CordaRuntimeException General type of exception thrown by most Corda APIs.
     * @throws UnexpectedFlowEndException Thrown when a flow session ends unexpectedly.
     */
    @Suspendable
    fun call(): T
}