package net.corda.schema.configuration;

public class VirtualNodeDatasourceConfig {
    private VirtualNodeDatasourceConfig() {
    }

    public static final String VNODE_DDL_POOL_CONFIG = "pool.ddl";
    public static final String VNODE_DML_POOL_CONFIG = "pool.dml";

    public static final String VNODE_POOL_MAX_SIZE = "max_size";
    public static final String VNODE_POOL_MIN_SIZE = "min_size";
    public static final String VNODE_POOL_IDLE_TIMEOUT_SECONDS = "idleTimeoutSeconds";
    public static final String VNODE_POOL_MAX_LIFETIME_SECONDS = "maxLifetimeSeconds";
    public static final String VNODE_POOL_KEEPALIVE_TIME_SECONDS = "keepaliveTimeSeconds";
    public static final String VNODE_VALIDATION_TIMEOUT_SECONDS = "validationTimeoutSeconds";
}
