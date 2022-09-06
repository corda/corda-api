package net.corda.v5.application.marshalling;

import net.corda.v5.application.flows.RPCStartableFlow;
import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link MarshallingService} is an abstract interface for marshalling to and from formatted string data.
 * Corda provides specialized implementations of the marshalling services for converting data in different string
 * formats.
 *
 * Example usage:
 * @see RPCStartableFlow
 */
@DoNotImplement
public interface MarshallingService {

    /**
     * Format the input data into the service's output format.
     *
     * @param data The object to convert on input.
     *
     * @return String representation of the data formatted according to the provided service.
     */
    @NotNull
    String format(@NotNull Object data);

    /**
     * Parse input strings to strongly typed objects.
     *
     * This method will throw an exception if the provided string does not conform to the expected format of the
     * service.
     *
     * @param input The input string to parse.
     * @param clazz The type to try and parse the data into.
     *
     * @return An instance of the required type containing the input data.
     */
    <T> T parse(@NotNull String input, @NotNull Class<T> clazz);

    /**
     * Deserializes the [input] into a list of instances of [T].
     *
     * @param input The input string to parse.
     * @param clazz The [Class] type to parse into.
     *
     * @return A new list of [T].
     */
    @NotNull
    <T> List<T> parseList(@NotNull String input, @NotNull Class<T> clazz);
}

/**
 * Parse input strings to strongly typed objects.
 *
 * This method will throw an exception if the provided string does not conform to the expected format of the service.
 *
 * @param input The input string to parse.
 *
 * @return An instance of the required type containing the input data.
 */
//inline fun <reified T> MarshallingService.parse(input: String): T {
//    return this.parse(input, T::class.java)
//}

/**
 * Deserializes the [input] into a list of instances of [T].
 *
 * @param input The input string to deserialize.
 *
 * @return A new list of [T].
 */
//inline fun <reified T> MarshallingService.parseList(input: String): List<T> {
//    return parseList(input, T::class.java)
//}
