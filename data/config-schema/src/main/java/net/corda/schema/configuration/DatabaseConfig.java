package net.corda.schema.configuration;

public final class DatabaseConfig {
    private DatabaseConfig() {
    }

    public static final String DB_USER = "database.user";
    public static final String DB_PASS = "database.pass";
    public static final String JDBC_DRIVER = "database.jdbc.driver";
    public static final String JDBC_DRIVER_DIRECTORY = "database.jdbc.directory";
    public static final String JDBC_URL = "database.jdbc.url";
    public static final String DB_POOL_MAX_SIZE = "database.pool.max_size";
    public static final String DB_POOL_MIN_SIZE = "database.pool.min_size";
    public static final String DB_POOL_IDLE_TIMEOUT_SECONDS = "database.pool.idleTimeoutSeconds";
    public static final String DB_POOL_MAX_LIFETIME_SECONDS = "database.pool.maxLifetimeSeconds";
    public static final String DB_POOL_KEEPALIVE_TIME_SECONDS = "database.pool.keepaliveTimeSeconds";
    public static final String DB_POOL_VALIDATION_TIMEOUT_SECONDS = "database.pool.validationTimeoutSeconds";
}
