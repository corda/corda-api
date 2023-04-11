package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import org.jetbrains.annotations.NotNull;

//TODO add implementation for this interface in corda-runtime-os
public interface FacadeRequest {
    @NotNull
    <T> T get(ParameterType<T> parameter);
}
