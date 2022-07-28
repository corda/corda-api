package net.corda.v5.application.flows

/**
 * Context properties of a [Flow] are key value pairs of Strings. They are comprised of two sets of properties, those
 * set by the platform when a [Flow] is started, and those which are added by the CorDapp developer. Properties are
 * immutable once set. Where keys exist as platform properties already, setting 'user' properties is an effective no-op
 * as they will always be ignored in preference for the platform value.
 * Both sets of context properties are passed from the originating [Flow] to sub-flows, initiated flows, and services.
 * Where sub-flows and initiated flows have extra 'user' properties added, these are only visible in the scope of those
 * flows and any of their sub-flows, initiated flows or services, but not back up the flow stack to any flows which
 * sit above the one in which they were added. In this way context properties can also be thought of as a stack,
 * unwinding along with the flow stack.
 */
interface FlowContextProperties {
    fun set(key: String, value: String)
    fun get(key: String): String?
}