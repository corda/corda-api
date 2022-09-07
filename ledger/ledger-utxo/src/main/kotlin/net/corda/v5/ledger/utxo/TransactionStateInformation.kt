package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.ledger.common.Party

/**
 * Represents information that applies to a transaction state.
 *
 * @constructor Creates a new instance of the [TransactionStateInformation] data class.
 * @property contractId The class name of the [Contract] associated with the transaction state.
 * @property notary The [Party] of the notary associated with the transaction state.
 * @property encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
 * @property constraint The [CpkConstraint] associated with the transaction state.
 */
@CordaSerializable
data class TransactionStateInformation(val contractId: String, val notary: Party, val encumbrance: Int?, val constraint: CpkConstraint) {

    /**
     * Creates a new instance of the [TransactionStateInformation] data class.
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param notary The [Party] of the notary associated with the transaction state.
     */
    constructor(contractId: String, notary: Party) :
            this(contractId, notary, null, CpkConstraint.AUTOMATIC_PLACEHOLDER)

    /**
     * Creates a new instance of the [TransactionStateInformation] data class.
     *
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param notary The [Party] of the notary associated with the transaction state.
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     */
    constructor(contractId: String, notary: Party, encumbrance: Int) :
            this(contractId, notary, encumbrance, CpkConstraint.AUTOMATIC_PLACEHOLDER)

    /**
     * Creates a new instance of the [TransactionStateInformation] data class.
     *
     * @param contractId The class name of the [Contract] associated with the transaction state.
     * @param notary The [Party] of the notary associated with the transaction state.
     * @param constraint The [CpkConstraint] associated with the transaction state.
     */
    constructor(contractId: String, notary: Party, constraint: CpkConstraint) : this(contractId, notary, null, constraint)
}
