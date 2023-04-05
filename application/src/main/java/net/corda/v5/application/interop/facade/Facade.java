package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

public interface Facade {

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
    FacadeRequest request(String methodName, TypedParameterValue<?>... inParameters);

    /**
     * Obtain a response to an invocation of the method with the given name, with the given parameter values.
     *
     * @param methodName    The name of the method that was invoked.
     * @param outParameters The values of the out parameters of the method.
     */
    @NotNull
    FacadeResponse response(String methodName, TypedParameterValue... outParameters);

}