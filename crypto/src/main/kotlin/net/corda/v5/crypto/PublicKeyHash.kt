package net.corda.v5.crypto

import net.corda.v5.base.types.toHexString
import java.security.PublicKey

fun PublicKey.calculateHash() = PublicKeyHash.calculate(this)

class PublicKeyHash private constructor(
    val value: String
) {
    companion object {
        fun parse(bytes: ByteArray): PublicKeyHash {
            require(bytes.size == 32) {
                "Input must be 32 bytes long for SHA-256 hash."
            }
            return PublicKeyHash(value = bytes.toHexString())
        }

        fun calculate(publicKey: PublicKey): PublicKeyHash =
            PublicKeyHash(value = publicKey.sha256Bytes().toHexString())
    }

    override fun hashCode() = value.hashCode()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is PublicKeyHash -> value == other.value
            is String -> value == other
            else -> false
        }
    }

    override fun toString() = value
}
