package net.corda.v5.application.flows.exception

import net.corda.v5.base.exceptions.CordaRuntimeException

/**
 * Exception which can be thrown by a [Flow] at any point in its logic to unexpectedly bring it to a permanent end. The exception will
 * propagate to all counterparty flows and will be thrown on their end the next time they wait on a [FlowSession.receive] or
 * [FlowSession.sendAndReceive]. Any flow which no longer needs to do a receive, or has already ended, will not receive the exception (if
 * this is required then have them wait for a confirmation message).
 *
 * If the *rethrown* [FlowException] is uncaught in counterparty flows and propagation triggers then the exception is downgraded to an
 * [UnexpectedFlowEndException]. This means only immediate counterparty flows will receive information about what the exception was.
 *
 * [FlowException] (or a subclass) can be a valid expected response from a flow, particularly ones which act as a service. It is recommended
 * a [Flow] document the [FlowException] types it can throw.
 *
 * @property originalErrorId the ID backing [errorId]. If null it will be set dynamically by the flow framework when the exception is
 * handled. This ID is propagated to counterparty flows, even when the [FlowException] is downgraded to an [UnexpectedFlowEndException].
 * This is so the error conditions may be correlated later on.
 */
open class FlowException(message: String?, cause: Throwable?) :
    CordaRuntimeException(message, cause) {
    constructor(message: String?) : this(message, null)
    constructor(cause: Throwable?) : this(cause?.toString(), cause)
    constructor() : this(null, null)
}
/**
 * Thrown when a flow session ends unexpectedly due to a type mismatch (the other side sent an object of a type that we were not expecting),
 * or the other side had an internal error, or the other side terminated when we were waiting for a response.
 */
class UnexpectedFlowEndException(message: String, cause: Throwable?) :
    CordaRuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}