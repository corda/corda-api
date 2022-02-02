package net.corda.schema.configuration.validator

import org.junit.jupiter.api.Test
import org.osgi.framework.Bundle
import org.osgi.framework.FrameworkUtil

class SchemaValidatorTest {

    companion object {
        private const val MESSAGING_CONFIG = "messaging-config.json"
    }

    @Test
    fun `validate schema medeia`() {
        val json = loadData(MESSAGING_CONFIG)
        val validator = SchemaValidator()
        validator.validateJsonMedeia(json)
    }

    @Test
    fun `validate schema everit`() {
        val json = loadData(MESSAGING_CONFIG)
        val validator = SchemaValidator()
        validator.validateJsonEverit(json)
    }

    @Test
    fun `validate schema networknt`(){
        val json = loadData(MESSAGING_CONFIG)
        val validator = SchemaValidator()
        validator.validateJsonNetworkNt(json)
    }

    @Test
    fun `validate schema vertx`() {
        val json = loadData(MESSAGING_CONFIG)
        val validator = SchemaValidator()
        validator.validateJsonVertx(json)
    }

    private fun loadData(resource: String): String {
        val bundle: Bundle? = FrameworkUtil.getBundle(this::class.java)
        val url = bundle?.getResource(resource)
            ?: this::class.java.classLoader.getResource(resource) ?: throw RuntimeException()
        return url.readText()
    }
}