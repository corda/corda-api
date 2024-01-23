package net.corda.schema.configuration;

/**
 * Configuration keys for State Manager config used to bootstrap a worker.
 */
public final class StateManagerConfig {
    private StateManagerConfig() {
    }

    public static final String STATE_MANAGER = "stateManager";
    public static final String TYPE = "type";
    public static final String STATE_TYPE = "stateType";

    // Keys for state type configuration
    public enum StateType {
        FLOW_CHECKPOINT("flowCheckpoint"),
        P2P_SESSION("p2pSession"),
        FLOW_MAPPING("flowMapping"),
        KEY_ROTATION("keyRotation"),
        TOKEN_POOL_CACHE("tokenPoolCache");

        private final String value;

        StateType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Database Configuration Values
    public static final class Database {
        public static final String DB_PROPERTIES = "database";
        public static final String JDBC_USER = DB_PROPERTIES + ".user";
        public static final String JDBC_PASS = DB_PROPERTIES + ".pass";

        public static final String JDBC_URL = DB_PROPERTIES + ".jdbc.url";
        public static final String JDBC_DRIVER = DB_PROPERTIES + ".jdbc.driver";
        public static final String JDBC_DRIVER_DIRECTORY = DB_PROPERTIES + ".jdbc.directory";
        public static final String JDBC_POOL_MAX_SIZE = DB_PROPERTIES + ".pool.maxSize";
        public static final String JDBC_POOL_MIN_SIZE = DB_PROPERTIES + ".pool.minSize";
        public static final String JDBC_POOL_IDLE_TIMEOUT_SECONDS = DB_PROPERTIES + ".pool.idleTimeoutSeconds";
        public static final String JDBC_POOL_MAX_LIFETIME_SECONDS = DB_PROPERTIES + ".pool.maxLifetimeSeconds";
        public static final String JDBC_POOL_KEEP_ALIVE_TIME_SECONDS = DB_PROPERTIES + ".pool.keepAliveTimeSeconds";
        public static final String JDBC_POOL_VALIDATION_TIMEOUT_SECONDS = DB_PROPERTIES + ".pool.validationTimeoutSeconds";
    }
}
