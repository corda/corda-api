package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class FacadeTypeQualifier implements FacadeTypeQualifierInterface{
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
}
