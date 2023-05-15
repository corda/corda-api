package net.corda.v5.application.interop.binding;

import net.corda.v5.application.interop.facade.FacadeResponse;

@FunctionalInterface
public interface Interpretable<T> {
    public T interpret(FacadeResponse response);
}
