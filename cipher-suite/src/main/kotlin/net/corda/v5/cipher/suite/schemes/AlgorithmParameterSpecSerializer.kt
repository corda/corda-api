package net.corda.v5.cipher.suite.schemes

import java.security.spec.AlgorithmParameterSpec

/**
 * Custom serializer for [AlgorithmParameterSpec] as most of the implementations are not serializable and throw
 * an exception when trying to use default constructors so the JSON cannot be used as well.
 */
interface AlgorithmParameterSpecSerializer<T : AlgorithmParameterSpec> {
    /**
     * Serialize the given parameters into the byte array.
     */
    fun serialize(params: T): ByteArray

    /**
     * Deserialize the given byte array into corresponding parameters.
     */
    fun deserialize(bytes: ByteArray): T
}