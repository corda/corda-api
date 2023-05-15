package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

public interface TypedParameterValue<T> {
    @NotNull
    TypedParameter<T> getParameter();

    @NotNull
    T getValue();
}
