package net.corda.schema.configuration;

public final class StateManagerConfig {
    private StateManagerConfig() {
    }

    public static final String STATE_MANAGER = "stateManager";
    public static final String TYPE = STATE_MANAGER + ".type";

    /**
     * Configuration for connecting to the state manager using database as underlying state storage.
     */
    public static final String DB_USER = STATE_MANAGER + ".database.user";
    public static final String DB_PASS = STATE_MANAGER + ".database.pass";
    public static final String JDBC_URL = STATE_MANAGER + ".database.jdbc.url";
}
