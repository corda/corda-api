package net.corda.v5.application.uniqueness.client

import net.corda.v5.application.uniqueness.result.UniquenessCheckResult
import net.corda.v5.base.annotations.DoNotImplement
import java.time.Instant
import java.util.concurrent.Future

/**
 * This service is used to queue a request for uniqueness checking.
 * The request will be processed asynchronously and a [Future] will
 * be returned that can be used to check the outcome.
 *
 * This service can be injected to either flows or other services.
 *
 * The actual request processing depends on which uniqueness service
 * is invoked from the client.
 *
 * The API contains only one function that is used to queue up a request.
 */
@DoNotImplement
interface UniquenessCheckerClientService {

    /**
     * This function is used to queue up a request for the client service,
     * which will process it in an async manner, and return a [Future].
     *
     * @param txId The ID of the transaction that needs processing
     *
     * @param inputs A list of the input state refs that belongs
     * o the given transaction, in a <TX_ID>:<STATE_NUM> format.
     * This list might be empty if the given transaction is only
     * doing issuance.
     *
     * @param references A list of the reference state refs that belongs
     * o the given transaction, in a <TX_ID>:<STATE_NUM> format.
     * This list might be empty.
     *
     * @param numberOfOutputStates The number of output states the
     * given transaction has.
     *
     * @param timeWindowLowerBound The earliest date/time from which
     * the transaction is considered valid. This is an optional parameter.
     *
     * @param timeWindowUpperBound The latest date/time until the transaction
     * is considered valid.
     */
    fun commitRequest(
        txId: String,
        inputs: List<String>,
        references: List<String>,
        numberOfOutputStates: Int,
        timeWindowLowerBound: Instant?,
        timeWindowUpperBound: Instant,
    ): Future<UniquenessCheckResult>
}
