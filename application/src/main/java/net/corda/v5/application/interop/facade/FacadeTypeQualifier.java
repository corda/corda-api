package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeTypeQualifier {
    @NotNull
    public String getOwner();

    @NotNull
    public List<String> getName();

    @NotNull
    public String getVersion();

    @NotNull
    FacadeTypeQualifier of(String qualifierString);
}
