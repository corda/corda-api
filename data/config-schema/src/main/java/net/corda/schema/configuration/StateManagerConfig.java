package net.corda.schema.configuration;

public final class StateManagerConfig {
    private StateManagerConfig() {
    }

    public static final String TYPE = "type";

    /**
     * Configuration for connecting to the state manager using database as underlying state storage.
     */
    public static final String DB_USER = "database.user";
    public static final String DB_PASS = "database.pass";
    public static final String JDBC_URL = "database.jdbc.url";
}
