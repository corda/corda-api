package net.corda.v5.application.uniqueness.client

import net.corda.data.uniqueness.UniquenessCheckResponse
import net.corda.v5.base.annotations.DoNotImplement
import java.time.Instant
import java.util.concurrent.Future

/**
 * This service is used to request uniqueness checking. The request will be processed asynchronously
 * and a [Future] will be returned that can be used to check the outcome. This service can be injected
 * to either flows or other services.
 */
@DoNotImplement
interface UniquenessCheckerClientService {

    /**
     * @param txId The ID of the transaction that needs processing
     *
     * @param inputStates A list of the input state refs that belongs to the given transaction in a
     * <TX_ID>:<STATE_NUM> format. This list might be empty if the given transaction is only doing
     * issuance.
     *
     * @param referenceStates A list of the reference state refs that belongs to the given transaction,
     * in a <TX_ID>:<STATE_NUM> format. This list might be empty.
     *
     * @param numOutputStates The number of output states the given transaction has.
     *
     * @param timeWindowLowerBound The earliest date/time from which the transaction is considered valid.
     * This is an optional parameter.
     *
     * @param timeWindowUpperBound The latest date/time until the transaction is considered valid.
     */
    @Suppress("LongParameterList")
    fun requestUniquenessCheck(
        txId: String,
        inputStates: List<String>,
        referenceStates: List<String>,
        numOutputStates: Int,
        timeWindowLowerBound: Instant?,
        timeWindowUpperBound: Instant,
    ): Future<UniquenessCheckResponse>
}
