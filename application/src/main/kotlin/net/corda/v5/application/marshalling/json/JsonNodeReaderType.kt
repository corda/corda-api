package net.corda.v5.application.marshalling.json

/**
 * Possible types of nodes in Json.
 */
enum class JsonNodeReaderType {
    /**
     * An array of objects, values, or other arrays.
     */
    ARRAY,

    /**
     * Binary data as bytes.
     */
    BINARY,

    /**
     * Boolean, either true or false.
     */
    BOOLEAN,

    /**
     * The Json representation of "null".
     */
    NULL,

    /**
     * A number, a superset of all number types, integer, floating point, double, long etc.
     */
    NUMBER,

    /**
     * A Json object.
     */
    OBJECT,

    /**
     * A Java object (POJO) which was serialized and can be extracted as an already deserialized Java object.
     */
    POJO,

    /**
     * Text.
     */
    STRING
}
