package net.corda.v5.application.interop.evm;

import net.corda.v5.base.exceptions.CordaRuntimeException;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

/**
 * Used to represent a parameter to a Solidity function.
 */
public class Parameter<T> {

    public Parameter(@NotNull String name, @NotNull Type<T> type, @NotNull T value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Parameter(@NotNull String name, @NotNull T value) {
        this.name = name;
        this.type = determineType(value);
        this.value = value;
    }

    public static <T> Parameter<T> of(@NotNull String name, @NotNull Type<T> type, @NotNull T value) {
        return new Parameter<>(name, type, value);
    }

    public static <T> Parameter<T> of(@NotNull String name, @NotNull T value) {
        return new Parameter<>(name, value);
    }

    @SafeVarargs
    public static <T> Parameter<T[]> of(@NotNull String name, @NotNull T... value) {
        return new Parameter<>(name, value);
    }

    /**
     * The name of the parameter.
     */
    @NotNull
    private final String name;

    /**
     * The type of this specific parameter.  See {@link Type} for supported types.
     */
    @NotNull
    private final Type<?> type;

    /**
     * The actual value of the parameter.
     */
    @NotNull
    private final T value;

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Type<?> getType() {
        return type;
    }

    @NotNull
    public T getValue() {
        return value;
    }


    private Type<?> determineType(T value) {
        if (value.getClass().isArray()) {
            //noinspection unchecked
            return determineArrayType((T[])value);
        } else if (value instanceof List<?>) {
            return determineListType((List<?>)value);
        }

        if (value instanceof Byte) {
            return Type.BYTE;
        } else if (value instanceof Short) {
            return Type.INT16;
        } else if (value instanceof Integer) {
            return Type.INT32;
        } else if (value instanceof Long) {
            return Type.INT64;
        } else if (value instanceof Boolean) {
            return Type.BOOLEAN;
        } else if (value instanceof Enum) {
            return Type.ENUM;
        } else if (value instanceof Character) {
            return Type.CHARACTER;
        } else if (value instanceof String) {
            return Type.STRING;
        } else if (value instanceof BigInteger) {
            return Type.INT256;
        }
        throw new CordaRuntimeException("Value type not supported for: " + value.getClass().getTypeName());
    }

    private Type<?> determineArrayType(T[] value) {
        if (value instanceof Byte[]) {
            return Type.BYTE_ARRAY;
        } else if (value instanceof Short[]) {
            return Type.INT16_ARRAY;
        } else if (value instanceof Integer[]) {
            return Type.INT32_ARRAY;
        } else if (value instanceof Long[]) {
            return Type.INT64_ARRAY;
        } else if (value instanceof Boolean[]) {
            return Type.BOOLEAN_ARRAY;
        } else if (value instanceof Enum[]) {
            return Type.ENUM_ARRAY;
        } else if (value instanceof Character[]) {
            return Type.CHARACTER_ARRAY;
        } else if (value instanceof String[]) {
            return Type.STRING_ARRAY;
        } else if (value instanceof BigInteger[]) {
            return Type.INT256_ARRAY;
        }
        throw new CordaRuntimeException("Value type not supported for: " + value.getClass().getTypeName());
    }

    private Type<?> determineListType(List<?> value) {
        if (value.get(0) instanceof Byte) {
            return Type.BYTE_LIST;
        } else if (value.get(0) instanceof Short) {
            return Type.INT16_LIST;
        } else if (value.get(0) instanceof Integer) {
            return Type.INT32_LIST;
        } else if (value.get(0) instanceof Long) {
            return Type.INT64_LIST;
        } else if (value.get(0) instanceof Boolean) {
            return Type.BOOLEAN_LIST;
        } else if (value.get(0) instanceof Enum) {
            return Type.ENUM_LIST;
        } else if (value.get(0) instanceof Character) {
            return Type.CHARACTER_LIST;
        } else if (value.get(0) instanceof String) {
            return Type.STRING_LIST;
        } else if (value.get(0) instanceof BigInteger) {
            return Type.INT256_LIST;
        }
        throw new CordaRuntimeException("Value type not supported for: " + value.getClass().getTypeName());
    }

}
