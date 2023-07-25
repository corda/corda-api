package net.corda.schema.configuration;

public final class EvmConfig {
    private EvmConfig() {
    }

    public static final String PROCESSING_MAX_RETRY_ATTEMPTS = "processing.maxRetryAttempts";
    public static final String PROCESSING_MAX_RETRY_WINDOW_DURATION = "processing.maxRetryWindowDuration";
    public static final String PROCESSING_MAX_RETRY_DELAY = "processing.maxRetryDelay";
    public static final String PROCESSING_THREAD_POOL_SIZE = "processing.threadPoolSize";
}
