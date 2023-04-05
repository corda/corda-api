package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FacadeMethod {
    /**
     * Get the input parameter with the given name.
     *
     * @param parameterName The name of the input parameter.
     */
    @NotNull
    <T> TypedParameter<T> inParameter(String parameterName);


    /**
     * Obtain the in parameter with the given name.
     *
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType The expected type of the parameter.
     */
    @NotNull
    <T> TypedParameter<T> inParameter(String parameterName, Class<T> expectedType);

    /**
     * Get the in parameter with the given name, without checking that its type matches an expected type.
     *
     * @param parameterName: The name of the parameter to obtain.
     */
    @Nullable
    TypedParameter<?> untypedInParameter(String parameterName);

    /**
     * Get the output parameter with the given name.
     *
     * @param parameterName The name of the output parameter.
     */
    @NotNull
    <T> TypedParameter<T> outParameter(String parameterName);

    /**
     * Obtain the out parameter with the given name.
     *
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType The expected type of the parameter.
     */

    <T> TypedParameter<T> outParameter(String parameterName, Class<T> expectedType);

    /**
     * Create a [FacadeRequest] for this method.
     *
     * @param parameterValues The parameter values to pass to the method.
     */
    @NotNull
    FacadeRequest request(TypedParameterValue<?>... parameterValues);

    @NotNull
    FacadeRequest request(List<TypedParameterValue<?>> parameterValues);

    /**
     * Create a [FacadeResponse] for this method.
     *
     * @param parameterValues The parameter values to return from the method.
     */
    @NotNull
    FacadeResponse response(TypedParameterValue<?>... parameterValues);

    @NotNull
    FacadeResponse response(List<TypedParameterValue<?>> parameterValues);
}
