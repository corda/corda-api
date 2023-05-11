package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

public interface TypedParameter<T> {
    @NotNull
    String getName();

    @NotNull
    ParameterType<T> getType();

    @NotNull
    TypedParameterValue<T> of(T value);
}
