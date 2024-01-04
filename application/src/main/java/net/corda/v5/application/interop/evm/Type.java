package net.corda.v5.application.interop.evm;

import java.math.BigInteger;

/**
 * The list of Solidity types supported by the {@link EvmService}
 */
public final class Type {
    private final String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Class<?> asClass() {
        switch (name) {
            case "byte":
            case "int8":
            case "byte_list":
            case "int8_list":
            case "byte_array":
            case "int8_array":
                return Byte.class;
            case "boolean":
            case "boolean_list":
            case "boolean_array":
                return Boolean.class;
            case "character":
            case "character_list":
            case "character_array":
                return Character.class;
            case "string":
            case "string_list":
            case "address":
            case "address_list":
            case "string_array":
            case "address_array":
            case "bytes":
            case "bytes32":
            case "bytes_array":
                return String.class;
            case "enum":
            case "enum_list":
            case "enum_array":
                return Enum.class;
            case "int16":
            case "int16_list":
            case "int16_array":
                return Short.class;
            case "int24":
            case "int24_list":
            case "uint24":
            case "uint24_list":
            case "int32":
            case "int32_list":
            case "uint32":
            case "uint32_list":
            case "int24_array":
            case "uint24_array":
            case "int32_array":
            case "uint32_array":
                return Integer.class;
            case "int40":
            case "int40_list":
            case "uint40":
            case "uint40_list":
            case "int48":
            case "int48_list":
            case "uint48":
            case "uint48_list":
            case "int56":
            case "int56_list":
            case "uint56":
            case "uint56_list":
            case "int64":
            case "int64_list":
            case "uint64":
            case "uint64_list":
            case "int40_array":
            case "uint40_array":
            case "int48_array":
            case "uint48_array":
            case "int56_array":
            case "uint56_array":
            case "int64_array":
            case "uint64_array":
                return Long.class;
            case "int72":
            case "int72_list":
            case "uint72":
            case "uint72_list":
            case "int80":
            case "int80_list":
            case "uint80":
            case "uint80_list":
            case "int88":
            case "int88_list":
            case "uint104_list":
            case "uint88":
            case "uint88_list":
            case "int96":
            case "int96_list":
            case "uint96":
            case "uint96_list":
            case "int104":
            case "int104_list":
            case "uint104":
            case "int112":
            case "int112_list":
            case "uint112":
            case "uint112_list":
            case "int120":
            case "int120_list":
            case "uint120":
            case "uint120_list":
            case "int128":
            case "int128_list":
            case "uint128":
            case "uint128_list":
            case "int136":
            case "int136_list":
            case "uint136":
            case "uint136_list":
            case "int144":
            case "int144_list":
            case "uint144":
            case "uint144_list":
            case "int152":
            case "int152_list":
            case "uint152":
            case "uint152_list":
            case "int160":
            case "int160_list":
            case "uint160":
            case "uint160_list":
            case "int168":
            case "int168_list":
            case "uint168":
            case "uint168_list":
            case "int176":
            case "int176_list":
            case "uint176":
            case "uint176_list":
            case "int184":
            case "int184_list":
            case "uint184":
            case "uint184_list":
            case "int192":
            case "int192_list":
            case "uint192":
            case "uint192_list":
            case "int200":
            case "int200_list":
            case "uint200":
            case "uint200_list":
            case "int208":
            case "int208_list":
            case "uint208":
            case "uint208_list":
            case "int216":
            case "int216_list":
            case "uint216":
            case "uint216_list":
            case "int224":
            case "int224_list":
            case "uint224":
            case "uint224_list":
            case "int232":
            case "int232_list":
            case "uint232":
            case "uint232_list":
            case "int240":
            case "int240_list":
            case "uint240":
            case "uint240_list":
            case "int248":
            case "int248_list":
            case "uint248":
            case "uint248_list":
            case "int256":
            case "int256_list":
            case "uint256":
            case "uint256_list":
            case "int72_array":
            case "uint72_array":
            case "int80_array":
            case "uint80_array":
            case "int88_array":
            case "uint88_array":
            case "int96_array":
            case "uint96_array":
            case "int104_array":
            case "uint104_array":
            case "int112_array":
            case "uint112_array":
            case "int120_array":
            case "uint120_array":
            case "int128_array":
            case "uint128_array":
            case "int136_array":
            case "uint136_array":
            case "int144_array":
            case "uint144_array":
            case "int152_array":
            case "uint152_array":
            case "int160_array":
            case "uint160_array":
            case "int168_array":
            case "uint168_array":
            case "int176_array":
            case "uint176_array":
            case "int184_array":
            case "uint184_array":
            case "int192_array":
            case "uint192_array":
            case "int200_array":
            case "uint200_array":
            case "int208_array":
            case "uint208_array":
            case "int216_array":
            case "uint216_array":
            case "int224_array":
            case "uint224_array":
            case "int232_array":
            case "uint232_array":
            case "int240_array":
            case "uint240_array":
            case "int248_array":
            case "uint248_array":
            case "int256_array":
            case "uint256_array":
                return BigInteger.class;
            default:
                throw new IllegalArgumentException("Unsupported type $returnType");
        }
    }

    public boolean isList() {
        return name.endsWith("_list");
    }

    public boolean isArray() {
        return name.endsWith("_array");
    }

    public static final Type BYTE = new Type("byte");
    public static final Type BOOLEAN = new Type("boolean");
    public static final Type CHARACTER = new Type("character");
    public static final Type STRING = new Type("string");
    public static final Type ADDRESS = new Type("address");
    public static final Type ENUM = new Type("enum");
    public static final Type INT8 = new Type("int8");
    public static final Type INT16 = new Type("int16");
    public static final Type INT24 = new Type("int24");
    public static final Type INT32 = new Type("int32");
    public static final Type INT40 = new Type("int40");
    public static final Type INT48 = new Type("int48");
    public static final Type INT56 = new Type("int56");
    public static final Type INT64 = new Type("int64");
    public static final Type INT72 = new Type("int72");
    public static final Type INT80 = new Type("int80");
    public static final Type INT88 = new Type("int88");
    public static final Type INT96 = new Type("int96");
    public static final Type INT104 = new Type("int104");
    public static final Type INT112 = new Type("int112");
    public static final Type INT120 = new Type("int120");
    public static final Type INT128 = new Type("int128");
    public static final Type INT136 = new Type("int136");
    public static final Type INT144 = new Type("int144");
    public static final Type INT152 = new Type("int152");
    public static final Type INT160 = new Type("int160");
    public static final Type INT168 = new Type("int168");
    public static final Type INT176 = new Type("int176");
    public static final Type INT184 = new Type("int184");
    public static final Type INT192 = new Type("int192");
    public static final Type INT200 = new Type("int200");
    public static final Type INT208 = new Type("int208");
    public static final Type INT216 = new Type("int216");
    public static final Type INT224 = new Type("int224");
    public static final Type INT232 = new Type("int232");
    public static final Type INT240 = new Type("int240");
    public static final Type INT248 = new Type("int248");
    public static final Type INT256 = new Type("int256");
    public static final Type UINT8 = new Type("uint8");
    public static final Type UINT16 = new Type("uint16");
    public static final Type UINT24 = new Type("uint24");
    public static final Type UINT32 = new Type("uint32");
    public static final Type UINT40 = new Type("uint40");
    public static final Type UINT48 = new Type("uint48");
    public static final Type UINT56 = new Type("uint56");
    public static final Type UINT64 = new Type("uint64");
    public static final Type UINT72 = new Type("uint72");
    public static final Type UINT80 = new Type("uint80");
    public static final Type UINT88 = new Type("uint88");
    public static final Type UINT96 = new Type("uint96");
    public static final Type UINT104 = new Type("uint104");
    public static final Type UINT112 = new Type("uint112");
    public static final Type UINT120 = new Type("uint120");
    public static final Type UINT128 = new Type("uint128");
    public static final Type UINT136 = new Type("uint136");
    public static final Type UINT144 = new Type("uint144");
    public static final Type UINT152 = new Type("uint152");
    public static final Type UINT160 = new Type("uint160");
    public static final Type UINT168 = new Type("uint168");
    public static final Type UINT176 = new Type("uint176");
    public static final Type UINT184 = new Type("uint184");
    public static final Type UINT192 = new Type("uint192");
    public static final Type UINT200 = new Type("uint200");
    public static final Type UINT208 = new Type("uint208");
    public static final Type UINT216 = new Type("uint216");
    public static final Type UINT224 = new Type("uint224");
    public static final Type UINT232 = new Type("uint232");
    public static final Type UINT240 = new Type("uint240");
    public static final Type UINT248 = new Type("uint248");
    public static final Type UINT256 = new Type("uint256");
    public static final Type BYTE_ARRAY = new Type("byte_array");
    public static final Type BYTES = new Type("bytes");
    public static final Type Bytes32 = new Type("bytes32");
    public static final Type BYTES_ARRAY = new Type("bytes_array");
    public static final Type BOOLEAN_ARRAY = new Type("boolean_array");
    public static final Type CHARACTER_ARRAY = new Type("character_array");
    public static final Type STRING_ARRAY = new Type("string_array");
    public static final Type ADDRESS_ARRAY = new Type("address_array");
    public static final Type ENUM_ARRAY = new Type("enum_array");
    public static final Type INT8_ARRAY = new Type("int8_array");
    public static final Type INT16_ARRAY = new Type("int16_array");
    public static final Type INT24_ARRAY = new Type("int24_array");
    public static final Type INT32_ARRAY = new Type("int32_array");
    public static final Type INT40_ARRAY = new Type("int40_array");
    public static final Type INT48_ARRAY = new Type("int48_array");
    public static final Type INT56_ARRAY = new Type("int56_array");
    public static final Type INT64_ARRAY = new Type("int64_array");
    public static final Type INT72_ARRAY = new Type("int72_array");
    public static final Type INT80_ARRAY = new Type("int80_array");
    public static final Type INT88_ARRAY = new Type("int88_array");
    public static final Type INT96_ARRAY = new Type("int96_array");
    public static final Type INT104_ARRAY = new Type("int104_array");
    public static final Type INT112_ARRAY = new Type("int112_array");
    public static final Type INT120_ARRAY = new Type("int120_array");
    public static final Type INT128_ARRAY = new Type("int128_array");
    public static final Type INT136_ARRAY = new Type("int136_array");
    public static final Type INT144_ARRAY = new Type("int144_array");
    public static final Type INT152_ARRAY = new Type("int152_array");
    public static final Type INT160_ARRAY = new Type("int160_array");
    public static final Type INT168_ARRAY = new Type("int168_array");
    public static final Type INT176_ARRAY = new Type("int176_array");
    public static final Type INT184_ARRAY = new Type("int184_array");
    public static final Type INT192_ARRAY = new Type("int192_array");
    public static final Type INT200_ARRAY = new Type("int200_array");
    public static final Type INT208_ARRAY = new Type("int208_array");
    public static final Type INT216_ARRAY = new Type("int216_array");
    public static final Type INT224_ARRAY = new Type("int224_array");
    public static final Type INT232_ARRAY = new Type("int232_array");
    public static final Type INT240_ARRAY = new Type("int240_array");
    public static final Type INT248_ARRAY = new Type("int248_array");
    public static final Type INT256_ARRAY = new Type("int256_array");
    public static final Type UINT8_ARRAY = new Type("uint8_array");
    public static final Type UINT16_ARRAY = new Type("uint16_array");
    public static final Type UINT24_ARRAY = new Type("uint24_array");
    public static final Type UINT32_ARRAY = new Type("uint32_array");
    public static final Type UINT40_ARRAY = new Type("uint40_array");
    public static final Type UINT48_ARRAY = new Type("uint48_array");
    public static final Type UINT56_ARRAY = new Type("uint56_array");
    public static final Type UINT64_ARRAY = new Type("uint64_array");
    public static final Type UINT72_ARRAY = new Type("uint72_array");
    public static final Type UINT80_ARRAY = new Type("uint80_array");
    public static final Type UINT88_ARRAY = new Type("uint88_array");
    public static final Type UINT96_ARRAY = new Type("uint96_array");
    public static final Type UINT104_ARRAY = new Type("uint104_array");
    public static final Type UINT112_ARRAY = new Type("uint112_array");
    public static final Type UINT120_ARRAY = new Type("uint120_array");
    public static final Type UINT128_ARRAY = new Type("uint128_array");
    public static final Type UINT136_ARRAY = new Type("uint136_array");
    public static final Type UINT144_ARRAY = new Type("uint144_array");
    public static final Type UINT152_ARRAY = new Type("uint152_array");
    public static final Type UINT160_ARRAY = new Type("uint160_array");
    public static final Type UINT168_ARRAY = new Type("uint168_array");
    public static final Type UINT176_ARRAY = new Type("uint176_array");
    public static final Type UINT184_ARRAY = new Type("uint184_array");
    public static final Type UINT192_ARRAY = new Type("uint192_array");
    public static final Type UINT200_ARRAY = new Type("uint200_array");
    public static final Type UINT208_ARRAY = new Type("uint208_array");
    public static final Type UINT216_ARRAY = new Type("uint216_array");
    public static final Type UINT224_ARRAY = new Type("uint224_array");
    public static final Type UINT232_ARRAY = new Type("uint232_array");
    public static final Type UINT240_ARRAY = new Type("uint240_array");
    public static final Type UINT248_ARRAY = new Type("uint248_array");
    public static final Type UINT256_ARRAY = new Type("uint256_array");
    public static final Type BYTE_LIST = new Type("byte_list");
    public static final Type BOOLEAN_LIST = new Type("boolean_list");
    public static final Type CHARACTER_LIST = new Type("character_list");
    public static final Type STRING_LIST = new Type("string_list");
    public static final Type ADDRESS_LIST = new Type("address_list");
    public static final Type ENUM_LIST = new Type("enum_list");
    public static final Type INT8_LIST = new Type("int8_list");
    public static final Type INT16_LIST = new Type("int16_list");
    public static final Type INT24_LIST = new Type("int24_list");
    public static final Type INT32_LIST = new Type("int32_list");
    public static final Type INT40_LIST = new Type("int40_list");
    public static final Type INT48_LIST = new Type("int48_list");
    public static final Type INT56_LIST = new Type("int56_list");
    public static final Type INT64_LIST = new Type("int64_list");
    public static final Type INT72_LIST = new Type("int72_list");
    public static final Type INT80_LIST = new Type("int80_list");
    public static final Type INT88_LIST = new Type("int88_list");
    public static final Type INT96_LIST = new Type("int96_list");
    public static final Type INT104_LIST = new Type("int104_list");
    public static final Type INT112_LIST = new Type("int112_list");
    public static final Type INT120_LIST = new Type("int120_list");
    public static final Type INT128_LIST = new Type("int128_list");
    public static final Type INT136_LIST = new Type("int136_list");
    public static final Type INT144_LIST = new Type("int144_list");
    public static final Type INT152_LIST = new Type("int152_list");
    public static final Type INT160_LIST = new Type("int160_list");
    public static final Type INT168_LIST = new Type("int168_list");
    public static final Type INT176_LIST = new Type("int176_list");
    public static final Type INT184_LIST = new Type("int184_list");
    public static final Type INT192_LIST = new Type("int192_list");
    public static final Type INT200_LIST = new Type("int200_list");
    public static final Type INT208_LIST = new Type("int208_list");
    public static final Type INT216_LIST = new Type("int216_list");
    public static final Type INT224_LIST = new Type("int224_list");
    public static final Type INT232_LIST = new Type("int232_list");
    public static final Type INT240_LIST = new Type("int240_list");
    public static final Type INT248_LIST = new Type("int248_list");
    public static final Type INT256_LIST = new Type("int256_list");
    public static final Type UINT8_LIST = new Type("uint8_list");
    public static final Type UINT16_LIST = new Type("uint16_list");
    public static final Type UINT24_LIST = new Type("uint24_list");
    public static final Type UINT32_LIST = new Type("uint32_list");
    public static final Type UINT40_LIST = new Type("uint40_list");
    public static final Type UINT48_LIST = new Type("uint48_list");
    public static final Type UINT56_LIST = new Type("uint56_list");
    public static final Type UINT64_LIST = new Type("uint64_list");
    public static final Type UINT72_LIST = new Type("uint72_list");
    public static final Type UINT80_LIST = new Type("uint80_list");
    public static final Type UINT88_LIST = new Type("uint88_list");
    public static final Type UINT96_LIST = new Type("uint96_list");
    public static final Type UINT104_LIST = new Type("uint104_list");
    public static final Type UINT112_LIST = new Type("uint112_list");
    public static final Type UINT120_LIST = new Type("uint120_list");
    public static final Type UINT128_LIST = new Type("uint128_list");
    public static final Type UINT136_LIST = new Type("uint136_list");
    public static final Type UINT144_LIST = new Type("uint144_list");
    public static final Type UINT152_LIST = new Type("uint152_list");
    public static final Type UINT160_LIST = new Type("uint160_list");
    public static final Type UINT168_LIST = new Type("uint168_list");
    public static final Type UINT176_LIST = new Type("uint176_list");
    public static final Type UINT184_LIST = new Type("uint184_list");
    public static final Type UINT192_LIST = new Type("uint192_list");
    public static final Type UINT200_LIST = new Type("uint200_list");
    public static final Type UINT208_LIST = new Type("uint208_list");
    public static final Type UINT216_LIST = new Type("uint216_list");
    public static final Type UINT224_LIST = new Type("uint224_list");
    public static final Type UINT232_LIST = new Type("uint232_list");
    public static final Type UINT240_LIST = new Type("uint240_list");
    public static final Type UINT248_LIST = new Type("uint248_list");
    public static final Type UINT256_LIST = new Type("uint256_list");
}
