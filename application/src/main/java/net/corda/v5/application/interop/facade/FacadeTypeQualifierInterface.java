package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FacadeTypeQualifierInterface {
    @NotNull
    public String getOwner();

    @NotNull
    public List<String> getName();

    @NotNull
    public String getVersion();

    @NotNull
    static FacadeTypeQualifier of(String qualifierString){
        List<String> parts = List.of(qualifierString.split("/"));
        if (parts.size() < 3) {
            throw new IllegalArgumentException("Invalid Facade Type Qualifier: " + qualifierString);
        }
        return new FacadeTypeQualifier(parts.get(0), parts.subList(1, parts.size() - 1), parts.get(parts.size() - 1));
    }
}
