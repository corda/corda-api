package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.TypedParameter;
import net.corda.v5.application.interop.parameters.TypedParameterValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A {@link FacadeResponse} is a response to a {@link FacadeRequest} to invoke a {@link FacadeMethod} on a {@link Facade}.
 */
public interface FacadeResponse {
    /**
     * @return The ID of the facade to which the method belongs.
     */
    @NotNull
    FacadeId getFacadeId();

    /**
     * @return The method that was invoked.
     */
    @NotNull
    String getMethodName();

    /**
     * @return The values of the out parameters of the method.
     */
    @NotNull
    List<TypedParameterValue<?>> getOutParameters();

    /**
     * Get the value of an out parameter.
     * @param parameter The parameter to get the value of.
     */
    @NotNull
    <T> T get(TypedParameter<T> parameter);
}
