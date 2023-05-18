package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link ParameterType} is the type of {@link TypedParameter}. It is always one of a small set of primitive types: "boolean",
 * "string", "decimal", "uuid", "timestamp", "bytes" and "json", or
 * a QualifiedType qualifying a primitive type with a {@link TypeQualifier} which identifies a more complex type.
 */
public interface ParameterType<T> {
    @NotNull
    ParameterTypeLabel getTypeLabel();

    /**
     * @return The expected type of {@link TypedParameterValue} for this {@link ParameterType}
     */
    @NotNull
    Class<T> getExpectedType();

    /**
     * @return The boolean which determines whether the ParameterType is primitive (false) or complex (true).
     */
    boolean isQualified();

    /**
     * @return The qualifier which assigns the ParameterType with a versioned identity,
     * expected to return null in case of a raw parameter type
     */
    TypeQualifier getQualifier();

    /**
     * @return The parameter type as one of the raw types.
     */
    ParameterType<T> getRawParameterType();

    /**
     * @return The name of the raw parameter type.
     */
    default String getTypeName() {
        return getTypeLabel().name();
    }
}