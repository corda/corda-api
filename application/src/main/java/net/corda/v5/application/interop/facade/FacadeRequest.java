package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeRequest {

    @NotNull
    FacadeId getFacadeId();

    @NotNull
    String getMethodName();

    @NotNull
    List<ParameterType<?>> getInParameters();
    @NotNull
    <T> T get(ParameterType<T> parameter);
}
