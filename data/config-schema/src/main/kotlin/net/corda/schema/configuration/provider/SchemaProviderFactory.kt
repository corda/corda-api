package net.corda.schema.configuration.provider

import net.corda.schema.configuration.provider.impl.SchemaProviderImpl

object SchemaProviderFactory {

    fun getSchemaProvider() : SchemaProvider {
        return SchemaProviderImpl()
    }
}