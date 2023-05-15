package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

/**
 * A [TypedParameter] is a parameter having a name and a defined [ParameterType].
 */
public interface TypedParameter<T> {
    /**
     * @return The name of the parameter, e.g. "amount".
     */
    @NotNull
    String getName();

    /**
     * @return The type of the parameter, e.g. [ParameterTypeLabel.STRING].
     */
    @NotNull
    ParameterType<T> getType();

    @NotNull
    TypedParameterValue<T> of(T value);
}
