package net.corda.rbac.schema

import net.corda.rbac.schema.RbacKeys.ALIAS_REGEX
import net.corda.rbac.schema.RbacKeys.CERTIFICATE_USAGE_REGEX
import net.corda.rbac.schema.RbacKeys.CLIENT_REQ_REGEX
import net.corda.rbac.schema.RbacKeys.CPI_FILE_CHECKSUM_REGEX
import net.corda.rbac.schema.RbacKeys.FLOW_NAME_REGEX
import net.corda.rbac.schema.RbacKeys.FLOW_STATE_REGEX
import net.corda.rbac.schema.RbacKeys.HSM_CATEGORY_REGEX
import net.corda.rbac.schema.RbacKeys.KEY_ID_REGEX
import net.corda.rbac.schema.RbacKeys.KEY_SCHEME_REGEX
import net.corda.rbac.schema.RbacKeys.OPTIONAL_QUERY_PARAMETER
import net.corda.rbac.schema.RbacKeys.PROPERTY_KEY_VALUE_REGEX
import net.corda.rbac.schema.RbacKeys.TENANT_ID_REGEX
import net.corda.rbac.schema.RbacKeys.USER_REGEX
import net.corda.rbac.schema.RbacKeys.USER_URL_REGEX
import net.corda.rbac.schema.RbacKeys.UUID_REGEX
import net.corda.rbac.schema.RbacKeys.VNODE_SHORT_HASH_REGEX
import net.corda.rbac.schema.RbacKeys.VNODE_STATE_REGEX
import net.corda.rbac.schema.RbacKeys.VNODE_STATUS_REQ_REGEX
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

class TestPatternsMatch {

    private fun wildcardMatch(input: String, regex: String): Boolean {
        return input.matches(regex.toRegex())
    }

    @Test
    fun testUUID() {
        val validUUIDString = UUID.randomUUID().toString()

        assertTrue(wildcardMatch(validUUIDString, UUID_REGEX))
        assertFalse(wildcardMatch("invalid", UUID_REGEX))
    }

    @Test
    fun testVNodeShortHash() {
        val validShortHashId = "ABCDEF123456"

        assertTrue(wildcardMatch(validShortHashId, VNODE_SHORT_HASH_REGEX))
        assertFalse(wildcardMatch("invalid", VNODE_SHORT_HASH_REGEX))
    }

    @Test
    fun testVNodeState() {
        val valid = "IN_MAINTENANCE"

        assertTrue(wildcardMatch(valid, VNODE_STATE_REGEX))
        assertFalse(wildcardMatch("inv@lid", VNODE_STATE_REGEX))
    }

    @Test
    fun testVNodeStatusReqId() {
        listOf("ABCDEF123456ABCDEF123456ABCDEF123456", "ABCDEF123456").forEach { validVNodeStatusReqId ->
            assertTrue(wildcardMatch(validVNodeStatusReqId, VNODE_STATUS_REQ_REGEX)) { "Failed for $validVNodeStatusReqId" }
        }

        assertFalse(wildcardMatch("ABCDEF123456ABCDEF123456", VNODE_STATUS_REQ_REGEX))
        assertFalse(wildcardMatch("invalid", VNODE_STATUS_REQ_REGEX))
    }

    @Test
    fun testUser() {
        listOf("joe.bloggs@company_1.com", "plainUser").forEach { validUserName ->
            assertTrue(wildcardMatch(validUserName, USER_REGEX)) { "Failed for $validUserName" }
        }
        assertFalse(wildcardMatch("joe/bloggs", USER_REGEX))
        assertFalse(wildcardMatch("0", USER_REGEX))
    }

    @Test
    fun testUserInURL() {
        listOf("joe.bloggs%40company_1.com", "plainUser").forEach { validUserName ->
            assertTrue(wildcardMatch(validUserName, USER_URL_REGEX)) { "Failed for $validUserName" }
        }

        assertFalse(wildcardMatch("joe/bloggs", USER_URL_REGEX))
        assertFalse(wildcardMatch("0", USER_URL_REGEX))
    }

    @Test
    fun testPropertyKeyValue() {
        val validPropertyKey = "My_Property-key.1"

        assertTrue(wildcardMatch(validPropertyKey, PROPERTY_KEY_VALUE_REGEX))
        assertFalse(wildcardMatch("Invalid@", PROPERTY_KEY_VALUE_REGEX))
    }

    @Test
    fun testCPIFileChecksum() {
        val validChecksum = "ABCDEF123456"

        assertTrue(wildcardMatch(validChecksum, CPI_FILE_CHECKSUM_REGEX))
        assertFalse(wildcardMatch("invalid", CPI_FILE_CHECKSUM_REGEX))
    }

    @Test
    fun testClientRequestId() {
        val validClientRequestId = "My-Request_Id.1"

        assertTrue(wildcardMatch(validClientRequestId, CLIENT_REQ_REGEX))
        assertFalse(wildcardMatch("client/request", CLIENT_REQ_REGEX))
        assertFalse(wildcardMatch("#client@request", CLIENT_REQ_REGEX))
    }

    @Test
    fun testFlowName() {
        val validFlowName = "com.company.MyFlow_1$2"

        assertTrue(wildcardMatch(validFlowName, FLOW_NAME_REGEX))
        assertFalse(wildcardMatch("flow/name", FLOW_NAME_REGEX))
        assertFalse(wildcardMatch("#flow@name", FLOW_NAME_REGEX))
    }

    @Test
    fun testFlowState() {
        val validFlowState = "ACTIVE"

        assertTrue(wildcardMatch(validFlowState, FLOW_STATE_REGEX))
        assertFalse(wildcardMatch("inv@lid", FLOW_STATE_REGEX))
    }

    @Test
    fun testCertificateUsage() {
        listOf("p2p-tls", "p2p-session", "code-signer").forEach { validCertificateUsage ->
            assertTrue(wildcardMatch(validCertificateUsage, CERTIFICATE_USAGE_REGEX)) { "Failed for $validCertificateUsage" }
        }

        assertFalse(wildcardMatch("invalid", CERTIFICATE_USAGE_REGEX))
    }

    @Test
    fun testTenantId() {
        listOf("ABCDEF123456", "p2p", "ABCDEF123456?query=abcdef123456").forEach { validTenantId ->
            assertTrue(wildcardMatch(validTenantId, TENANT_ID_REGEX + OPTIONAL_QUERY_PARAMETER)) { "Failed for $validTenantId" }
        }

        assertFalse(wildcardMatch("invalid", TENANT_ID_REGEX))
    }

    @Test
    fun testKeyId() {
        val validKeyId = "ABCDEF123456"

        assertTrue(wildcardMatch(validKeyId, KEY_ID_REGEX))
        assertFalse(wildcardMatch("invalid", KEY_ID_REGEX))
    }

    @Test
    fun testHSMCategory() {
        listOf("ACCOUNTS", "CI", "LEDGER", "NOTARY", "PRE_AUTH", "SESSION_INIT", "TLS", "JWT_KEY").forEach { validCategory ->
            assertTrue(wildcardMatch(validCategory, HSM_CATEGORY_REGEX)) { "Failed for $validCategory" }
        }

        assertFalse(wildcardMatch("invalid", HSM_CATEGORY_REGEX))
    }

    @Test
    fun testAlias() {
        val validAlias = "myAlias_1"

        assertTrue(wildcardMatch(validAlias, ALIAS_REGEX))
        assertFalse(wildcardMatch("inv@lid", ALIAS_REGEX))
    }

    @Test
    fun testKeyScheme() {
        listOf(
            "CORDA.RSA",
            "CORDA.ECDSA.SECP256K1",
            "CORDA.ECDSA.SECP256R1",
            "CORDA.EDDSA.ED25519",
            "CORDA.X25519",
            "CORDA.SM2",
            "CORDA.GOST3410.GOST3411",
            "CORDA.SPHINCS-256"
        ).forEach { validKeyScheme ->
            assertTrue(wildcardMatch(validKeyScheme, KEY_SCHEME_REGEX)) { "Failed for $validKeyScheme" }
        }

        assertFalse(wildcardMatch("invalid", KEY_SCHEME_REGEX))
    }
}