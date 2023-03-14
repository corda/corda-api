package net.corda.v5.serialization;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.types.OpaqueBytes;
import org.jetbrains.annotations.NotNull;

/**
 * A type safe wrapper around a byte array that contains a serialised object.
 */
@SuppressWarnings("unused")
@CordaSerializable
public interface SerializedBytes<T> {
//    /**
//     * Constructs a SerializedBytes holding the specified bytes.
//     * @param bytes Byte array holding serialized form of type T.
//     */
//    public SerializedBytes(@NotNull byte[] bytes) {
//        super(bytes);
//    }

    @NotNull
    byte[] getBytes();

    /**
     * Returns a text description of this SerializedBytes object
     * @return Text description of this SerializedBytes object.
     */
    @NotNull
    String getSummary();
//    {
//        return "SerializedBytes(size = " + getSize() + ')';
//    }
}
