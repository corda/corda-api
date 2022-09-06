package net.corda.v5.application.flows;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * [InitiatingFlow] marks a flow as initiating, meaning that it starts a [ResponderFlow] when creating a session with
 * another network entity.
 *
 * Any flows that participate in flow sessions must declare a protocol name, using [InitiatingFlow.protocol]
 * and [InitiatedBy.protocol]. The platform will use the protocol name to establish what [ResponderFlow] to invoke on
 * the responder side when the initiator side creates a session.
 *
 * For example, to set up an initiator-responder pair, declare the following:
 *
 * ```kotlin
 * @InitiatingFlow(protocol = "myprotocol")
 * class MyFlowInitiator : Flow {
 *  ...
 * }
 *
 * @InitiatedBy(protocol = "myprotocol")
 * class MyFlowResponder : ResponderFlow {
 *  ...
 * }
 * ```
 *
 * Flows may also optionally declare a range of protocol versions they support. By default, flows support protocol
 * version 1 only. When initiating a flow, the platform will look for the highest supported protocol version as declared
 * on the initiating side and start that flow on the responder side.
 *
 * @property protocol The protocol of the annotated flow.
 * @property version The protocol version.
 *
 * @see InitiatedBy
 * @see ResponderFlow
 */
@Target(TYPE)
@Documented
@Retention(RUNTIME)
public @interface InitiatingFlow {
    @NotNull
    String protocol();

    @NotNull
    int[] version() default { 1 };
}
