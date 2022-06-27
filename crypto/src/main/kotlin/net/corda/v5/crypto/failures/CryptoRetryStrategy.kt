package net.corda.v5.crypto.failures

/**
 * Strategy to handle transient faults by retrying.
 */
fun interface CryptoRetryStrategy {
    companion object {
        /**
         * Creates default backoff array for linear retry strategy of 3 max attempts
         * with 200 milliseconds wait time in between
         */
        @JvmStatic
        fun createLinearBackoff(): LongArray =
            createBackoff(3, 200L)

        /**
         * Creates default backoff array for exponential retry strategy of 6 max attempts
         * with 1s, 2s, 4s, 8s and 16s wait time in between
         */
        @JvmStatic
        fun createExponentialBackoff(): LongArray =
            createExponentialBackoff(6, 1000L)

        /**
         * Creates backoff array for exponential retry strategy.
         */
        @JvmStatic
        fun createExponentialBackoff(maxAttempts: Int, initialBackoff: Long): LongArray {
            var next = initialBackoff
            return LongArray(maxAttempts - 1) {
                val current = next
                next *= 2
                current
            }
        }

        /**
         * Creates backoff array for linear retry strategy. If the number of attempts is less than max attempts then
         * the last values is repeated.
         */
        @JvmStatic
        fun createBackoff(maxAttempts: Int, vararg backoff: Long): LongArray {
            if(backoff.isEmpty()) {
                return LongArray(0)
            }
            return LongArray(maxAttempts - 1) {
                if(it < backoff.size) {
                    backoff[it]
                } else {
                    backoff[backoff.size - 1]
                }
            }
        }
    }

    /**
     * Returns the next wait period in milliseconds for the given attempt and current waiting period.
     * The return value of -1 would mean that the operations is deemed unrecoverable so no further attempts to retry.
     *
     * @param attempt - the current attempt which failed, starts at 1
     * @param currentBackoffMillis - the current backoff time
     */
    fun getBackoff(attempt: Int, currentBackoffMillis: Long): Long
}