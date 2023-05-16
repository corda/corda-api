package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.TypedParameterValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * A {@link Facade} is a shared abstraction used by interoperating Corda agents.
 * It is a collection of {@link FacadeMethod}s that can be invoked from a flow.
 * It comes in the form of a text file with minimal semantics containing list of methods with parameters and returned values.
 */
public interface Facade {

    /**
     * @return The name of the facade, e.g. "org.corda.interop/platform/tokens/1.0".
     */
    @NotNull
    FacadeId getFacadeId();

    /**
     * @return The methods that can be invoked on the facade.
     */
    @NotNull
    List<FacadeMethod> getMethods();

    /**
     * @return The facade methods with a given string name that can be invoked on the facade.
     */
    @NotNull
    Map<String, FacadeMethod> getMethodsByName();

    /**
     * Get the method with the given name.
     * @param name The name of the method to get.
     */
    @NotNull
    FacadeMethod method(String name);

    /**
     * Obtain a request to invoke the method with the given name, with the given parameter values.
     * @param methodName   The name of the method to invoke.
     * @param inParameters The parameter values to pass to the method.
     */
    @NotNull
    FacadeRequest request(String methodName, TypedParameterValue<?>... inParameters);

    /**
     * Obtain a response to an invocation of the method with the given name, with the given parameter values.
     * @param methodName    The name of the method that was invoked.
     * @param outParameters The values of the out parameters of the method.
     */
    @NotNull
    FacadeResponse response(String methodName, TypedParameterValue<?>... outParameters);
}