package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

public interface ParameterType<T> {
    @NotNull
    ParameterTypeLabel getTypeLabel();

    @NotNull
    Class<T> getExpectedType();

    boolean isQualified();

    TypeQualifier getQualifier();

    ParameterType<T> getRawParameterType();

    default String getTypeName() {
        return getTypeLabel().getTypeName();
    }
}