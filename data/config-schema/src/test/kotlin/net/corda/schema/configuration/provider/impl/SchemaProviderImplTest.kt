package net.corda.schema.configuration.provider.impl

import net.corda.schema.configuration.ConfigKeys.CRYPTO_CONFIG
import net.corda.schema.configuration.ConfigKeys.DB_CONFIG
import net.corda.schema.configuration.ConfigKeys.FLOW_CONFIG
import net.corda.schema.configuration.ConfigKeys.IDENTITY_CONFIG
import net.corda.schema.configuration.ConfigKeys.MESSAGING_CONFIG
import net.corda.schema.configuration.ConfigKeys.P2P_CONFIG
import net.corda.schema.configuration.ConfigKeys.PLATFORM_CONFIG
import net.corda.schema.configuration.ConfigKeys.POLICY_CONFIG
import net.corda.schema.configuration.ConfigKeys.RPC_CONFIG
import net.corda.schema.configuration.ConfigKeys.SANDBOX_CONFIG
import net.corda.schema.configuration.ConfigKeys.SECRETS_CONFIG
import net.corda.schema.configuration.provider.SchemaProviderFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SchemaProviderImplTest {

    companion object {
        // All the top level config keys excluding the boot config, which is handled differently.
        private val CONFIG_KEYS = listOf(
            CRYPTO_CONFIG,
            DB_CONFIG,
            FLOW_CONFIG,
            IDENTITY_CONFIG,
            MESSAGING_CONFIG,
            P2P_CONFIG,
            PLATFORM_CONFIG,
            POLICY_CONFIG,
            RPC_CONFIG,
            SECRETS_CONFIG,
            SANDBOX_CONFIG
        )
        private val VERSIONS = listOf("1.0")

        // This is a bit dubious as it assumes that all keys will update schema version at the same time. Either this is
        // true in which case the file structure is wrong, or it is false in which case this function needs to change to
        // account for this. However, this is good enough to bootstrap the schema provider tests.
        @JvmStatic
        private fun schemaSources(): Stream<Arguments> {
            return CONFIG_KEYS.stream().flatMap { key ->
                VERSIONS.stream().map { version -> arguments(key, version) }
            }
        }
    }

    @ParameterizedTest(name = "schema provider fetches schema for top-level keys: key={0}, version={1}")
    @MethodSource("schemaSources")
    fun `schema provider fetches schema for top-level keys`(key: String, version: String) {
        val provider = SchemaProviderFactory.getSchemaProvider()
        val stream = provider.getSchema(key, version)
        stream.close()
    }
}