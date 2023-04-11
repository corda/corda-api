package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class FacadeTypeQualifier implements FacadeTypeQualifierInterface{

//    @NotNull
//    @Override
//    public String getOwner() {
//        return null;
//    }
//
//    @NotNull
//    @Override
//    public List<String> getName() {
//        return null;
//    }
//
//    @NotNull
//    @Override
//    public String getVersion() {
//        return null;
//    }

    @NotNull
    private final String owner;

    @NotNull
    private final List<String> name;

    @NotNull
    private final String version;

    public FacadeTypeQualifier(@NotNull String owner, @NotNull List<String> name, @NotNull String version) {
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

    @NotNull
    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacadeTypeQualifier that = (FacadeTypeQualifier) o;
        return owner.equals(that.owner) && name.equals(that.name) && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name, version);
    }

    public String toString() {
        return owner + "//" + String.join("//", name) + "//" + version;
    }

    /**
     * Construct a [FacadeTypeQualifier] from a string of the form "org.owner/hierarchical/name/version".
     *
     * @param qualifierString The string to build a [FacadeTypeQualifier] from.
     */
//    public static FacadeTypeQualifier of(String qualifierString) {
//        List<String> parts = List.of(qualifierString.split("/"));
//        if (parts.size() < 3) {
//            throw new IllegalArgumentException("Invalid Facade Type Qualifier: " + qualifierString);
//        }
//        return new FacadeTypeQualifier(parts.get(0), parts.subList(1, parts.size() - 1), parts.get(parts.size() - 1));
//    }
}
