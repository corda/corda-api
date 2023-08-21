package net.corda.schema.configuration;

public final class StateManagerConfig {
    private StateManagerConfig() {
    }

    /**
     * Configuration for connecting to the state manager using database as underlying state storage.
     */
    public static final String TYPE = "stateManager.stateManagerType";
    public static final String DB_PASS = "stateManager.database.pass";
    public static final String DB_USER = "stateManager.database.user";
    public static final String JDBC_DRIVER = "stateManager.database.jdbc.driver";
    public static final String JDBC_DRIVER_DIRECTORY = "stateManager.database.jdbc.directory";
    public static final String JDBC_URL = "stateManager.database.jdbc.url";
}
