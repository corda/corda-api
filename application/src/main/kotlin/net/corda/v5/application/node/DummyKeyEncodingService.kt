package net.corda.v5.application.node

import net.corda.v5.crypto.internal.Crypto
import java.security.PublicKey

interface KeyEncodingService {
    val pubKeys : Map<PublicKey,String>

    fun encodeAsString(value: PublicKey): String =
        pubKeys[value]!!

    fun decodePublicKey(value: String): PublicKey =
        pubKeys.entries.first { it.value == value }.key
}


@Suppress("EmptyDefaultConstructor")
class DummyKeyEncodingService(): KeyEncodingService {
    private val keys = listOf<PublicKey>(Crypto.generateKeyPair().public, Crypto.generateKeyPair().public)
    override val pubKeys : Map<PublicKey,String> = mapOf(
        keys[0] to "---PEM1---",
        keys[1] to "---PEM2---")

}