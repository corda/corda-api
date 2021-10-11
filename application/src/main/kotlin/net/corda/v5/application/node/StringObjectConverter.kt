package net.corda.v5.application.node

interface StringObjectConverter<T> {
    fun convert(stringProperties: Map<String, String>, clazz: Class<out T>): T
}