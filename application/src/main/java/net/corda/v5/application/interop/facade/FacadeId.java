package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class FacadeId {

    /**
     * Construct a {@link FacadeId} from a string of the form "org.owner/hierarchical/name/version".
     * @param idString The string to build a {@link FacadeId} from.
     */
    public static FacadeId of(String idString) {
        List<String> parts = List.of(idString.split("/"));
        if (parts.size() < 3) {
            throw new IllegalArgumentException("Invalid Facade ID: " + idString);
        }
        return new FacadeId(parts.get(0), parts.subList(1, parts.size() - 1), parts.get(parts.size() - 1));
    }

    @NotNull
    private final String owner;

    @NotNull
    private final List<String> name;

    @NotNull
    private final String version;

    @NotNull
    private final String unversionedName;

    /**
     * A {@link FacadeId} identifies a version of a facade.
     * @param owner The name of the owner of the facade, for example, "org.corda".
     * @param name The name of the facade, e.g. "platform/tokens", expressed as a List of strings.
     * @param version The version identifier of the facade, e.g. "1.0".
     */
    public FacadeId(@NotNull String owner, @NotNull List<String> name, @NotNull String version) {
        this.owner = owner;
        this.name = name;
        this.version = version;
        this.unversionedName = owner + "/" + String.join("/", name);
    }

    @NotNull
    public String getOwner() {
        return owner;
    }

    @NotNull
    public List<String> getName() {
        return name;
    }

    @NotNull
    public String getVersion() {
        return version;
    }

    @NotNull
    public String getUnversionedName() {
        return unversionedName;
    }

    @Override
    public String toString() {
        return unversionedName + "/" + version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacadeId facadeId = (FacadeId) o;
        return owner.equals(facadeId.owner) && name.equals(facadeId.name) && version.equals(facadeId.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name, version);
    }
}
