package net.corda.schema

class TestSchema {
    companion object {

        /**
         * P2P Topic Test Schema
         */
        const val CRYPTO_KEYS_TOPIC = "p2p.crypto.keys"
        const val GROUP_NETWORK_MAP_TOPIC = "p2p.group.netmap"
        const val IDENTITY_NETWORK_MAP_TOPIC = "p2p.identity.netmap"
        const val HOSTED_MAP_TOPIC = "p2p.hosted.identities"
    }
}
