package net.corda.v5.application.persistence

/**
 * Used to build a Query.
 *
 * @param R the type of the results.
 */
interface Query<R> {
    /**
     * Execute the [Query]
     *
     * @return list of entities found. Empty list if none were found.
     */
    fun execute(): List<R>
}