package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;

final class FacadeId {

    @NotNull
    String owner;

    @NotNull
    List<String> name;

    @NotNull
    String version;

    public FacadeId(@NotNull String owner, @NotNull List<String> name, @NotNull String version) {
        this.owner = owner;
        this.name = name;
        this.version = version;
    }

    @NotNull
    public String getOwner() {
        return owner;
    }

    @NotNull
    public List<String> getName() {
        return name;
    }

    public static FacadeId of(String idString) {
        List<String> parts = List.of(idString.split("/"));
        if (parts.size() < 3) {
            throw new IllegalArgumentException("Invalid Facade ID: " + idString);
        }
        return new FacadeId(parts.get(0), parts.subList(1, parts.size() - 1), parts.get(parts.size() - 1));
    }

    @NotNull
    String unversionedName = owner + "/" + String.join("/", name);

    @Override
    public java.lang.String toString() {
        return unversionedName + "/" + version;
    }
}
