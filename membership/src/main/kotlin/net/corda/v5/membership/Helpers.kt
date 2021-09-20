package net.corda.v5.membership

import net.corda.data.WireKeyValuePair
import net.corda.impl.application.node.DEFAULT_MEMBER_GROUP_ID
import net.corda.impl.application.node.EndpointInfoImpl
import net.corda.internal.application.node.EndpointInfo

val endpoints = listOf(EndpointInfoImpl("https://localhost:10000", 1), EndpointInfoImpl("https://google.com", 10))

const val URL_KEY = "corda.endpoints.%s.connectionURL"
const val PROTOCOL_VERSION = "corda.endpoints.%s.protocolVersion"

private fun convertEndpoints(endpoints: List<EndpointInfo>): List<Pair<String, String>> {
    val result = mutableListOf<Pair<String, String>>()
    for(i in endpoints.indices) {
        result.add(Pair(String.format(URL_KEY, i), endpoints[i].url))
        result.add(Pair(String.format(PROTOCOL_VERSION, i), endpoints[i].protocolVersion.toString()))
    }
    return result
}

const val IDENTITY_KEYS_KEY = "corda.identityKeys.%s"

private fun convertPublicKeys(keyEncodingService: DummyKeyEncodingService): List<Pair<String, String>> {
    val result = mutableListOf<Pair<String, String>>()
    for(i in 0 until keyEncodingService.pubKeys.keys.size ) {
        result.add(Pair(String.format(IDENTITY_KEYS_KEY, i), keyEncodingService.encodeAsString(keyEncodingService.pubKeys.keys.elementAt(i))))
    }
    return result
}

fun createExampleMemberInfo(principal : String = "O=Alice,L=London,C=GB", keyEncodingService: DummyKeyEncodingService) : MemberInfoPoC =
    MemberInfoPoCImpl(
        MemberContextPoCImpl(
            // important to create it as ordered map, because later on we will do a validation of key order!
            sortedMapOf(
                MemberInfoExtensionPoC.PARTY_NAME to principal,
                MemberInfoExtensionPoC.PARTY_KEY to keyEncodingService.encodeAsString(keyEncodingService.pubKeys.keys.first()),
                MemberInfoExtensionPoC.GROUP_ID to DEFAULT_MEMBER_GROUP_ID,
                *convertPublicKeys(keyEncodingService).toTypedArray(),
                *convertEndpoints(endpoints).toTypedArray(),
                MemberInfoExtensionPoC.SOFTWARE_VERSION to "5.0.0",
                MemberInfoExtensionPoC.PLATFORM_VERSION to "10",
                MemberInfoExtensionPoC.SERIAL to "1"
            )
        ), MemberContextPoCImpl(sortedMapOf("corda.status" to "xxx")), keyEncodingService
    )

fun convertToMemberInfoPoC(memberContextPoC: MemberContextPoC, mgmContextPoC: MemberContextPoC, keyEncodingService: DummyKeyEncodingService): MemberInfoPoC {
    return MemberInfoPoCImpl(memberContextPoC, mgmContextPoC, keyEncodingService)
}

fun validateKeyOrder(original: List<WireKeyValuePair>) {
    val originalKeys = original.map { it.key }
    val sortedKeys = originalKeys.sortedBy { it }
    if (originalKeys != sortedKeys) {
        throw IllegalArgumentException("The input was manipulated as it's expected to be ordered by first element in pairs.")
    }
}

fun List<WireKeyValuePair>.convertToContext(): MemberContextPoC {
    // before returning the ordered map, do the validation of ordering
    // (to avoid malicious attacks where extra data is attached to the end of the context)
    validateKeyOrder(this)
    return MemberContextPoCImpl(this.map { it.key to it.value }.toMap().toSortedMap())
}