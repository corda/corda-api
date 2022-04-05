package net.corda.schema.configuration

/**
 * Configuration keys to access public parts of the configuration under the corda.messaging key
 */
object MessagingConfig {

    /**
     * Configuration paths that should be merged into the messaging config block from the boot config.
     *
     * Note that these do not appear directly in the user-facing schema, as they are properties provided to the process
     * on start.
     */
    object Boot {
        const val INSTANCE_ID = "instance.id"
        const val TOPIC_PREFIX = "topic.prefix"
    }

    /**
     * Configuration for connecting to the underlying message bus.
     */
    object Bus {
        const val BUS = "bus"
        const val BUS_TYPE = "$BUS.busType"
        const val KAFKA_PROPERTIES = "$BUS.kafkaProperties"
        const val KAFKA_PROPERTIES_COMMON = "$KAFKA_PROPERTIES.common"
        const val BOOTSTRAP_SERVER = "$KAFKA_PROPERTIES_COMMON.bootstrap.servers"
        const val KAFKA_PROPERTIES_CONSUMER = "$KAFKA_PROPERTIES.consumer"
        const val CONSUMER_MAX_POLL_INTERVAL = "$KAFKA_PROPERTIES_CONSUMER.max.poll.interval.ms"

        const val DB_PROPERTIES = "$BUS.dbProperties"
    }

    /**
     * Subscription related configuration.
     */
    object Subscription {
        const val SUBSCRIPTION = "subscription"
        const val POLL_TIMEOUT = "$SUBSCRIPTION.poll.timeout"
        const val THREAD_STOP_TIMEOUT = "$SUBSCRIPTION.thread.stop.timeout"
        const val PROCESSOR_RETRIES = "$SUBSCRIPTION.processor.retries"
        const val SUBSCRIBE_RETRIES = "$SUBSCRIPTION.subscribe.retries"
        const val COMMIT_RETRIES = "$SUBSCRIPTION.commit.retries"
        const val PROCESSOR_TIMEOUT = "$SUBSCRIPTION.processor.timeout"
    }

    /**
     * Publisher related configuration.
     */
    object Publisher {
        const val PUBLISHER = "publisher"
        const val CLOSE_TIMEOUT = "$PUBLISHER.close.timeout"
        const val TRANSACTIONAL = "$PUBLISHER.transactional"
    }
}