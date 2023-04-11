package net.corda.v5.application.interop.parameters;

import net.corda.v5.application.interop.facade.FacadeTypeQualifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RawParameterType<T> implements ParameterType<T>{

    @NotNull
    private final Class<T> expectedType;

    @NotNull
    private final String name;

    private final boolean isQualified = false;

    @Nullable
    private final FacadeTypeQualifier qualifier = null;

    @NotNull
    private ParameterTypeLabel typeLabel;

    @NotNull
    private final Class<T> expectedClass = (Class<T>) typeLabel.getExpectedClass();

    public RawParameterType(@NotNull Class<T> expectedType, @NotNull String name) {
        this.expectedType = expectedType;
        this.name = name;
    }

    @NotNull
    @Override
    public Class<T> getExpectedType() {
        return expectedType;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RawParameterType<?> that = (RawParameterType<?>) o;
        return expectedType.equals(that.expectedType) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expectedType, name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isQualified() {
        return isQualified;
    }

    @Override
    @Nullable
    public FacadeTypeQualifier getQualifier() {
        return qualifier;
    }

    @Override
    @NotNull
    public ParameterTypeLabel getTypeLabel() {
        return typeLabel;
    }

    @NotNull
    public Class<T> getExpectedClass() {
        return expectedClass;
    }
}

