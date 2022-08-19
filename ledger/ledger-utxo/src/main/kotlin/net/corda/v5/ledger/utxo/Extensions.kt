package net.corda.v5.ledger.utxo

import java.util.function.Predicate

fun <T> Collection<T>.elementAtOrThrow(index: Int, collectionName: String): T {
    if (index < 0 || index > size - 1) throw IndexOutOfBoundsException("The $collectionName list does not contain an element at the specified index: $index. The index is out of bounds.")
    return elementAt(index)
}

fun <T> Collection<T>.singleOrThrow(collectionName: String): T {
    if (isEmpty()) throw NoSuchElementException("The $collectionName list contains zero elements.")
    if (size > 1) throw IllegalArgumentException("The $collectionName list contains more than one element.")
    return single()
}

fun <T> Collection<T>.singleOrThrow(predicate: Predicate<T>, collectionName: String): T {
    val selection = filter { predicate.test(it) }
    if (selection.isEmpty()) throw NoSuchElementException("The $collectionName list contains zero elements matching the specified predicate.")
    if (selection.size > 1) throw IllegalArgumentException("The $collectionName list contains more than one element matching the specified predicate.")
    return single()
}