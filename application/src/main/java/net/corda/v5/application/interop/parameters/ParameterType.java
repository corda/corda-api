package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

/**
 * A [ParameterType] is the type of [TypedParameter]. It is always one of a small set of primitive types, or
 * a [QualifiedType] qualifying a primitive type with a [TypeQualifier] which identifies a more complex type.
 */
public interface ParameterType<T> {
    @NotNull
    ParameterTypeLabel getTypeLabel();

    /**
     * @return The expected type of [TypedParameterValue] for this [ParameterType]
     */
    @NotNull
    Class<T> getExpectedType();

    /**
     * @return The boolean which determines whether the ParameterType is primitive or complex.
     */
    boolean isQualified();

    /**
     * @return The qualifier which assigns the ParameterType with a versioned identity.
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
        return getTypeLabel().getTypeName();
    }
}