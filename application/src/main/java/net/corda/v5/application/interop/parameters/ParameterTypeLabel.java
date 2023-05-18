package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * Enum defining the possible primitive types for parameters.
 */
public enum ParameterTypeLabel {
    BOOLEAN(Boolean.class),
    STRING(String.class),
    DECIMAL(BigDecimal.class),
    UUID(java.util.UUID.class),
    TIMESTAMP(ZonedDateTime.class),
    BYTES(ByteBuffer.class),
    JSON(String.class);

    @NotNull
    private final Class<?> expectedClass;

    /**
     * @param expectedClass The java class of the primitive type, e.g. String.class.
     */
    ParameterTypeLabel(@NotNull Class<?> expectedClass) {
        this.expectedClass = expectedClass;
    }

    @NotNull
    public Class<?> getExpectedClass() {
        return expectedClass;
    }

    /**
     * @param rawTypeName The string name of the raw parameter type
     * @return The raw parameter type.
     */
    public static ParameterTypeLabel parse(String rawTypeName) {
        return Arrays.stream(values()).filter((v) -> v.name().equals(rawTypeName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid raw parameter type: " + rawTypeName + " - must be one of " +
                                Arrays.toString(values()))
                );
    }
}
