package net.corda.v5.application.interop.parameters;

import net.corda.v5.application.interop.facade.FacadeTypeQualifier;
import net.corda.v5.application.interop.facade.FacadeTypeQualifierInterface;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

public interface ParameterType<T> {
    Pattern facadeTypeRegex = Pattern.compile("\\s*(\\S+)(\\s+\\((\\S+)\\))?.*");
    @NotNull
    ParameterTypeLabel getTypeLabel();
    @NotNull
    Class<T> getExpectedType();
    boolean isQualified();
    FacadeTypeQualifierInterface getQualifier();

    /**
     * Parse a [ParameterType] from a string in the format "type" or "type (qualifier)".
     * <p>
     * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json".
     *
     * @param typeString The string to parse.
     */
    public static <T> ParameterType<T> of(String typeString) {
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
    public static <T> ParameterType<T> of(String typeString, Map<String, ParameterType<?>> aliases) {
        var matcher = facadeTypeRegex.matcher(typeString);
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

        return new QualifiedType(null, rawType, FacadeTypeQualifierInterface.of(qualifierString), null);
    }

    private static ParameterType<?> parseRawParameterType(String typeName) {
        return ParameterTypeLabel.parse(typeName).getParameterType();
    }
}