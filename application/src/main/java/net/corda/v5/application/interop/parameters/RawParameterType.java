package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RawParameterType<T> implements ParameterType<T>{

    @NotNull
    private final Class<T> expectedType;

    @NotNull
    private final String name;

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
}
