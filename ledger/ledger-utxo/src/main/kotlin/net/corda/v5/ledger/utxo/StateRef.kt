package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.SecureHash

/**
 * Represents a reference to a state.
 *
 * @property transactionHash The hash of the transaction in which the referenced state was created.
 * @property index The index of the state in the transaction's outputs in which the referenced state was created.
 */
@CordaSerializable
data class StateRef(val transactionHash: SecureHash, val index: Int) {

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    override fun toString(): String = "$transactionHash:$index"
}
