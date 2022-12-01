package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.DoNotImplement

/**
 * A container for retrieving information from a [UtxoFilteredTransaction].
 *
 * The underlying data in the original transaction is a component group, i.e. a list of entries in a Merkle tree
 * structure (this is what allows us to filter out bits and still calculate the id). The component group can either
 * be
 * * Completely filtered out - we do not get any inforamtion about this data set
 * * A size-only merkle proof - in this case, we can only retrieve the size of the original list
 * * An audit proof - in this case, we get access to some or all of the entries of the original list.
 */
sealed interface UtxoFilteredData<T> {

    /**
     * Marker interface for a completely filtered out component group. No further information is available
     */
    @DoNotImplement
    interface UtxoFilteredDataRemoved<T> : UtxoFilteredData<T>

    /**
     * Interface for a size only proof. This will only tell us how many entries there were originally, but not their
     * content.
     */
    @DoNotImplement
    interface UtxoFilteredDataSizeOnly<T> : UtxoFilteredData<T> {
        /**
         * @param size The size of the component group in the original transaction
         */
        val size: Int
    }

    /**
     * Interface for an audit proof.
     *
     * This allows us to retrieve some or all of the original entries. The size entry
     * will be the size of the component group in the original transaction. The values will be a list of all entries
     * that we get access to - its size can be less than the size value. The [FilteredEntry] objects have the original
     * data and the original index of the entry.
     */
    @DoNotImplement
    interface UtxoFilteredDataAudit<T> : UtxoFilteredData<T> {

        /**
         * @param size The size of the component group in the original transaction
         */
        val size: Int

        /**
         * @param values: The list of the revealed entries
         */
        val values: List<FilteredEntry<T>>
    }
}