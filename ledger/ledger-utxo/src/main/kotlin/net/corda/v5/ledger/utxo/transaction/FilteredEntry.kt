package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.DoNotImplement

/**
 * This class represents a value in a component group of a [UtxoFilteredTransaction]. The index has to
 * be given explicitly, as elements might be filtered out, so the position in the list is not reliable
 */
@DoNotImplement
interface FilteredEntry<T> {

    /**
     * @param index The index of this value in its component group in the original transaction
     */
    val index: Int

    /**
     * @param value The value from the original transaction
     */
    val value: T
}
