package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class FacadeId {
    @NotNull
    String owner;

    @NotNull
    List<String> name;

    @NotNull
    String version;

    /**
     * A [FacadeId] identifies a version of a facade.
     *
     * @param owner The name of the owner of the facade, e.g. "org.corda".
     * @param name The name of the facade, e.g. "platform/tokens", expressed as a List of strings.
     * @param version The version identifier of the facade, e.g. "1.0".
     */
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

    /**
     * Construct a [FacadeId] from a string of the form "org.owner/hierarchical/name/version".
     *
     * @param idString The string to build a [FacadeId] from.
     */
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

    @NotNull
    public String getVersion() {
        return version;
    }

    @NotNull
    public String getUnversionedName() {
        return unversionedName;
    }
}
