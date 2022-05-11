package net.corda.schema

const val DEFAULT_PARTITION = 1
const val DEFAULT_REPLICATION = 3

interface TopicDef {
    val name: String
    val numPartitions: Int
    val replicationFactor: Int
    val config: Map<String, String>
        get() = emptyMap()
}

sealed class Schemas {
    companion object {
        /**
         * Messaging topic schema
         *
         * The following is an example schema for topics.  In this case, for a compacted topic, as defined
         * in the [config] section.
         *
         * topics = [
         *     {
         *         topicName = "exampleCompactedTopic"
         *         numPartitions = 1
         *         replicationFactor = 3
         *         config {
         *             cleanup.policy = compact
         *         }
         *     }
         * ]
         */
        const val TOPIC_NAME = "topicName"
        const val NUM_PARTITIONS = "numPartitions"
        const val REPLICATION_FACTOR = "replicationFactor"
        const val TOPIC_CONFIG = "config"

        /**
         * [getStateAndEventDLQTopic] returns the state and event dlq topic
         * [getStateAndEventStateTopic] returns the state and event state topic
         * [getRPCResponseTopic] returns the rpc response topic
         */
        fun getStateAndEventDLQTopic(topic: String) = "$topic.dlq"
        fun getStateAndEventStateTopic(topic: String) = "$topic.state"
        fun getRPCResponseTopic(topic: String) = "$topic.resp"
    }

    /**
     * Config read topic schema
     */
    sealed class Config {
        companion object {
            const val CONFIG_TOPIC = "config.topic"
            const val CONFIG_MGMT_REQUEST_TOPIC = "config.management.request"
            const val CONFIG_MGMT_REQUEST_RESP_TOPIC = "$CONFIG_MGMT_REQUEST_TOPIC.resp"
        }

        data class ConfigManagementRequestTopic(
            override val name: String = CONFIG_MGMT_REQUEST_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class ConfigManagementRequestResponseTopic(
            override val name: String = CONFIG_MGMT_REQUEST_RESP_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class ConfigTopic(
            override val name: String = CONFIG_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef
    }

    /**
     * CPI topic schema
     */

    sealed class CPI {
        companion object {
            const val CPI_INFO_TOPIC = "cpi.info"
            const val CPI_UPLOAD_TOPIC = "cpi.upload"
            const val CPI_UPLOAD_STATUS_TOPIC = "cpi.upload.status"
        }

        data class CPIInfoTopic(
            override val name: String = CPI_INFO_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class CPIUploadTopic(
            override val name: String = CPI_UPLOAD_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class CPIStatusTopic(
            override val name: String = CPI_UPLOAD_STATUS_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
    }

/**
     * Crypto topic schema
     */
    sealed class Crypto {
        companion object {
            const val HSM_REGISTRATION_MESSAGE_TOPIC = "crypto.registration.hsm"
            const val RPC_OPS_MESSAGE_TOPIC = "crypto.ops.rpc"
            const val FLOW_OPS_MESSAGE_TOPIC = "crypto.ops.flow"
            const val HSM_CONFIG_TOPIC = "crypto.config.hsm"
            const val HSM_CONFIG_LABEL_TOPIC = "crypto.config.hsm.label"
            const val MEMBER_CONFIG_TOPIC = "crypto.config.member"
            const val SIGNING_KEY_PERSISTENCE_TOPIC = "crypto.key.info"
            const val SOFT_HSM_PERSISTENCE_TOPIC = "crypto.key.soft"
            const val EVENT_TOPIC = "crypto.event"
            const val RPC_HSM_REGISTRATION_MESSAGE_TOPIC = "crypto.hsm.rpc.registration"
            const val RPC_HSM_CONFIGURATION_MESSAGE_TOPIC = "crypto.hsm.rpc.configuration"
        }
        data class CryptoKeyInfoTopic(
            override val name: String = SIGNING_KEY_PERSISTENCE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
        data class CryptoKeySoftTopic(
            override val name: String = SOFT_HSM_PERSISTENCE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
        data class CryptoOpsFlowTopic(
            override val name: String = FLOW_OPS_MESSAGE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
        data class CryptoOpsRpcTopic(
            override val name: String = RPC_OPS_MESSAGE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
        data class CryptoOpsRpcRespTopic(
            override val name: String = "$RPC_OPS_MESSAGE_TOPIC.reps",
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
    }

    /**
     * Flow event topic schema
     */
    sealed class Flow {
        companion object {
            const val FLOW_STATUS_TOPIC = "flow.status"
            const val FLOW_EVENT_TOPIC = "flow.event"
            const val FLOW_EVENT_STATE_TOPIC = "flow.event.state"
            const val FLOW_EVENT_DLQ_TOPIC = "flow.event.dlq"
            const val FLOW_MAPPER_EVENT_TOPIC = "flow.mapper.event"
            const val FLOW_MAPPER_EVENT_STATE_TOPIC = "flow.mapper.event.state"
            const val FLOW_MAPPER_EVENT_DLQ_TOPIC = "flow.mapper.event.dlq"
        }

        data class FlowEventTopic(
            override val name: String = FLOW_EVENT_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class FlowEventStateTopic(
            override val name: String = FLOW_EVENT_STATE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class FlowEventDLQTopic(
            override val name: String = FLOW_EVENT_DLQ_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class FlowMapperEventTopic(
            override val name: String = FLOW_MAPPER_EVENT_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class FlowMapperEventStateTopic(
            override val name: String = FLOW_MAPPER_EVENT_STATE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class FlowMapperEventDLQTopic(
            override val name: String = FLOW_MAPPER_EVENT_DLQ_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class FlowStatusTopic(
            override val name: String = FLOW_STATUS_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef
    }

    /**
     * Membership topic schema
     */
    sealed class Membership {
        companion object {
            // Member persistence topics
            const val MEMBER_LIST_TOPIC = "membership.members"
            const val GROUP_PARAMETERS_TOPIC = "membership.group.params"
            const val CPI_WHITELIST_TOPIC = "membership.group.cpi.whitelists"
            const val PROPOSAL_TOPIC = "membership.proposals"
            const val MEMBERSHIP_RPC_TOPIC = "membership.rpc.ops"

            // Member messaging topics
            const val UPDATE_TOPIC = "membership.update"
            const val EVENT_TOPIC = "membership.event"
        }

        data class MembershipMembersTopic(
            override val name: String = MEMBER_LIST_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class MembershipRpcOpsTopic(
            override val name: String = MEMBERSHIP_RPC_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class MembershipRpcOpsRespTopic(
            override val name: String = "$MEMBERSHIP_RPC_TOPIC.resp",
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
    }

    /**
     * P2P topic schema
     */
    sealed class P2P {
        companion object {
            const val P2P_OUT_TOPIC = "p2p.out"
            const val P2P_IN_TOPIC = "p2p.in"
            const val P2P_OUT_MARKERS = "p2p.out.markers"
            const val LINK_OUT_TOPIC = "link.out"
            const val LINK_IN_TOPIC = "link.in"
            const val SESSION_OUT_PARTITIONS = "session.out.partitions"
            const val GATEWAY_TLS_TRUSTSTORES = "gateway.tls.truststores"
            const val GATEWAY_TLS_CERTIFICATES = "gateway.tls.certs"
        }

        data class P2PInTopic(
            override val name: String = P2P_IN_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class P2POutTopic(
            override val name: String = P2P_OUT_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class P2POutMarkerTopic(
            override val name: String = P2P_OUT_MARKERS,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef
    }

    /**
     * Permissions Message schema
     */
    sealed class Permissions {
        companion object {
            const val PERMISSIONS_USER_SUMMARY_TOPIC = "permissions.user.summary"
        }

        data class PermissionsUserSummaryTopic(
            override val name: String = PERMISSIONS_USER_SUMMARY_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef
    }

    /**
     * RPC Message schema
     */
    sealed class RPC {
        companion object {
            const val RPC_PERM_MGMT_REQ_TOPIC = "rpc.permissions.management"
            const val RPC_PERM_MGMT_RESP_TOPIC = "rpc.permissions.management.resp"
            const val RPC_PERM_USER_TOPIC = "rpc.permissions.user"
            const val RPC_PERM_GROUP_TOPIC = "rpc.permissions.group"
            const val RPC_PERM_ROLE_TOPIC = "rpc.permissions.role"
            const val RPC_PERM_ENTITY_TOPIC = "rpc.permissions.permission"
        }

        data class RPCPermissionsManagementTopic(
            override val name: String = RPC_PERM_MGMT_REQ_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class RPCPermissionsManagementResponseTopic(
            override val name: String = RPC_PERM_MGMT_RESP_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class RPCPermissionsGroupTopic(
            override val name: String = RPC_PERM_GROUP_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class RPCPermissionsPermissionTopic(
            override val name: String = RPC_PERM_ENTITY_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class RPCPermissionsUserTopic(
            override val name: String = RPC_PERM_USER_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef

        data class RPCPermissionsRoleTopic(
            override val name: String = RPC_PERM_ROLE_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef
    }

    /**
     * Virtual Node schema
     */
    sealed class VirtualNode {
        companion object {
            const val VIRTUAL_NODE_INFO_TOPIC = "virtual.node.info"
            const val VIRTUAL_NODE_CREATION_REQUEST_TOPIC = "virtual.node.creation.request"
            const val CPI_INFO_TOPIC = "cpi.info"
            const val CPI_UPLOAD_TOPIC = "cpi.upload"
            const val CPI_UPLOAD_STATUS_TOPIC = "cpi.upload.status"
            const val CPK_FILE_TOPIC = "cpk.file"
            const val ENTITY_PROCESSOR = "db.entity.processor"
        }
        data class VirtualNodeCreationRequestTopic(
            override val name: String = VIRTUAL_NODE_CREATION_REQUEST_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class VirtualNodeCreationRequestResponseTopic(
            override val name: String = "$VIRTUAL_NODE_CREATION_REQUEST_TOPIC.resp",
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
        ) : TopicDef

        data class VirtualNodeInfoTopic(
            override val name: String = VIRTUAL_NODE_INFO_TOPIC,
            override val numPartitions: Int = DEFAULT_PARTITION,
            override val replicationFactor: Int = DEFAULT_REPLICATION,
            override val config: Map<String, String> = mapOf(
                "cleanup.policy" to "compact"
            )
        ) : TopicDef
    }
}
