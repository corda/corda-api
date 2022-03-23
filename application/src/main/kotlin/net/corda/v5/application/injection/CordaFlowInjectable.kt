package net.corda.v5.application.injection

/**
 * Interfaces that extend [CordaFlowInjectable] can be injected into [net.corda.v5.application.flows.Flow]s using [CordaInject].
 */
/*
JH: We're not very clear on what a user should do with this and I think we need to be. I'm actually wondering if we
need it at all though. Do we do any checking of what services a user asks for when they try and inject something into a
flow? I think we were using this as a discovery mechanism and maybe we don't need it for that any more.
 */
interface CordaFlowInjectable

