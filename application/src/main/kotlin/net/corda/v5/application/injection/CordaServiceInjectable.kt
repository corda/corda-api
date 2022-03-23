package net.corda.v5.application.injection

/**
 * Interfaces that extend [CordaServiceInjectable] can be injected into
 * [CordaService][net.corda.v5.application.services.CordaService]s
 * or [NotaryService][net.corda.v5.ledger.notary.NotaryService] using [CordaInject].
 */
/*
JH: See CordaFlowInjectable. Without Corda services we can chuck this entirely, but it might be worth abandoning this as
a discovery mechanism anyway.
 */
interface CordaServiceInjectable