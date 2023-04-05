package net.corda.v5.application.interop.parameters;

import net.corda.v5.application.interop.facade.FacadeTypeQualifier;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

public interface ParameterType<T> {
    /**
     * Parse a [ParameterType] from a string in the format "type" or "type (qualifier)".
     * <p>
     * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json".
     *
     * @param typeString The string to parse.
     */
    default <T> ParameterType<T> of(String typeString) {
        return of(typeString, Collections.emptyMap());
    }

    /**
     * Parse a [ParameterType] from a string in the format "type" or "type (qualifier)".
     * <p>
     * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json", or aliases
     * defined in the supplied [Map].
     *
     * @param typeString The string to parse.
     * @param aliases    A map of type aliases.
     */
    @SuppressWarnings("unchecked")
    default <T> ParameterType<T> of(String typeString, Map<String, ParameterType<?>> aliases) {
        var matcher = Pattern.compile("\\s*(\\S+)(\\s+\\((\\S+)\\))?.*").matcher(typeString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid parameter type: " + typeString);
        }

        var rawTypeName = matcher.group(1);
        var qualifierString = matcher.groupCount() == 3 ? matcher.group(3) : null;
        var aliased = aliases.get(rawTypeName);
        if (aliased != null) {
            if (qualifierString != null) {
                throw new IllegalArgumentException("Alias " + rawTypeName + " cannot be qualified with " + qualifierString);
            }
            return (ParameterType<T>) aliased;
        }

        var rawType = ParameterType.parseRawParameterType(rawTypeName);

        if (qualifierString == null) {
            return (ParameterType<T>) rawType;
        }

        return new QualifiedType(rawType, FacadeTypeQualifier.of(qualifierString));
    }

    static ParameterType<?> parseRawParameterType(String typeName) {
        return PrimitiveParameterType.parse(typeName).getParameterType();
    }

    @NotNull
    Class<T> getExpectedType();

    public enum PrimitiveParameterType {
        BOOLEAN("boolean", Boolean.class),
        STRING("string", String.class),
        DECIMAL("decimal", BigDecimal.class),
        UUID("uuid", java.util.UUID.class),
        TIMESTAMP("timestamp", ZonedDateTime.class),
        BYTES("bytes", ByteBuffer.class),
        JSON("json", String.class);

        static PrimitiveParameterType parse(String rawTypeName) {
            return Arrays.stream(values()).filter((v) -> v.typeName.equals(rawTypeName))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException("Invalid raw parameter type: " + rawTypeName + " - must be one of " +
                                    Arrays.toString(values()))
                    );
        }

        private final String typeName;
        private final Class<?> expectedType;

        PrimitiveParameterType(String name, Class<?> expectedType) {
            this.typeName = name;
            this.expectedType = expectedType;
        }

        public <T> ParameterType<T> getParameterType() {
            return new RawParameterType<>((Class<T>) expectedType, typeName);
        }
    }
}
