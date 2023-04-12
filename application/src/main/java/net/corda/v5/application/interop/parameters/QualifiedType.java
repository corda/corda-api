package net.corda.v5.application.interop.parameters;

import net.corda.v5.application.interop.facade.FacadeTypeQualifierInterface;
import org.jetbrains.annotations.NotNull;

public final class QualifiedType<T> implements ParameterType<T> {

    @NotNull
    RawParameterType<T> rawParameterType;

    @NotNull
    private final ParameterType<T> type;

    @NotNull
    private final FacadeTypeQualifierInterface qualifier;

    @NotNull
    private ParameterTypeLabel typeLabel;

    @NotNull
    private final Class<T> expectedClass = (Class<T>) typeLabel.getExpectedClass();

    @NotNull
    private final Class<T> expectedRawClass = (Class<T>) rawParameterType.getExpectedClass();

    private final boolean isQualified = true;

    public QualifiedType(RawParameterType<T> rawParameterType, @NotNull ParameterType<T> type, @NotNull FacadeTypeQualifierInterface qualifier, ParameterTypeLabel typeLabel) {
        this.rawParameterType = rawParameterType;
        this.type = type;
        this.qualifier = qualifier;
        this.typeLabel = typeLabel;
    }

    @NotNull
    @Override
    public Class<T> getExpectedType() {
        return type.getExpectedType();
    }

    @Override
    public String toString() {
        return type + "(" + qualifier + ")";
    }

    public RawParameterType<T> getRawParameterType() {
        return rawParameterType;
    }

    @NotNull
    public ParameterType<T> getType() {
        return type;
    }

    @NotNull
    public Class<T> getExpectedClass() {
        return expectedClass;
    }

    @Override
    public boolean isQualified() {
        return isQualified;
    }

    @Override
    @NotNull
    public FacadeTypeQualifierInterface getQualifier() {
        return qualifier;
    }

    @Override
    @NotNull
    public ParameterTypeLabel getTypeLabel() {
        return typeLabel;
    }

    @NotNull
    public Class<T> getExpectedRawClass() {
        return expectedRawClass;
    }
}

