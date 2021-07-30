package net.corda.v5.application.services.lifecycle

import net.corda.v5.base.annotations.DoNotImplement

/**
 * This is the parent interface for all service lifecycle events. This type is passed in the [ServiceLifecycleObserver.onEvent] method.
 */
@DoNotImplement
interface ServiceLifecycleEvent

