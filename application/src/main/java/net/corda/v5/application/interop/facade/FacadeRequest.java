package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

public interface FacadeRequest {
    @NotNull
    <T> T get(Class<Object> parameter);
}
