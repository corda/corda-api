package net.corda.v5.application.flows;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * The {@link CordaInject} annotation should be used within {@link Flow}s to inject Corda's platform services.
 * <p>
 * The annotation is placed onto a [Flow]'s fields/properties and not on constructor parameters.
 * <p>
 * Example usage:
 * <ul>
 * <li>Kotlin:
 * <pre>
 * class MyFlow : RPCStartableFlow {
 *
 *     &#64;CordaInject
 *     lateinit var flowEngine: FlowEngine
 *
 *     &#64;CordaInject
 *     lateinit var flowMessaging: FlowMessaging
 *
 *     &#64;Suspendable
 *     override fun call(requestBody: RPCRequestData): String {
 *         ...
 *     }
 * }
 * </pre></li>
 * <li>Java:
 * <pre>
 * class MyFlow implements RPCStartableFlow {
 *
 *     &#64;CordaInject
 *     public FlowEngine flowEngine;
 *
 *     &#64;CordaInject
 *     public FlowMessaging flowMessaging;
 *
 *     &#64;Suspendable
 *     &#64;Override
 *     public String call(RPCRequestData requestBody) {
 *         ...
 *     }
 * }
 * </li></pre>
 * </ul>
 * The properties annotated with {@link CordaInject} can be public or private as the reflection that sets them can bypass
 * their visibility modifiers. Making a {@link Flow}'s properties public, internal or package private is convenient for unit
 * testing and does not affect the {@link Flow}'s runtime behaviour.
 * <p>
 * The injected properties cannot be accessed within a {@link Flow}'s constructor and should only be used from within a
 * {@link Flow}'s call method or subsequently executed code.
 */
@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface CordaInject {
}
