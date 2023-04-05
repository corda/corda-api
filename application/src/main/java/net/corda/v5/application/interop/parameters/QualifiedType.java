package net.corda.v5.application.interop.parameters;

import net.corda.v5.application.interop.facade.FacadeTypeQualifier;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

//TODO make methods notNull
public final class QualifiedType<T> implements ParameterType{

    @NotNull
    private final ParameterType<T> type;

    @NotNull
    private final FacadeTypeQualifier qualifier;

    public QualifiedType(@NotNull ParameterType<T> type, @NotNull FacadeTypeQualifier qualifier) {
        this.type = type;
        this.qualifier = qualifier;
    }

    @Override
    public ParameterType of(String typeString) {
        return null;
    }

    @Override
    public ParameterType of(String typeString, Map aliases) {
        return null;
    }

    @Override
    public Class<T> getExpectedType() {
        return type.getExpectedType();
    }

    @Override
    public String toString() {
        return type + "(" + qualifier + ")";
    }
}
