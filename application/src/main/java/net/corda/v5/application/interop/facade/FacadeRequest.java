package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.TypedParameter;
import net.corda.v5.application.interop.parameters.TypedParameterValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A {@link FacadeRequest} is a request to invoke a {@link FacadeMethod} on a {@link Facade}.
 */
public interface FacadeRequest {

    /**
     * @return The ID of the facade to which the method belongs.
     */
    @NotNull
    FacadeId getFacadeId();

    /**
     * @return The name of the method to invoke.
     */
    @NotNull
    String getMethodName();

    /**
     * @return The parameter values to pass to the method.
     */
    @NotNull
    List<TypedParameterValue<?>> getInParameters();

    /**
     * Get the value of a parameter by name.
     * @param parameter The parameter to get the value of.
     */
    @NotNull
    <T> T get(TypedParameter<T> parameter);
}
