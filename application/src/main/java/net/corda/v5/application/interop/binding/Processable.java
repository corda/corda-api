package net.corda.v5.application.interop.binding;

import net.corda.v5.application.interop.facade.FacadeRequest;
import net.corda.v5.application.interop.facade.FacadeResponse;

@FunctionalInterface
public interface Processable {
    public FacadeResponse process(FacadeRequest request);
}
