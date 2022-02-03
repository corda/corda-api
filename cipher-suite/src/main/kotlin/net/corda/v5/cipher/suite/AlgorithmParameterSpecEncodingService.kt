package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.SerializedAlgorithmParameterSpec
import java.security.spec.AlgorithmParameterSpec

/**
 * Encoding service which can encode and decode signature parameters.
 */
interface AlgorithmParameterSpecEncodingService {
    /**
     * Serialize the given parameters into the byte array.
     */
    fun serialize(params: AlgorithmParameterSpec): SerializedAlgorithmParameterSpec

    /**
     * Deserialize the given byte array into corresponding parameters.
     */
    fun deserialize(params: SerializedAlgorithmParameterSpec): AlgorithmParameterSpec
}