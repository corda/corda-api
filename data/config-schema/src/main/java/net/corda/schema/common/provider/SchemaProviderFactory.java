package net.corda.schema.common.provider;

import net.corda.schema.configuration.provider.impl.SchemaProviderConfigImpl;
import net.corda.schema.cordapp.configuration.provider.impl.SchemaProviderCordappConfigImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Factory for schema providers.
 */
public final class SchemaProviderFactory {
    private SchemaProviderFactory() {
    }

    /**
     * Create a new schema provider.
     *
     * @return The new schema provider.
     */
    @NotNull
    public static SchemaProvider getConfigSchemaProvider() {
        return new SchemaProviderConfigImpl();
    }

    /**
     * Create a new schema provider.
     *
     * @return The new schema provider.
     */
    @NotNull
    public static SchemaProvider getCordappConfigSchemaProvider() {
        return new SchemaProviderCordappConfigImpl();
    }
}
