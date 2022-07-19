package net.corda.v5.application.persistence

/**
 * Request object for making Query requests on the Persistence Service.
 *
 * @param queryName the name of the named query registered in the persistence context.
 * @param namedParameters the named parameters to be set in the named query.
 */
data class PersistenceQueryRequest(
    /**
     * The name of the named query registered in the persistence context.
     */
    val queryName: String,
    /**
     * The named parameters to be set in the named query.
     */
    val namedParameters: Map<String, Any>,
    /**
     * The index of the first result in the query to return.
     * Default is 0.
     */
    val offset: Int,
    /**
     * The maximum number of results to return.
     * Default is "all records".
     */
    val limit: Int
) {
    /**
     * Request object for making query requests on the Persistence Service without offset and limit values.
     *
     * @param queryName the name of the named query registered in the persistence context.
     * @param namedParameters the named parameters to be set in the named query.
     */
    constructor(queryName: String, namedParameters: Map<String, Any>) :
        this(queryName, namedParameters, 0, Int.MAX_VALUE)

    class Builder(private val queryName: String, private val namedParameters: Map<String, Any>) {
        private var offset: Int = 0
        private var limit: Int = Int.MAX_VALUE

        fun build() = PersistenceQueryRequest(queryName, namedParameters, offset, limit)

        fun withOffset(offset: Int): Builder = apply { this.offset = offset }

        fun withLimit(limit: Int): Builder = apply { this.limit = limit }
    }
}
