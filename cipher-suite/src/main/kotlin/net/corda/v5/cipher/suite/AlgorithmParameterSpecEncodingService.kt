package net.corda.v5.cipher.suite

import java.security.spec.AlgorithmParameterSpec

/**
 * Encoding service which can encode and decode signature parameters.
 */
interface AlgorithmParameterSpecEncodingService {
    /**
     * Serialize the given parameters into the byte array.
     */
    fun serialize(params: AlgorithmParameterSpec): ByteArray

    /**
     * Deserialize the given byte array into corresponding parameters.
     */
    fun deserialize(bytes: ByteArray): AlgorithmParameterSpec
}