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
     * @property DELIMITER Represents the delimiter that is used to split the transaction hash and index.
     */
    companion object {
        const val DELIMITER = ':'

        /**
         * Parses the specified value into a [StateRef].
         *
         * @param value The value to parse.
         * @return Returns a [StateRef] representing the specified value.
         */
        @JvmStatic
        fun parse(value: String): StateRef {
            val transactionHash = value.substringBefore(DELIMITER)
            val index = value.substringAfterLast(DELIMITER)

            return StateRef(SecureHash.create(transactionHash), index.toInt())
        }
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    override fun toString(): String = "$transactionHash$DELIMITER$index"
}
