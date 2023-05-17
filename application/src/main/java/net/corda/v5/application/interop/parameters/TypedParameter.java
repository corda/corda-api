package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link TypedParameter} is a parameter having a name and a defined {@link ParameterType}.
 */
public interface TypedParameter<T> {
    /**
     * @return The name of the parameter, for example, "amount".
     */
    @NotNull
    String getName();

    /**
     * @return The {@link ParameterType} of the parameter which enables calling getExpectedType() utility
     * to get the primitive type from the ParameterTypeLabel enum, for example, {@link ParameterTypeLabel}.STRING.
     */
    @NotNull
    ParameterType<T> getType();

    /**
     * @param value The value of the parameter.
     * @return Parameter converted to {@link TypedParameterValue}
     */
    @NotNull
    TypedParameterValue<T> of(T value);
}
