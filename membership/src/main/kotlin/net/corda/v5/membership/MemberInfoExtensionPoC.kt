package net.corda.v5.membership

import net.corda.impl.application.node.EndpointInfoImpl
import net.corda.internal.application.node.EndpointInfo

class MemberInfoExtensionPoC {
    companion object {
        /** Key name for identity keys property. */
        const val IDENTITY_KEYS = "corda.identityKeys"
        /** Key name for platform version property. */
        const val PLATFORM_VERSION = "corda.platformVersion"
        /** Key name for party property. */
        const val PARTY_NAME = "corda.party.name"
        const val PARTY_KEY = "corda.party.key"
        /** Key name for serial property. */
        const val SERIAL = "corda.serial"
        /** Key name for status property. */
        const val STATUS = "corda.status"
        /** Key name for endpoints property. */
        const val ENDPOINTS = "corda.endpoints"
        /** Key name for softwareVersion property. */
        const val SOFTWARE_VERSION = "corda.softwareVersion"
        /** Key name for group identifier property. */
        const val GROUP_ID = "corda.groupId"

        /** Active nodes can transact in the Membership Group with the other nodes. **/
        const val MEMBER_STATUS_ACTIVE = "ACTIVE"
        /**
         * Membership request has been submitted but Group Manager still hasn't responded to it. Nodes with this status can't
         * communicate with the other nodes in the Membership Group.
         */
        const val MEMBER_STATUS_PENDING = "PENDING"
        /**
         * Suspended nodes can't communicate with the other nodes in the Membership Group. They are still members of the Membership Group
         * meaning they can be re-activated.
         */
        const val MEMBER_STATUS_SUSPENDED = "SUSPENDED"

        /** Group identifier. UUID as a String. */
        @JvmStatic
        val MemberInfoPoC.groupId: String
            get() = memberProvidedContext.getValue(GROUP_ID)

        /**  List of P2P endpoints for member's node. */
        @JvmStatic
        val MemberInfoPoC.endpoints: List<EndpointInfo>
            get() = memberProvidedContext.map.readEndpoints()
        /** Corda-Release-Version. */
        @JvmStatic
        val MemberInfoPoC.softwareVersion: String
            get() = memberProvidedContext.getValue(SOFTWARE_VERSION)
        /** Status of Membership. */
        @JvmStatic
        val MemberInfoPoC.status: String
            get() = mgmProvidedContext.getValue(STATUS)

        private val cachedValues = mutableMapOf<String, Any>()

        @Suppress("UNCHECKED_CAST")
        private fun Map<String, String>.readEndpoints(): List<EndpointInfo> {
            if(cachedValues.containsKey(ENDPOINTS)) {
                return cachedValues.getValue(ENDPOINTS) as List<EndpointInfo>
            }
            val result = filter { it.key.startsWith(ENDPOINTS) }
                .asSequence()
                .groupBy { it.key.split(".")[2] }
                .map { EndpointInfoImpl(
                    it.value.first { it.key.contains("connectionURL") }.value,
                    it.value.first { it.key.contains("protocolVersion") }.value.toInt()
                ) }
            cachedValues[ENDPOINTS] = result
            return result
        }
    }
}