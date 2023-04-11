package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeId {

    @NotNull
    String getOwner();

    @NotNull
    List<String> getName();

    @NotNull
    String getVersion();

    @NotNull
    String getUnversionedName();

    @NotNull
    FacadeId of(String idString);

}
