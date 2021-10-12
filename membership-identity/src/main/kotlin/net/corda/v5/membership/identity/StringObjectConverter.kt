package net.corda.v5.membership.identity

interface StringObjectConverter<T> {
    fun convert(stringProperties: KeyValueStore, clazz: Class<out T>): T
}