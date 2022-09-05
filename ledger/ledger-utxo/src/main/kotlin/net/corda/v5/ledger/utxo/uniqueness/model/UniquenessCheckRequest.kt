package net.corda.v5.ledger.utxo.uniqueness.model

import net.corda.v5.crypto.SecureHash
import java.time.Instant

/**
 * A representation of a uniqueness check request used by the uniqueness checker and the backing store.
 *
 * TODO This could be internal in runtime-os however, it will require some OSGi error solving
 */
interface UniquenessCheckRequest {
    val txId: SecureHash
    val rawTxId: String
    val inputStates: List<UniquenessCheckStateRef>
    val referenceStates: List<UniquenessCheckStateRef>
    val numOutputStates: Int
    val timeWindowLowerBound: Instant?
    val timeWindowUpperBound: Instant
}