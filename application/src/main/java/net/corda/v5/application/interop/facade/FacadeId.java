package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeId {
    @NotNull
    FacadeId of(String idString);

}
