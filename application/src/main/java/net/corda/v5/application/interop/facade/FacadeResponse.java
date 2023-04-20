package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import net.corda.v5.application.interop.parameters.ParameterTypeLabel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeResponse {
    @NotNull
    FacadeId getFacadeId();

    @NotNull
    String getMethodName();

    @NotNull
    List<ParameterTypeLabel> getOutParameters();
    @NotNull
    <T> T get(ParameterType<T> parameter);
}
