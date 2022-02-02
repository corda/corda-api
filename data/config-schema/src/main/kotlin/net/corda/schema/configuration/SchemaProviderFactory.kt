package net.corda.schema.configuration

class SchemaProviderFactory {

    fun getSchemaProvider() : SchemaProvider {
        return SchemaProviderImpl()
    }
}