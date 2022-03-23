package net.corda.v5.application.flows.core

import kotlin.annotation.AnnotationTarget.FIELD

/**
 * This annotation can be used with [Flow][net.corda.v5.application.flows.Flow] and
 * [CordaService][net.corda.v5.application.services.CordaService] to indicate which dependencies should be injected.
 */
/*
JH: If we're also providing Corda services, they require this annotation too. I'm thinking we should just split the
API here and do this a bit differently in the services world, and leave this one to flows.
 */
@Target(FIELD)
@MustBeDocumented
annotation class CordaInject