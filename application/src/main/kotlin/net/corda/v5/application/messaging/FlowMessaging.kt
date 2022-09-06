package net.corda.v5.application.messaging

import net.corda.v5.application.flows.FlowContextProperties
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.types.MemberX500Name

@DoNotImplement
interface FlowMessaging {

    /**
     * Creates a communication session with another member. Subsequently you may send/receive using this session object.
     * Note that this function does not communicate in itself, the counter-flow will be kicked off by the first
     * send/receive.
     *
     * Initiated flows are initiated with context based on the context of the initiating flow. The context of the
     * initiating flow is snapshotted by the returned session when this method is called. Altering the parent context
     * has no effect on the context of the session after this point, and therefore it has no effect on the context of
     * the initiated flow either.
     *
     * @param x500Name The X500 name of the member to communicate with.
     * @return The session.
     */
    @Suspendable
    fun initiateFlow(x500Name: MemberX500Name): FlowSession

    /**
     * Creates a communication session with another member. Subsequently you may send/receive using this session object.
     * Note that this function does not communicate in itself, the counter-flow will be kicked off by the first
     * send/receive.
     *
     * This overload takes a mutator of context properties. Any properties set or modified against the context passed to
     * this mutator will be propagated to initiated flows and all that flow's initiated flows and sub flows down the
     * stack. The properties passed to the mutator are pre-populated with the parent context properties, see
     * [FlowContextProperties].
     *
     * @param x500Name The X500 name of the member to communicate with.
     * @param flowContextPropertiesMutator A mutator of context properties
     * @return The session.
     */
    @Suspendable
    fun initiateFlow(x500Name: MemberX500Name, flowContextPropertiesMutator: FlowContextPropertiesMutator): FlowSession

    /** Suspends until a message has been received for each session in the specified [sessions].
     *
     * Consider [receiveAll(receiveType: Class<R>, sessions: Set<FlowSession>): List<UntrustworthyData<R>>] when the same type is expected from all sessions.
     *
     * Remember that when receiving data from other parties the data should not be trusted until it's been thoroughly
     * verified for consistency and that all expectations are satisfied, as a malicious peer may send you subtly
     * corrupted data in order to exploit your code.
     *
     * @returns a [Map] containing the objects received, wrapped in an [UntrustworthyData], by the [FlowSession]s who sent them.
     */
    @Suspendable
    fun receiveAllMap(sessions: Map<FlowSession, Class<out Any>>): Map<FlowSession, UntrustworthyData<Any>>

    /**
     * Suspends until a message has been received for each session in the specified [sessions].
     *
     * Consider [sessions: Map<FlowSession, Class<out Any>>): Map<FlowSession, UntrustworthyData<Any>>] when sessions are expected to receive different types.
     *
     * Remember that when receiving data from other parties the data should not be trusted until it's been thoroughly
     * verified for consistency and that all expectations are satisfied, as a malicious peer may send you subtly
     * corrupted data in order to exploit your code.
     *
     * @returns a [List] containing the objects received, wrapped in an [UntrustworthyData], with the same order of [sessions].
     */
    @Suspendable
    fun <R> receiveAll(receiveType: Class<out R>, sessions: Set<FlowSession>): List<UntrustworthyData<R>>

    /**
     * Queues the given [payload] for sending to the provided [sessions] and continues without suspending.
     *
     * Note that the other parties may receive the message at some arbitrary later point or not at all: if one of the provided [sessions]
     * is offline then message delivery will be retried until the corresponding node comes back or until the message is older than the
     * network's event horizon time.
     *
     * @param payload the payload to send.
     * @param sessions the sessions to send the provided payload to.
     */
    @Suspendable
    fun sendAll(payload: Any, sessions: Set<FlowSession>)

    /**
     * Queues the given payloads for sending to the provided sessions and continues without suspending.
     *
     * Note that the other parties may receive the message at some arbitrary later point or not at all: if one of the provided [sessions]
     * is offline then message delivery will be retried until the corresponding node comes back or until the message is older than the
     * network's event horizon time.
     *
     * @param payloadsPerSession a mapping that contains the payload to be sent to each session.
     */
    @Suspendable
    fun sendAllMap(payloadsPerSession: Map<FlowSession, *>)

    /**
     * Closes the provided sessions and performs cleanup of any resources tied to these sessions.
     *
     * Note that sessions are closed automatically when the corresponding top-level flow terminates.
     * So, it's beneficial to eagerly close them in long-lived flows that might have many open sessions that are not needed anymore and consume resources (e.g. memory, disk etc.).
     * A closed session cannot be used anymore, e.g. to send or receive messages. So, you have to ensure you are calling this method only when the provided sessions are not going to be used anymore.
     * As a result, any operations on a closed session will fail with an [UnexpectedFlowEndException].
     * When a session is closed, the other side is informed and the session is closed there too eventually.
     * To prevent misuse of the API, if there is an attempt to close an uninitialised session the invocation will fail with an [IllegalStateException].
     */
    @Suspendable
    fun close(sessions: Set<FlowSession>)
}

/**
 * Mutator of context properties. Instances of this interface can optionally be passed to [FlowMessaging] when sessions
 * are initiated if there are requirements to modify context properties which are sent to the initiated flow.
 */
fun interface FlowContextPropertiesMutator {
    /**
     * @param flowContextProperties A set of modifiable context properties. Change these properties in the body of the
     * function as required. The modified set will be the ones used to determine the context of the initiated session.
     */
    fun apply(flowContextProperties: FlowContextProperties)
}
