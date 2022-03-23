package net.corda.v5.application.services.lifecycle

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.services.core.lifecycle.ServiceLifecycleEvent

/**
 * This event is dispatched when State Machine is fully started.
 *
 * If a handler for this event throws [CordaServiceCriticalFailureException] - this is the way to flag that it will not make
 * sense for Corda node to continue its operation. The lifecycle events dispatcher will endeavor to terminate node's JVM as soon
 * as practically possible.
 */
@DoNotImplement
interface StateMachineStarted : ServiceLifecycleEvent