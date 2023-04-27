package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.TypedParameter;
import net.corda.v5.application.interop.parameters.TypedParameterValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface FacadeMethod {

    /**
     * Obtain the in parameter with the given name.
     *
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType  The expected type of the parameter.
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
     * Obtain the out parameter with the given name.
     *
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType  The expected type of the parameter.
     */
    <T> TypedParameter<T> outParameter(String parameterName, Class<T> expectedType);

    /**
     * Create a [FacadeRequest] for this method.
     *
     * @param parameterValues The parameter values to pass to the method.
     */
    @NotNull
    FacadeRequest request(TypedParameterValue<?>... parameterValues);

    /**
     * Create a [FacadeResponse] for this method.
     *
     * @param parameterValues The parameter values to return from the method.
     */
    @NotNull
    FacadeResponse response(TypedParameterValue<?>... parameterValues);

    @NotNull
    FacadeId getFacadeId();

    @NotNull
    String getName();

    @NotNull
    FacadeMethodType getType();

    @NotNull
    List<TypedParameter<?>> getInParameters();

    @NotNull
    List<TypedParameter<?>> getOutParameters();

    @NotNull
    String getQualifiedName();
}
