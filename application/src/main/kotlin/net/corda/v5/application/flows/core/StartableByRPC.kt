package net.corda.v5.application.flows.core

import kotlin.annotation.AnnotationTarget.CLASS

/**
 * Allows a [Flow] to be started by RPC.
 *
 * If the annotation is missing, the flow will not be allowed to start via RPC and an exception will be thrown if done so.
 */
/*
JH: We're looking to redesign this. Flows startable from an external source require JSON input/output and we should
encode that more strongly in C5 (this might also let us drop JSONConstructor).

I'd like to make this a bit more generic, and to basically say "this flow is startable by some external event". In the
future that could be some Kafka-based thing if we give access to our topics from external systems.
 */
@Target(CLASS)
@MustBeDocumented
annotation class StartableByRPC