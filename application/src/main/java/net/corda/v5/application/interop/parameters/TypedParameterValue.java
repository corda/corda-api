package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

/**
 * The value of a [TypedParameter].
 * Cannot be constructed unless the types of the parameter and value agree.
 */
public interface TypedParameterValue<T> {
    /**
     * @return The parameter to which this value belongs.
     */
    @NotNull
    TypedParameter<T> getParameter();

    /**
     * @return The value of the parameter.
     */
    @NotNull
    T getValue();
}
