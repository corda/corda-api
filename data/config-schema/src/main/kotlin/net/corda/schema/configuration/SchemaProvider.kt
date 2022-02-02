package net.corda.schema.configuration

import java.net.URI

/**
 * Exposes the configuration JSON schema files in this module in a way that should work under OSGi and non-OSGi.
 *
 * Schema is provided on a per-top level messaging key basis. Alternatively, all schema may be retrieved.
 *
 */
interface SchemaProvider {

    fun getSchema(key: String) : URI

    fun getAllSchema() : List<URI>
}