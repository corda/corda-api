package net.corda.schema.configuration;

import static net.corda.schema.configuration.MessagingConfig.MAX_ALLOWED_MSG_SIZE;

/**
 * Configuration paths for values used to bootstrap the worker.
 */
@SuppressWarnings("unused")
public final class BootConfig {
    private BootConfig() {
    }

    public static final String INSTANCE_ID = "instanceId";
    public static final String TOPIC_PREFIX = "topicPrefix";
    public static final String BOOT_MAX_ALLOWED_MSG_SIZE = MAX_ALLOWED_MSG_SIZE;

    public static final String BOOT_KAFKA = "kafka";
    public static final String BOOT_KAFKA_COMMON = BOOT_KAFKA + ".common";
    public static final String BOOT_CRYPTO = "crypto";

    public static final String BOOT_DB = "db";
    public static final String BOOT_JDBC_URL = BOOT_DB + ".database.jdbc.url";
    public static final String BOOT_JDBC_USER = BOOT_DB + ".database.user";
    public static final String BOOT_JDBC_PASS = BOOT_DB + ".database.pass";

    public static final String BOOT_DIR = "dir";
    public static final String BOOT_WORKSPACE_DIR = BOOT_DIR + ".workspace";
    public static final String BOOT_TMP_DIR = BOOT_DIR + ".tmp";

    public static final String BOOT_REST = "rest";
    public static final String BOOT_REST_TLS_KEYSTORE_FILE_PATH = BOOT_REST + ".tls.keystore.path";
    public static final String BOOT_REST_TLS_KEYSTORE_PASSWORD = BOOT_REST + ".tls.keystore.password";
    public static final String BOOT_REST_TLS_CRT_PATH = BOOT_REST + ".tls.crt.path";
    public static final String BOOT_REST_TLS_KEY_PATH = BOOT_REST + ".tls.key.path";
    public static final String BOOT_REST_TLS_CA_CRT_PATH = BOOT_REST + ".tls.ca.crt.path";

    public static final String BOOT_SECRETS = "secrets";

    public static final String BOOT_INPUT_TOPICS = "inputTopics";
    public static final String BOOT_OUTPUT_TOPICS = "outputTopics";

    // Keys that are valid for the flow output topic map
    public static final String DB_OUTPUT = "db";
    public static final String CRYPTO_OUTPUT = "crypto";
    public static final String VERIFICATION_OUTPUT = "verification";
    public static final String SESSION_OUTPUT = "session";
    public static final String START_OUTPUT = "start";
    public static final String LEDGER_OUTPUT = "ledger";
    public static final String UNIQUENESS_OUTPUT = "uniqueness";
}
