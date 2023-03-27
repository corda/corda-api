package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class ParameterType<T> {

    /**
     * This pattern matches (after a whitespace prefix of any length) either a single non-whitespace string, e.g.
     * "uuid", or a pair of non-whitespace strings separated by at least one character of whitespace, with the
     * second string surrounded by parentheses.
     *
     * For example: "denomination  (org.corda.interop/finance/tokens/denomination/v1.0)".
     */
    private static Pattern facadeTypeRegex = Pattern.compile("\\s*(\\S+)(\\s+\\((\\S+)\\))?.*");

    /**
     * Parse a [KotlinParameterType] from a string in the format "type" or "type (qualifier)".
     *
     * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json".
     *
     * @param typeString The string to parse.
     */
    public static <T> ParameterType<T> of(String typeString) {
        of(typeString, Collections.emptyMap());
    }


    /**
            * Parse a [KotlinParameterType] from a string in the format "type" or "type (qualifier)".
            *
            * The accepted types are "boolean", "string", "decimal", "uuid", "timestamp", "bytes" and "json", or aliases
         * defined in the supplied [Map].
            *
            * @param typeString The string to parse.
         * @param aliases A map of type aliases.
            */
    @SuppressWarnings("unchecked")
    public static <T> ParameterType<T> of(String typeString, Map<String, ParameterType<?>> aliases) {
        /*
         val typeMatch = facadeTypeRegex.matchEntire(typeString)
                ?: throw IllegalArgumentException("Invalid parameter type: $typeString")

            val rawTypeName = typeMatch.groups[1]!!.value
            val qualifierString = typeMatch.groups[3]?.value
            val aliased = aliases[rawTypeName]
            if (aliased != null) {
                if (qualifierString != null) {
                    throw IllegalArgumentException("Alias $rawTypeName cannot be qualified with $qualifierString")
                }
                return aliased as KotlinParameterType<T>
            }

            val rawType = parseRawParameterType<T>(rawTypeName)
            return if (qualifierString == null) rawType
            else QualifiedType(rawType, FacadeTypeQualifier.of(qualifierString))
         */
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

        return new QualifiedType(rawType, FacadeTypeQualifier.of(qualifierString));
    }

    private static ParameterType<?> parseRawParameterType(String typeName) {
        switch(typeName) {
            case "boolean": return new RawParameterType<>(Boolean.class, "boolean");
            default: throw new IllegalArgumentException(
                    "Invalid raw parameter type: " + typeName + " - must be one of " +
                            "boolean, string, decimal, uuid, timestamp, bytes or json");
        }
        /*when (typeName) {
        "boolean" -> BooleanType
        "string" -> StringType
        "decimal" -> DecimalType
        "uuid" -> UUIDType
        "timestamp" -> TimestampType
        "bytes" -> ByteBufferType
        "json" -> JsonType

                else -> throw IllegalArgumentException(
                "Invalid raw parameter type: $typeName - " +
                        "must be one of boolean, string, decimal, uuid, timestamp, bytes or json"
        )
    } as KotlinParameterType<T>
    */
    }

    abstract Class<T> getExpectedType();

    @Override
    public boolean equals(Object other) {
        return other instanceof ParameterType && other.toString().equals(toString());
    }

    private static final class RawParameterType<T> extends ParameterType<T> {

        @NotNull
        private final Class<T> expectedType;

        @NotNull
        private final String name;

        public RawParameterType(@NotNull Class<T> expectedType, @NotNull String name) {
            this.expectedType = expectedType;
            this.name = name;
        }

        @Override
        Class<T> getExpectedType() {
            return expectedType;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            RawParameterType<?> that = (RawParameterType<?>) o;
            return expectedType.equals(that.expectedType) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expectedType, name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final class QualifiedType<T> extends ParameterType<T> {

        @NotNull
        private final ParameterType<T> type;

        @NotNull
        private final FacadeTypeQualifier qualifier;

        public QualifiedType(@NotNull ParameterType<T> type, @NotNull FacadeTypeQualifier qualifier) {
            this.type = type;
            this.qualifier = qualifier;
        }

        @Override
        public Class<T> getExpectedType() {
            return type.getExpectedType();
        }

        @Override
        public String toString() {
            return type + "(" + qualifier + ")";
        }
    }

}
