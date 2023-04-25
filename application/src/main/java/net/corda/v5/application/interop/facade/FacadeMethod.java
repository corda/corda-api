package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface FacadeMethod {
    /**
     * Get the input parameter with the given name.
     *
     * @param parameterName The name of the input parameter.
     */
    @NotNull
    <T> ParameterType<T> inParameter(String parameterName);


    /**
     * Obtain the in parameter with the given name.
     *
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType  The expected type of the parameter.
     */
    @NotNull
    <T> ParameterType<T> inParameter(String parameterName, Class<T> expectedType);

    /**
     * Get the in parameter with the given name, without checking that its type matches an expected type.
     *
     * @param parameterName: The name of the parameter to obtain.
     */
    @Nullable
    ParameterType<?> untypedInParameter(String parameterName);

    /**
     * Get the output parameter with the given name.
     *
     * @param parameterName The name of the output parameter.
     */
    @NotNull
    <T> ParameterType<T> outParameter(String parameterName);

    /**
     * Obtain the out parameter with the given name.
     *
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType  The expected type of the parameter.
     */
    <T> ParameterType<T> outParameter(String parameterName, Class<T> expectedType);

    /**
     * Create a [FacadeRequest] for this method.
     *
     * @param parameterValues The parameter values to pass to the method.
     */
    @NotNull
    FacadeRequest request(ParameterType... parameterValues);

    /**
     * Create a [FacadeResponse] for this method.
     *
     * @param parameterValues The parameter values to return from the method.
     */
    @NotNull
    FacadeResponse response(ParameterType... parameterValues);

    @NotNull
    FacadeId getFacadeId();

    @NotNull
    String getName();

    @NotNull
    FacadeMethodType getType();

    @NotNull
    List<ParameterType<?>> getInParameters();

    @NotNull
    List<ParameterType<?>> getOutParameters();

    @NotNull
    String getQualifiedName();
}
