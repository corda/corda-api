package net.corda.schema.configuration

/** Default configuration values for associated [ConfigKeys] */
class ConfigDefaults {
    companion object {
        const val JDBC_DRIVER = "org.postgresql.Driver"
        const val DB_POOL_MAX_SIZE = 10
    }
}
