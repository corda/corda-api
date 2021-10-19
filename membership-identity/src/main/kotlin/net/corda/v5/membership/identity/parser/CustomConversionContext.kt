package net.corda.v5.membership.identity.parser

import net.corda.v5.membership.identity.KeyValueStore

/**
 * [ConversionContext] for complex types (such as Party, EndpointInfo, etc).
 *
 * @property store The [KeyValueStore] containing all keys and their values.
 * @property key The key we are looking for in the store.
 * @property converter The converter we need to use for the parsing.
 */
class CustomConversionContext(
    store: KeyValueStore,
    storeClass: Class<out KeyValueStore>,
    key: String,
    val converter: ObjectConverter,
) : ConversionContext(store, storeClass, key)