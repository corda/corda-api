package net.corda.v5.application.marshalling.json

import net.corda.v5.application.marshalling.JsonMarshallingService
import net.corda.v5.base.annotations.DoNotImplement
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Every [JsonNodeReader] can be thought of as a node in the tree of Json objects being read. Each node in the tree,
 * including the root node represents a value in Json. Every node is referenced in its parent by a field name, except
 * the root node which has no parent. Thus the field name is a property of the parent not the node itself. This allows
 * writers of deserializers to traverse the tree and extract values of different types in order to reconstruct the class
 * which was originally serialized.
 *
 * Where the value type of a [JsonNodeReader] is not a Json object or array, that [JsonNodeReader] is a leaf in the tree
 * (no children). Where the value type of a [JsonNodeReader] is a Json object, that object is then represented by
 * another [JsonNodeReader]. Where the value type of a [JsonNodeReader] is an array, that array is then represented by
 * an iterator of [JsonNodeReader] objects. In other words, array [JsonNodeReader]s are nodes with multiple children,
 * whilst object [JsonNodeReader]s are nodes with a single child.
 */
@Suppress("TooManyFunctions")
@DoNotImplement
interface JsonNodeReader {
    /**
     * Get an iterator to all field names and values.
     *
     * @return An iterator to a map entry consisting of field name and value, where value is represented by a
     * [JsonNodeReader].
     */
    fun fields(): Iterator<Map.Entry<String, JsonNodeReader>>

    /**
     * Determine whether this [JsonNodeReader] has a field specified.
     *
     * @param fieldName The name of the field.
     *
     * @return true if the field exists, false otherwise.
     */
    fun hasField(fieldName: String): Boolean

    /**
     * Get the value of a field specified by name.
     *
     * @param fieldName The name of the field.
     *
     * @return The field's value represented by a [JsonNodeReader] or null if no such field exists.
     */
    fun getField(fieldName: String): JsonNodeReader?

    /**
     * The type of this node.
     *
     * @return The type as a value from [JsonNodeReaderType].
     */
    fun getType(): JsonNodeReaderType

    /**
     * Returns true if this node represents a Boolean in the Json.
     *
     * @return true if a boolean, otherwise false.
     */
    fun isBoolean(): Boolean

    /**
     * This method attempts to convert the underlying Json for this node to a Boolean type.
     *
     * @return the value of the underlying Json if it is a Boolean type, or false if it is a different type.
     */
    fun asBoolean(): Boolean

    /**
     * This method attempts to convert the underlying Json for this node to a Boolean type.
     *
     * @param defaultValue The value to return if the underlying Json type is not a Boolean.
     *
     * @return the value of the underlying Json if it is a Boolean type, or [defaultValue] if it is a different type.
     */
    fun asBoolean(defaultValue: Boolean): Boolean

    /**
     * Returns true if this node represents a Number in the Json.
     *
     * @return true if a Number, otherwise false.
     */
    fun isNumber(): Boolean

    /**
     * This method attempts to convert the underlying Json for this node to a Number type.
     *
     * @return the value of the underlying Json if it is a Number type, or null if it is a different type.
     */
    fun asNumber(): Number?

    /**
     * This method attempts to convert the underlying Json for this node to a Number type.
     *
     * @param defaultValue The value to return if the underlying Json type is not a Number.
     *
     * @return the value of the underlying Json if it is a Number type, or [defaultValue] if it is a different type.
     */
    fun asNumber(defaultValue: Number): Number

    /**
     * Returns true if this node represents a Number in the underlying Json which is not an integer.
     *
     * @return true if this is floating point number.
     */
    fun isFloatingPointNumber(): Boolean

    /**
     * Returns true if this node represents a Double in the Json. This is not the same as asking if the number can be
     * converted to a double, see [asDouble] for more information.
     *
     * @return true if a Double, otherwise false.
     */
    fun isDouble(): Boolean

    /**
     * If this node represents a Json Number type, this method returns the value as a Double. Otherwise it returns 0.0.
     * To convert other types to Double see [asDouble]. When the underlying Json represents a number which does not fit
     * in a Double type, it is converted using Java coercion.
     *
     * @return the value of the underlying Json Number as a Double or 0.0 if the Json is not a Number.
     */
    fun doubleValue(): Double

    /**
     * This method attempts to convert the underlying Json for this node to a Double type using default Java rules.
     * Booleans are converted to 0.0 for false and 1.0 for true. Strings are also parsed and converted as per standard
     * Java rules. Where conversion is not possible 0.0 is returned.
     *
     * @return the value of the underlying Json as a Double or 0.0 if that is not possible.
     */
    fun asDouble(): Double

    /**
     * This method attempts to convert the underlying Json for this node to a Double type using default Java rules.
     * Booleans are converted to 0.0 for false and 1.0 for true. Strings are also parsed and converted as per standard
     * coercion Java rules. Where conversion is not possible the supplied default is returned.
     *
     * @param defaultValue The value to return if the underlying Json cannot be converted to a Double.
     *
     * @return the value of the underlying Json as a Double or [defaultValue] if that is not possible.
     */
    fun asDouble(defaultValue: Double): Double

    /**
     * Returns true if this node represents a Float in the Json. Note that there is no asFloat support, [asDouble]
     * can be used to convert Json which is not a Float type to a floating point number.
     *
     * @return true if a Double, otherwise false.
     */
    fun isFloat(): Boolean

    /**
     * If this node represents a Json Number type, this method returns the value as a Float. Note that there is no
     * asFloat support, [asDouble] can be used to convert Json which is not a Float type to a floating point number.
     * When the underlying Json represents a number which does not fit in a Float type, it is converted using Java
     * coercion.
     *
     * @return the value of the underlying Json Number as a Double or 0.0 if the Json is not a Number.
     */
    fun floatValue(): Float

    /**
     * Returns true if this node represents an Int in the Json. This is not the same as asking if the number can be
     * converted to an Int, see [asInt] for more information.
     *
     * @return true if an Int, otherwise false.
     */
    fun isInt(): Boolean

    /**
     * Returns true if this node represents a Number in the underlying Json and can be converted to an Int. It includes
     * floating point numbers which have an integral part which does not overflow an Int. See also
     * [isFloatingPointNumber].
     *
     * @return true if can be converted, otherwise false.
     */
    fun canConvertToInt(): Boolean

    /**
     * This method attempts to convert the underlying Json for this node to an Int type using default Java rules.
     * Booleans are converted to 0 for false and 1 for true. Strings are also parsed and converted as per standard
     * Java rules. Where conversion is not possible 0 is returned.
     *
     * @return the value of the underlying Json as an Int or 0 if that is not possible.
     */
    fun asInt(): Int

    /**
     * This method attempts to convert the underlying Json for this node to an Int type using default Java rules.
     * Booleans are converted to 0 for false and 1 for true. Strings are also parsed and converted as per standard
     * coercion Java rules. Where conversion is not possible the supplied default is returned.
     *
     * @param defaultValue The value to return if the underlying Json cannot be converted to an Int.
     *
     * @return the value of the underlying Json as an Int or [defaultValue] if that is not possible.
     */
    fun asInt(defaultValue: Int): Int

    /**
     * Returns true if this node represents a Long in the Json. This is not the same as asking if the number can be
     * converted to a Long, see [asLong] for more information.
     *
     * @return true if a Long, otherwise false.
     */
    fun isLong(): Boolean

    /**
     * Returns true if this node represents a Number in the underlying Json and can be converted to a Long. It includes
     * floating point numbers which have an integral part which does not overflow a Long. See also
     * [isFloatingPointNumber].
     *
     * @return true if can be converted, otherwise false.
     */
    fun canConvertToLong(): Boolean

    /**
     * This method attempts to convert the underlying Json for this node to a Long type using default Java rules.
     * Booleans are converted to 0 for false and 1 for true. Strings are also parsed and converted as per standard
     * coercion Java rules. Where conversion is not possible 0 is returned.
     *
     * @return the value of the underlying Json as a Long or 0 if that is not possible.
     */
    fun asLong(): Long

    /**
     * This method attempts to convert the underlying Json for this node to a Long type using default Java rules.
     * Booleans are converted to 0 for false and 1 for true. Strings are also parsed and converted as per standard
     * Java rules. Where conversion is not possible the supplied default is returned.
     *
     * @param defaultValue The value to return if the underlying Json cannot be converted to a Long.
     *
     * @return the value of the underlying Json as a Long or [defaultValue] if that is not possible.
     */
    fun asLong(defaultValue: Long): Long

    /**
     * Returns true if this node represents a Short in the Json. Note that there is no asShort support, [asInt]
     * can be used to convert Json which is not a Short type to an integer.
     *
     * @return true if a Short, otherwise false.
     */
    fun isShort(): Boolean

    /**
     * If this node represents a Json Number type, this method returns the value as a Short. Note that there is no
     * asShort support, [asInt] can be used to convert Json which is not a Short type to an integer.
     * When the underlying Json represents a number which does not fit in a Short type, it is converted using Java
     * coercion.
     *
     * @return the value of the underlying Json Number as a Short or 0 if the Json is not a Number.
     */
    fun shortValue(): Short

    /**
     * Returns true if this node represents a BigInteger in the Json.
     *
     * @return true if a BigInteger, otherwise false.
     */
    fun isBigInteger(): Boolean

    /**
     * If this node represents a Json Number type, this method returns the value as a BigInteger. When the underlying
     * Json represents a number which does not fit in a Short type, it is converted using Java coercion.
     *
     * @return the value of the underlying Json Number as a BigInteger or 0 if the Json is not a Number.
     */
    fun bigIntegerValue(): BigInteger

    /**
     * Returns true if this node represents a BigDecimal in the Json.
     *
     * @return true if a BigInteger, otherwise false.
     */
    fun isBigDecimnal(): Boolean

    /**
     * If this node represents a Json Number type, this method returns the value as a BigDecimal.
     *
     * @return the value of the underlying Json Number as a BigDecimal or 0 if the Json is not a Number.
     */
    fun bigDecimalValue(): BigDecimal

    /**
     * Returns true if this node represents a string in the Json. If the string is a base64 encoded value this method
     * will return false. See [isBinary].
     *
     * @return true if a String, otherwise false.
     */
    fun isText(): Boolean

    /**
     * Returns the value of this node as a string if the underlying Json value is not an array or object. If it is an
     * array or object an empty String is returned.
     *
     * @return the value as a String.
     */
    fun asText(): String

    /**
     * Returns the value of this node as a String if the underlying Json value is not an array or object. If it is an
     * array or object an empty String is returned.
     *
     * @param defaultValue The default value to return if this node is an array or object type.
     *
     * @return the value as a String or defaultValue
     */
    fun asText(defaultValue: String): String

    /**
     * Returns true if this node represents an object in the Json.
     *
     * @return true if a Json object, false otherwise
     */
    fun isObject(): Boolean

    /**
     * Returns another node to represent a Json object if the underlying Json represented by this node is a Json object.
     * If this node does not represent a Json object, returns null.
     *
     * @return a [JsonNodeReader] representing the object or null if this node does not represent a Json object.
     */
    fun asObject(): JsonNodeReader

    /**
     * Returns true if this node represents a string in the Json which contains a base64 encoded byte array.
     *
     * @return true if this node represents a string in the Json which contains a base64 encoded byte array.
     */
    fun isBinary(): Boolean

    /**
     * Returns a byte array if this node represents a string in the Json which contains a base64 encoded byte array.
     *
     * @return the base64 decoded byte array if this node represents that, or null if it represents other types.
     */
    fun binaryValue(): ByteArray?

    /**
     * Returns true if this node represents an array in the Json.
     *
     * @return true if a Json array, false otherwise
     */
    fun isArray(): Boolean

    /**
     * Returns an iterator to [JsonNodeReader]s each of which represents the next value in the array.
     *
     * @return an iterator or null if this node type does not represent a Json array.
     */
    fun asArray(): Iterator<JsonNodeReader>?

    /**
     * Returns true if this node represents the value of "null" in the underlying Json.
     *
     * @return true if null, otherwise false.
     */
    fun isNull(): Boolean

    /**
     * Parse this [JsonNodeReader] to strongly typed objects. Will deserialize using default deserializers or any custom
     * Json deserializers registered. This method can be used if during custom deserialization of one class type, the
     * deserializer expects a field's value to contain an object which can be deserialized to another class type which
     * is already known to either be default deserializable, or for which other custom deserializers are registered.
     * It is the equivalent of calling the [JsonMarshallingService] parse method on a Json string representation of this
     * node.
     *
     * @param clazz The type to try and parse this node into.
     *
     * @return An instance of the required type or null if this node does not represent a Json serialized version of
     * that type.
     */
    fun <T> parse(clazz: Class<T>): T?
}

/**
 * Parse this [JsonNodeReader] to strongly typed objects. Will deserialize using default deserializers or any custom
 * Json deserializers registered. This method can be used if during custom deserialization of one class type, the
 * deserializer expects a field's value to contain an object which can be deserialized to another class type which
 * is already known to either be default deserializable, or for which other custom deserializers are registered.
 * It is the equivalent of calling the [JsonMarshallingService] parse method on a Json string representation of this
 * node.
 *
 * @return An instance of the required type or null if this node does not represent a Json serialized version of
 * that type.
 */
inline fun <reified T> JsonNodeReader.parse(): T? = this.parse(T::class.java)
