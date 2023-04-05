package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

public interface FacadeResponse {
    @NotNull
    <T> T get(TypedParameter<T> parameter);
}
