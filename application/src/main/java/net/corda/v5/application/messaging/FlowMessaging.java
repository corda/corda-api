package net.corda.v5.application.messaging;

import net.corda.v5.application.flows.Flow;
import net.corda.v5.application.flows.FlowContextProperties;
import net.corda.v5.application.flows.ResponderFlow;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.base.types.MemberX500Name;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link FlowMessaging} allows a flow to initiate and communicate with one or more 3rd party flows.
 * <p>
 * The platform will provide an instance of {@link FlowMessaging} to flows via property injection.
 * <p>
 * A {@link Flow} can initiate one or more flows other counterparties within the network, when a new flow is initiated a
 * {@link FlowSession} is created. The {@link FlowSession} represents the connection to an initiated flow and can be used
 * to send and receive data between the two flows.
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:<pre>{@code
 * class MyFlow : ClientStartableFlow {
 *    @CordaInject
 *    lateinit var flowMessaging: FlowMessaging
 *
 *    override fun call(requestBody: RestRequestBody): String {
 *        val counterparty = parse("CN=Alice, O=Alice Corp, L=LDN, C=GB")
 *
 *        val session = flowMessaging.initiateFlow(counterparty)
 *
 *        val result = session.sendAndReceive<String>("hello")
 *
 *        session.close()
 *
 *        return result
 *    }
 *  }
 * }</pre></li>
 * <li>Java:<pre>{@code
 * class MyFlow implements ClientStartableFlow {
 *
 *    @CordaInject
 *    public FlowMessaging flowMessaging;
 *
 *    @Override
 *    public String call(RestRequestBody requestBody) {
 *        MemberX500Name counterparty = MemberX500Name.parse("CN=Alice, O=Alice Corp, L=LDN, C=GB");
 *        FlowSession session = flowMessaging.initiateFlow(counterparty);
 *
 *        String result = session.sendAndReceive(String.class, "hello");
 *
 *        session.close();
 *
 *        return result;
 *    }
 *}
 * }</pre></li>
 * </ul>
 */
@DoNotImplement
public interface FlowMessaging {

    /**
     * Creates a communication session with a counterparty's {@link ResponderFlow}. Subsequently, you may send/receive using
     * this session object. Note that this function does not communicate in itself. The counter-flow will be kicked off
     * by the first send/receive.
     * <p>
     * Initiated flows are initiated with context based on the context of the initiating flow at the point in time this
     * method is called. The context of the initiating flow is snapshotted by the returned session. Altering the flow
     * context has no effect on the context of the session after this point, and therefore it has no effect on the
     * context of the initiated flow either.
     *
     * @param x500Name The X500 name of the member to communicate with.
     *
     * @return The session.
     */
    @Suspendable
    @NotNull
    FlowSession initiateFlow(@NotNull MemberX500Name x500Name);


    /**
     * Creates a communication session with a counterparty's {@link ResponderFlow}. Subsequently, you may send/receive using
     * this session object. Note that this function does not communicate in itself. The counter-flow will be kicked off
     * by the first send/receive.
     * <p>
     * Initiated flows are initiated with context based on the context of the initiating flow at the point in time this
     * method is called. The context of the initiating flow is snapshotted by the returned session. Altering the flow
     * context has no effect on the context of the session after this point, and therefore it has no effect on the
     * context of the initiated flow either.
     *
     * @param x500Name The X500 name of the member to communicate with.
     * @param requireClose When set to true, the initiated party will send a close message after calling FlowSession.close()
     *                     and the initiating party will suspend and wait to receive the message when they call FlowSession.close().
     *                     When set to false the session is marked as terminated immediately when close() is called.
     *
     * @return The session.
     */
    @Suspendable
    @NotNull
    FlowSession initiateFlow(@NotNull MemberX500Name x500Name, boolean requireClose);

    /**
     * Creates a communication session with another member. Subsequently, you may send/receive using this session object.
     * Note that this function does not communicate in itself. The counter-flow will be kicked off by the first
     * send/receive.
     * <p>
     * This overload takes a builder of context properties. Any properties set or modified against the context passed to
     * this builder will be propagated to initiated flows and all that flow's initiated flows and sub flows down the
     * stack. The properties passed to the builder are pre-populated with the current flow context properties, see
     * {@link FlowContextProperties}. Altering the current flow context has no effect on the context of the session after the
     * builder is applied and the session returned by this method, and therefore it has no effect on the context of the
     * initiated flow either.
     * <p>
     * Example of use in Kotlin.
     * ```Kotlin
     * val flowSession = flowMessaging.initiateFlow(virtualNodeName) { flowContextProperties ->
     *      flowContextProperties["key"] = "value"
     * }
     * ```
     * Example of use in Java.
     * ```Java
     * FlowSession flowSession = flowMessaging.initiateFlow(virtualNodeName, (flowContextProperties) -> {
     *      flowContextProperties.put("key", "value");
     * });
     * ```
     *
     * @param x500Name The X500 name of the member to communicate with.
     * @param flowContextPropertiesBuilder A builder of context properties.
     *
     * @return The session.
     *
     * @throws IllegalArgumentException if the builder tries to set a property for which a platform property already
     * exists or if the key is prefixed by {@link FlowContextProperties#CORDA_RESERVED_PREFIX}. See also
     * {@link FlowContextProperties}. Any other
     * exception thrown by the builder will also be thrown through here and should be avoided in the provided
     * implementation, see {@link FlowContextPropertiesBuilder}.
     */
    @Suspendable
    @NotNull
    FlowSession initiateFlow(@NotNull MemberX500Name x500Name, @NotNull FlowContextPropertiesBuilder flowContextPropertiesBuilder);

    /**
     * Invoke an interop facade.
     *
     * This API is work in progress and is expected to change as interop functionality matures.
     *
     * @param memberName The MemberX500name of the alter-ego.
     * @param interopGroupId The UUID of the interop group to communicate with.
     * @param facadeName The name of the facade to invoke.
     * @param methodName The name of the function within the facade to invoke.
     * @param payload The payload for the facade call.
     * @return The response string from the facade.
     */
    @Suspendable
    @NotNull
    String callFacade(
            @NotNull MemberX500Name memberName,
            @NotNull String interopGroupId,
            @NotNull String facadeName,
            @NotNull String methodName,
            @NotNull String payload);

    /**
     * Creates a communication session with another member. Subsequently, you may send/receive using this session object.
     * Note that this function does not communicate in itself. The counter-flow will be kicked off by the first
     * send/receive.
     * <p>
     * This overload takes a builder of context properties. Any properties set or modified against the context passed to
     * this builder will be propagated to initiated flows and all that flow's initiated flows and sub flows down the
     * stack. The properties passed to the builder are pre-populated with the current flow context properties, see
     * {@link FlowContextProperties}. Altering the current flow context has no effect on the context of the session after the
     * builder is applied and the session returned by this method, and therefore it has no effect on the context of the
     * initiated flow either.
     * <p>
     * Example of use in Kotlin.
     * ```Kotlin
     * val flowSession = flowMessaging.initiateFlow(virtualNodeName) { flowContextProperties ->
     *      flowContextProperties["key"] = "value"
     * }
     * ```
     * Example of use in Java.
     * ```Java
     * FlowSession flowSession = flowMessaging.initiateFlow(virtualNodeName, (flowContextProperties) -> {
     *      flowContextProperties.put("key", "value");
     * });
     * ```
     *
     * @param x500Name The X500 name of the member to communicate with.
     * @param requireClose When set to true, the initiated party will send a close message after calling FlowSession.close()
     *                     and the initiating party will suspend and wait to receive the message when they call FlowSession.close().
     *                     When set to false the session is marked as terminated immediately when close() is called.
     * @param flowContextPropertiesBuilder A builder of context properties.
     *
     * @return The session.
     *
     * @throws IllegalArgumentException if the builder tries to set a property for which a platform property already
     * exists or if the key is prefixed by {@link FlowContextProperties#CORDA_RESERVED_PREFIX}. See also
     * {@link FlowContextProperties}. Any other
     * exception thrown by the builder will also be thrown through here and should be avoided in the provided
     * implementation, see {@link FlowContextPropertiesBuilder}.
     */
    @Suspendable
    @NotNull
    FlowSession initiateFlow(@NotNull MemberX500Name x500Name, boolean requireClose, @NotNull FlowContextPropertiesBuilder flowContextPropertiesBuilder);

    /**
     * Suspends until a message has been received for each session in the specified {@code sessions}.
     * <p>
     * Consider {@link #receiveAllMap(Map)} when sessions are expected to receive different types.
     * <p>
     * The {@code receiveType} should be of a type that is annotated with @CordaSerializable or a primitive type. This
     * function cannot handle types that do not meet these criteria.
     *
     * @param <R> type of object to be received for all {@code sessions}.
     * @param receiveType Type of object to be received for all {@code sessions}, which should be either a primitive type
     *                    or a type annotated with @CordaSerializable.
     * @param sessions Set of sessions to receive from.
     * @return a {@link List} containing the objects received from the {@code sessions}.
     *
     * @throws CordaRuntimeException if any session is closed or in a failed state.
     */
    @Suspendable
    @NotNull
    <R> List<R> receiveAll(@NotNull Class<? extends R> receiveType, @NotNull Set<FlowSession> sessions);

    /**
     * Suspends until a message has been received for each session in the specified {@code sessions}.
     * <p>
     * Consider {@link #receiveAll(Class, Set)} when the same type is expected from all sessions.
     * <p>
     * The types of objects expected to be received should be annotated with @CordaSerializable or be a primitive type. This
     * function cannot handle types that do not meet these criteria.
     *
     * @param sessions Map of session to the type of object that is expected to be received
     * @return a {@link Map} containing the objects received by the {@link FlowSession}s who sent them.
     *
     * @throws CordaRuntimeException if any session is closed or in a failed state.
     */
    @Suspendable
    @NotNull
    Map<FlowSession, ?> receiveAllMap(@NotNull Map<FlowSession, Class<?>> sessions);

    /**
     * Queues the given {@code payload} for sending to the provided {@code sessions} and continues without waiting for a response.
     * <p>
     * Note that the other parties may receive the message at some arbitrary later point or not at all: if one of the provided [sessions]
     * is offline then message delivery will be retried until the session expires. Sessions are deemed to be expired when this session
     * stops receiving heartbeat messages from the counterparty within the configurable timeout.
     * <p>
     * The {@code payload} object should be of a type that is annotated with @CordaSerializable or a primitive type. This
     * function cannot handle types that do not meet these criteria.
     *
     * @param payload the payload to send, which should be either a primitive type or a type annotated with @CordaSerializable.
     * @param sessions the sessions to send the provided payload to.
     *
     * @throws CordaRuntimeException if any session is closed or in a failed state.
     */
    @Suspendable
    void sendAll(@NotNull Object payload, @NotNull Set<FlowSession> sessions);

    /**
     * Queues the given payloads for sending to the provided sessions and continues without waiting for a response.
     * <p>
     * Note that the other parties may receive the message at some arbitrary later point or not at all: if one of the provided [sessions]
     * is offline then message delivery will be retried until the session expires. Sessions are deemed to be expired when this session
     * stops receiving heartbeat messages from the counterparty within the configurable timeout.
     * <p>
     * The objects in {@code payloadsPerSession} should be of types that are annotated with @CordaSerializable or be primitive types. This
     * function cannot handle types that do not meet these criteria.
     *
     * @param payloadsPerSession a mapping that contains the payload to be sent to each session.
     *                           The payloads should be either of primitive types or types annotated with @CordaSerializable.
     *
     * @throws CordaRuntimeException if any session is closed or in a failed state.
     */
    @Suspendable
    void sendAllMap(@NotNull Map<FlowSession, Object> payloadsPerSession);
}
