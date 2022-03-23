package net.corda.v5.application.flows.error

import net.corda.v5.base.exceptions.CordaRuntimeException

/*
JH: What I'd like to see in this file are exception types that user code can throw to signal to the platfom they'd like
specific behaviour. Useful things would be:
- Graceful termination, i.e. it's failed and we'll try and unwind as best we can. I think this should be the default for
random exceptions that come out of user code anyway, but there's no harm in also having an explicit signal (we might be
able to provide better logging or something)
- Timed retry, i.e the user thinks their code might work if we try again in some amount of time. In that case we'd
probably bound it somehow, e.g. exponential backoff/max retry count before switching to a graceful failure.
- User-triggered retry. This would be if user config were incorrect, and we'd give a corresponding RPC call to replay
from previous checkpoint when that was fixed.

For exceptions we might throw to user code for them to handle, I'd propose putting those alongside the services that
might throw them. Thoughts?
 */

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