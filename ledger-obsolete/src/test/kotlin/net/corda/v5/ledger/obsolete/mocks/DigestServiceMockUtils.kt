@file:JvmName("DigestServiceMockUtils")

package net.corda.v5.ledger.obsolete.mocks

import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.DigestService
import net.corda.v5.crypto.SecureHash
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


private val hashConstants: ConcurrentMap<String, HashConstants> = ConcurrentHashMap()

fun DigestService.getZeroHash(digestAlgorithmName: DigestAlgorithmName): SecureHash {
    return getConstantsFor(digestAlgorithmName).zero
}

private fun DigestService.getConstantsFor(digestAlgorithmName: DigestAlgorithmName): HashConstants {
    val algorithm = digestAlgorithmName.name
    return hashConstants.getOrPut(algorithm) {
        val digestLength = digestLength(digestAlgorithmName)
        HashConstants(
            zero = SecureHash(algorithm, ByteArray(digestLength) { 0.toByte() })
        )
    }
}

private class HashConstants(val zero: SecureHash)