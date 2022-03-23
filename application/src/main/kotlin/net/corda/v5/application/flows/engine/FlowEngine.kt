package net.corda.v5.application.flows.engine

import net.corda.v5.application.flows.core.Flow
import net.corda.v5.application.flows.core.InitiatingFlow
import net.corda.v5.application.flows.error.FlowException
import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import java.time.Duration
import java.util.UUID

@DoNotImplement
interface FlowEngine : CordaFlowInjectable {
    /**
     * Returns a wrapped [UUID][java.util.UUID] object that identifies this flow or it's top level instance (i.e. subflows have the same
     * identifier as their parents).
     */
    val flowId: UUID

    /**
     * Invokes the given subflow. This function returns once the subflow completes successfully with the result
     * returned by that subflow's [Flow.call] method. If the subflow has a progress tracker, it is attached to the
     * current step in this flow's progress tracker.
     *
     * If the subflow is not an initiating flow (i.e. not annotated with [InitiatingFlow]) then it will continue to use
     * the existing sessions this flow has created with its counterparties. This allows for subflows which can act as
     * building blocks for other flows, for example removing the boilerplate of common sequences of sends and receives.
     *
     * @throws FlowException This is either thrown by [subLogic] itself or propagated from any of the remote
     * [Flow]s it communicated with. The subflow can be retried by catching this exception.
     */
    @Suspendable
    fun <R> subFlow(subLogic: Flow<R>): R

    /**
     * Suspends the flow and only wakes it up after at least [duration] time has passed.
     *
     * Warning: long sleeps and in general long running flows are highly discouraged, as there is currently no
     * support for flow migration!
     *
     * @throws FlowException if attempted to sleep for longer than 5 minutes.
     */
    /*
    JH: Do we want to offer this? We can make it work (particularly once we've implemented timed wakeup, this could be
    used to give fine grained control of that), I'm just not certain we should. I think the guarantee would be that
    we'd sleep for at least as long as the duration, but we may end up sleeping for quite a bit longer depending on load.

    Long sleeps would also break all sessions in the new implementation, so it might be dangerous to offer it. We could
    of course fix that by throwing if the duration is too long.
     */
    @Suspendable
    fun sleep(duration: Duration)

    /**
     * Suspends a flow until [FlowExternalOperation] is completed. Once the [FlowExternalOperation] gets completed the flow will resume and
     * return result [R].
     *
     * @param operation The [FlowExternalOperation] to be executed asynchronously.
     * @return The result [R] of [FlowExternalOperation.execute].
     */
    /*
    JH: This won't work as written I don't think. It's effectively a form of anonymous service, as we'd have to proxy the request and run it
    somewhere else. This needs some redesign to figure out how to do it. My proposal is to remove it entirely, but that is dependent on GA
    requirements.
     */
//    @Suspendable
//    fun <R : Any> await(operation: FlowExternalOperation<R>): R
}

