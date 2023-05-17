package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Arrays;

//TODO Investigate if typeName can be dropped

/**
 * Enum defining the possible primitive types for parameters.
 */
public enum ParameterTypeLabel {
    BOOLEAN("boolean", Boolean.class),
    STRING("string", String.class),
    DECIMAL("decimal", BigDecimal.class),
    UUID("uuid", java.util.UUID.class),
    TIMESTAMP("timestamp", ZonedDateTime.class),
    BYTES("bytes", ByteBuffer.class),
    JSON("json", String.class);

    @NotNull
    private final String typeName;

    @NotNull
    private final Class<?> expectedClass;

    /**
     * @param typeName The name of the primitive type, for example, "string".
     * @param expectedClass The java class of the primitive type, for example, "String.class".
     */
    ParameterTypeLabel(@NotNull String typeName, @NotNull Class<?> expectedClass) {
        this.typeName = typeName;
        this.expectedClass = expectedClass;
    }

    @NotNull
    public String getTypeName() {
        return typeName;
    }

    @NotNull
    public Class<?> getExpectedClass() {
        return expectedClass;
    }

    /**
     * @param rawTypeName The string name of the raw parameter type.
     * @return The raw parameter type.
     */
    public static ParameterTypeLabel parse(String rawTypeName) {
        return Arrays.stream(values()).filter((v) -> v.typeName.equals(rawTypeName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid raw parameter type: " + rawTypeName + " - must be one of " +
                                Arrays.toString(values()))
                );
    }
}
