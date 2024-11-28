package net.corda.schema.configuration;

public final class LedgerConfig {
    private LedgerConfig() {
    }

    public static final String UTXO_TOKEN_MIN_DELAY_BEFORE_NEXT_FULL_SYNC = "tokens.minDelayBeforeNextFullSync";
    public static final String UTXO_TOKEN_MIN_DELAY_BEFORE_NEXT_PERIODIC_SYNC = "tokens.minDelayBeforeNextPeriodicSync";
    public static final String UTXO_TOKEN_FULL_SYNC_BLOCK_SIZE = "tokens.fullSyncBlockSize";
    public static final String UTXO_TOKEN_PERIODIC_CHECK_BLOCK_SIZE = "tokens.periodCheckBlockSize";
    public static final String UTXO_TOKEN_SEND_WAKEUP_MAX_RETRY_ATTEMPTS = "tokens.sendWakeUpMaxRetryAttempts";
    public static final String UTXO_TOKEN_SEND_WAKEUP_MAX_RETRY_DELAY = "tokens.sendWakeUpMaxRetryDelay";
    public static final String UTXO_TOKEN_CACHED_TOKEN_PAGE_SIZE = "tokens.cachedTokenPageSize";
    public static final String UTXO_TOKEN_CLAIM_TIMEOUT_SECONDS = "tokens.claimTimeoutSeconds";
    public static final String UTXO_TOKEN_CACHE_EXPIRY_PERIOD_MILLISECONDS = "tokens.tokenCacheExpiryPeriodMilliseconds";
    public static final String UTXO_TOKEN_MIN_DB_BACKOFF_PERIOD_MILLISECONDS = "tokens.dbBackoffMinPeriodMilliseconds";
    public static final String UTXO_TOKEN_MAX_DB_BACKOFF_PERIOD_MILLISECONDS = "tokens.dbBackoffMaxPeriodMilliseconds";
    public static final String UTXO_BACKCHAIN_BATCH_SIZE = "backchain.batchSize";
    public static final String UTXO_LEDGER_REPAIR_RUNTIME_DURATION = "repair.runtimeDuration";
    public static final String UTXO_LEDGER_REPAIR_FROM_DURATION = "repair.fromDuration";
    public static final String UTXO_LEDGER_REPAIR_UNTIL_DURATION = "repair.untilDuration";
}
