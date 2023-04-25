package net.corda.v5.application.interop.parameters;

import org.jetbrains.annotations.NotNull;
import java.util.regex.Pattern;

public interface ParameterType<T> {
    Pattern facadeTypeRegex = Pattern.compile("\\s*(\\S+)(\\s+\\((\\S+)\\))?.*");

    @NotNull
    ParameterTypeLabel getTypeLabel();

    @NotNull
    Class<T> getExpectedType();

    Boolean isQualified();

    TypeQualifier getQualifier();

    ParameterType<T> getRawParameterType();

    default String getTypeName() {
        return getTypeLabel().getTypeName();
    }
}