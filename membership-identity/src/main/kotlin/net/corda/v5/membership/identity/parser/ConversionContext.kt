package net.corda.v5.membership.identity.parser

import net.corda.v5.membership.identity.KeyValueStore

/**
 * The context from which we want to do the conversion and parsing from.
 *
 * @property store The [KeyValueStore] containing all keys and their values.
 * @property storeClass The type of the store. Can be either [MemberContext] or [MGMContext].
 * @property key The key we are looking for in the store.
 */
open class ConversionContext(
    val store: KeyValueStore,
    val storeClass: Class<out KeyValueStore>,
    val key: String
) {
    fun value(): String? = store[key]
}