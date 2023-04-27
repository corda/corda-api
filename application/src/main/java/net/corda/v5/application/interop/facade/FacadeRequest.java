package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import net.corda.v5.application.interop.parameters.TypedParameter;
import net.corda.v5.application.interop.parameters.TypedParameterValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeRequest {

    @NotNull
    FacadeId getFacadeId();

    @NotNull
    String getMethodName();

    @NotNull
    List<TypedParameterValue<?>> getInParameters();

    /**
     * Get the value of a parameter by name.
     *
     * @param parameter The parameter to get the value of.
     */
    @NotNull
    <T> T get(TypedParameter<T> parameter);
}
