package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

/**
 * Represents information that applies to a transaction state.
 *
 * @constructor Creates a new instance of the [TransactionStateInformation] data class.
 * @property contract The class name of the contract associated with the transaction state.
 * @property notary The [PublicKey] of the notary associated with the transaction state.
 * @property encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
 * @property constraint The [CpkConstraint] associated with the transaction state.
 */
@CordaSerializable
data class TransactionStateInformation(val contract: String, val notary: PublicKey, val encumbrance: Int?, val constraint: CpkConstraint) {

    /**
     * Creates a new instance of the [TransactionStateInformation] data class.
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     */
    constructor(contract: String, notary: PublicKey) :
            this(contract, notary, null, CpkConstraint.automaticPlaceholderCpkConstraint)

    /**
     * Creates a new instance of the [TransactionStateInformation] data class.
     *
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param encumbrance The index of an associated, encumbered state, or null if no encumbrance applies to the associated transaction state.
     */
    constructor(contract: String, notary: PublicKey, encumbrance: Int) :
            this(contract, notary, encumbrance, CpkConstraint.automaticPlaceholderCpkConstraint)

    /**
     * Creates a new instance of the [TransactionStateInformation] data class.
     *
     * @param contract The class name of the contract associated with the transaction state.
     * @param notary The [PublicKey] of the notary associated with the transaction state.
     * @param constraint The [CpkConstraint] associated with the transaction state.
     */
    constructor(contract: String, notary: PublicKey, constraint: CpkConstraint) :
            this(contract, notary, null, constraint)
}
