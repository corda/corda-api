package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import org.jetbrains.annotations.NotNull;

public interface FacadeResponse {
    @NotNull
    <T> T get(ParameterType<T> parameter);
}
