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
    public static final String DB_POOL_IDLE_TIMEOUT = "database.pool.idleTimeout";
    public static final String DB_POOL_MAX_LIFETIME = "database.pool.maxLifetime";
    public static final String DB_POOL_KEEPALIVE_TIME = "database.pool.keepaliveTime";
    public static final String DB_POOL_VALIDATION_TIMEOUT = "database.pool.validationTimeout";
}
