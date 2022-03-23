package net.corda.v5.application.flows.core

/**
 * Marks a constructor of a flow indicating that it can be instantiated from a JSON String.
 * Flows that are available to start via the RPC service with [RpcStartFlowRequestParameters]
 * must have a constructor with this annotation.
 */
/*
JH: I would like to remove this entirely, but it depends on the StartableByRPC redesign.
 */
@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class JsonConstructor