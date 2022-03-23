package net.corda.v5.application.flows.messaging

import net.corda.v5.base.annotations.CordaSerializable

/*
JH: This needs a rework, but something like this could be valuable as a header to give some information on the other
side. I think we should insist that both sides are running the same CPI version though, in which case this might matter
less.
 */

/**
 * Version and name of the CorDapp hosting the other side of the flow.
 */
@CordaSerializable
data class FlowInfo(
        /**
         * The integer flow version the other side is using.
         * @see InitiatingFlow
         */
        val flowVersion: Int,
        /**
         * Name of the CorDapp jar hosting the flow, without the .jar extension. It will include a unique identifier
         * to deduplicate it from other releases of the same CorDapp, typically a version string. See the
         * [CorDapp JAR format](https://docs.corda.net/cordapp-build-systems.html#cordapp-jar-format) for more details.
         */
        val appName: String)
