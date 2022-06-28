package net.corda.v5.base.exceptions

/**
 * Strategy to handle transient faults by retrying.
 */
fun interface BackoffStrategy {
    companion object {
        /**
         * Creates default backoff array for linear retry strategy of 3 max attempts
         * with 200 milliseconds wait time in between
         */
        @JvmStatic
        fun createLinearBackoff(): BackoffStrategy =
            createBackoff(3, listOf(200L))

        /**
         * Creates default backoff array for exponential retry strategy of 6 max attempts
         * with 1s, 2s, 4s, 8s and 16s wait time in between
         */
        @JvmStatic
        fun createExponentialBackoff(): BackoffStrategy =
            createExponentialBackoff(6, 1000L)

        /**
         * Creates backoff array for exponential retry strategy.
         */
        @JvmStatic
        fun createExponentialBackoff(maxAttempts: Int, initialBackoff: Long): BackoffStrategy = when {
            maxAttempts <= 1 -> Default(emptyArray())
            else -> {
                var next = initialBackoff
                Default(
                    Array(maxAttempts - 1) {
                        val current = next
                        next *= 2
                        current
                    }
                )
            }
        }

        /**
         * Creates backoff array for linear retry strategy. If the number of attempts is less than max attempts then
         * the last values is repeated.
         */
        @JvmStatic
        fun createBackoff(maxAttempts: Int, backoff: List<Long>): BackoffStrategy = when {
            maxAttempts <= 1 -> Default(emptyArray())
            backoff.isEmpty() -> createBackoff(maxAttempts, listOf(0L))
            else -> Default(
                    Array(maxAttempts - 1) {
                        if (it < backoff.size) {
                            backoff[it]
                        } else {
                            backoff[backoff.size - 1]
                        }
                    }
                )
        }
    }

    /**
     * Returns the next wait period in milliseconds for the given attempt and current waiting period.
     * The return value of -1 would mean that the operations is deemed unrecoverable so no further attempts to retry.
     *
     * @param attempt - the current attempt which failed, starts at 1
     */
    fun getBackoff(attempt: Int): Long

    /**
     * Default implementation of the [BackoffStrategy]
     *
     * @property backoff defines the wait times between each attempt, the number of max attempts is backoff size plus 1.
     */
    class Default(
        private val backoff: Array<Long>
    ) : BackoffStrategy {
        override fun getBackoff(attempt: Int): Long =
            if (attempt < 1 || attempt > backoff.size) {
                -1
            } else {
                backoff[attempt - 1]
            }
    }
}