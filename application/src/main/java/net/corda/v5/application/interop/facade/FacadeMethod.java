package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.TypedParameter;
import net.corda.v5.application.interop.parameters.TypedParameterValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A [FacadeMethod] is a method of a [Facade].
 */
public interface FacadeMethod {

    /**
     * Obtain the in parameter with the given name.
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType  The expected type of the parameter.
     */
    @NotNull
    <T> TypedParameter<T> inParameter(String parameterName, Class<T> expectedType);

    /**
     * Get the in parameter with the given name, without checking that its type matches an expected type.
     * @param parameterName: The name of the parameter to obtain.
     */
    @Nullable
    TypedParameter<?> untypedInParameter(String parameterName);

    /**
     * Obtain the out parameter with the given name.
     * @param parameterName The name of the parameter to obtain.
     * @param expectedType  The expected type of the parameter.
     */
    <T> TypedParameter<T> outParameter(String parameterName, Class<T> expectedType);

    /**
     * Create a [FacadeRequest] for this method.
     * @param parameterValues The parameter values to pass to the method.
     */
    @NotNull
    FacadeRequest request(TypedParameterValue<?>... parameterValues);

    /**
     * Create a [FacadeResponse] for this method.
     * @param parameterValues The parameter values to return from the method.
     */
    @NotNull
    FacadeResponse response(TypedParameterValue<?>... parameterValues);

    /**
     * @return facadeId The [FacadeId] of the owning facade.
     */
    @NotNull
    FacadeId getFacadeId();

    /**
     * @return The name of the method
     */
    @NotNull
    String getName();

    /**
     * @return The type of the facade method, can be of two types: command or query
     */
    @NotNull
    FacadeMethodType getType();

    /**
     * @return The input parameters of the method.
     */
    @NotNull
    List<TypedParameter<?>> getInParameters();

    /**
     * @return The output parameters of the method.
     */
    @NotNull
    List<TypedParameter<?>> getOutParameters();

    /**
     * @return The qualified name of the method, which is the name of the facade followed by the name of the method.
     */
    @NotNull
    String getQualifiedName();

    enum FacadeMethodType {
        COMMAND,
        QUERY
    }
}
