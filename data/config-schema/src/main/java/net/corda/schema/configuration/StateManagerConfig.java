package net.corda.schema.configuration;

/**
 * Configuration keys for State Manager config used to bootstrap a worker.
 */
public final class StateManagerConfig {
    private StateManagerConfig() {
    }

    public static final String STATE_MANAGER = "stateManager";
    public static final String TYPE = "type";

    // Keys for state types in State Manager configuration map
    public enum StateType {
        FLOW_CHECKPOINT("flowCheckpoint"),
        FLOW_MAPPING("flowMapping"),
        FLOW_STATUS("flowStatus"),
        KEY_ROTATION("keyRotation"),
        P2P_SESSION("p2pSession"),
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
        public static final String JDBC_USER = DB_PROPERTIES + ".username";
        public static final String JDBC_PASS = DB_PROPERTIES + ".passworkd";

        public static final String JDBC_URL = DB_PROPERTIES + ".jdbc.url";
        public static final String JDBC_DRIVER = DB_PROPERTIES + ".jdbc.driver";
        public static final String JDBC_DRIVER_DIRECTORY = DB_PROPERTIES + ".jdbc.directory";
        public static final String JDBC_POOL_MAX_SIZE = DB_PROPERTIES + ".connectionPool.maxSize";
        public static final String JDBC_POOL_MIN_SIZE = DB_PROPERTIES + ".connectionPool.minSize";
        public static final String JDBC_POOL_IDLE_TIMEOUT_SECONDS = DB_PROPERTIES + ".connectionPool.idleTimeoutSeconds";
        public static final String JDBC_POOL_MAX_LIFETIME_SECONDS = DB_PROPERTIES + ".connectionPool.maxLifetimeSeconds";
        public static final String JDBC_POOL_KEEP_ALIVE_TIME_SECONDS = DB_PROPERTIES + ".connectionPool.keepAliveTimeSeconds";
        public static final String JDBC_POOL_VALIDATION_TIMEOUT_SECONDS = DB_PROPERTIES + ".connectionPool.validationTimeoutSeconds";
    }
}
