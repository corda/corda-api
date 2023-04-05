package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

//TODO add implementation for this interface in corda-runtime-os
public interface FacadeRequest {
    @NotNull
    <T> T get(TypedParameter<T> parameter);
}
