package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterTypeLabel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Facade {

    @NotNull
    FacadeId getFacadeId();

    @NotNull
    List<FacadeMethod> getMethods();

    @NotNull
    Map<String, FacadeMethod> getMethodsByName();

    /**
     * Get the method with the given name.
     *
     * @param name The name of the method to get.
     */

    @NotNull
    FacadeMethod method(String name);

    /**
     * Obtain a request to invoke the method with the given name, with the given parameter values.
     *
     * @param methodName   The name of the method to invoke.
     * @param inParameters The parameter values to pass to the method.
     */
    @NotNull
    FacadeRequest request(String methodName, ParameterTypeLabel... inParameters);

    /**
     * Obtain a response to an invocation of the method with the given name, with the given parameter values.
     *
     * @param methodName    The name of the method that was invoked.
     * @param outParameters The values of the out parameters of the method.
     */
    @NotNull
    FacadeResponse response(String methodName, ParameterTypeLabel... outParameters);

}