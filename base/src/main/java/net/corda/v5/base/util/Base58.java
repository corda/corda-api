package net.corda.v5.base.util;


import java.math.BigInteger;
import java.util.Arrays;

/**
 * Base58 is a way to encode Bitcoin addresses (or arbitrary data) as alphanumeric strings.
 * <p>
 * Note that this is not the same Base58 as used by Flickr, which may be referenced online.
 * <p>
 * Satoshi explains: Why Base58 instead of standard Base64 encoding?
 * <ul>
 * <li>Don't want 0OIl characters that look the same in some fonts and
 * could be used to create visually identical looking account numbers.</li>
 * <li>A string with non-alphanumeric characters is not as easily accepted as an account number.</li>
 * <li>E-mail usually won't line-break if there's no punctuation to break at.</li>
 * <li>Doubleclicking selects the whole number as one word if it's all alphanumeric.</li>
 * </ul>
 * <p>
 * However, note that the encoding/decoding runs in O(n&sup2;) time, so it is not useful for large data.
 * <p>
 * The basic idea of the encoding is to treat the data bytes as a large number represented using
 * Base256 digits, convert the number to be represented using Base58 digits, preserve the exact
 * number of leading zeros (which are otherwise lost during the mathematical operations on the
 * numbers), and finally represent the resulting Base58 digits as alphanumeric ASCII characters.
 * <p>
 * Note that this class originally comes from the Apache licensed bitcoinj library. The original author of this code is the
 * same as the original author of the R3 repository.
 */
public class Base58 {
    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char ENCODED_ZERO = ALPHABET[0];
    private static final int[] INDEXES = new int[128];

    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    /**
     * Encodes the given bytes as a Base58 string (no checksum is appended).
     *
     * @param input The bytes to encode.
     * @return The Base58 encoded string.
     */
    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }
        // Count leading zeros.
        int zeros = 0;
        while (zeros < input.length && input[zeros] == 0) {
            ++zeros;
        }
        // Convert Base256 digits to Base58 digits (plus conversion to ASCII characters).
        input = Arrays.copyOf(input, input.length); // since we modify it in place
        char[] encoded = new char[input.length * 2]; // upper bound
        int outputStart = encoded.length;
        for (int inputStart = zeros; inputStart < input.length; ) {
            encoded[--outputStart] = ALPHABET[divmod(input, inputStart, 256, 58)];
            if (input[inputStart] == 0) {
                ++inputStart; // Skip leading zeros for optimization.
            }
        }
        // Preserve exactly as many leading encoded zeros in output as there were leading zeros in input.
        while (outputStart < encoded.length && encoded[outputStart] == ENCODED_ZERO) {
            ++outputStart;
        }
        while (--zeros >= 0) {
            encoded[--outputStart] = ENCODED_ZERO;
        }
        // Return encoded string (including encoded leading zeros).
        return new String(encoded, outputStart, encoded.length - outputStart);
    }

    /**
     * Decodes the given Base58 string into the original data bytes.
     *
     * @param input The Base58 encoded string to decode.
     * @return The decoded data bytes.
     * @throws IllegalArgumentException If the given string is not a valid Base58 string.
     */
    public static byte[] decode(String input) throws IllegalArgumentException {
        if (input.isEmpty()) {
            return new byte[0];
        }
        // Convert the Base58 encoded ASCII chars to a Base58 byte sequence (Base58 digits).
        byte[] input58 = new byte[input.length()];
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            int digit = c < 128 ? INDEXES[c] : -1;
            if (digit < 0) {
                throw new IllegalArgumentException("Illegal character " + c + " at position " + i);
            }
            input58[i] = (byte) digit;
        }
        // Count leading zeros.
        int zeros = 0;
        while (zeros < input58.length && input58[zeros] == 0) {
            ++zeros;
        }
        // Convert Base58 digits to Base256 digits.
        byte[] decoded = new byte[input.length()];
        int outputStart = decoded.length;
        for (int inputStart = zeros; inputStart < input58.length; ) {
            decoded[--outputStart] = divmod(input58, inputStart, 58, 256);
            if (input58[inputStart] == 0) {
                ++inputStart; // Skip leading zeros for optimization.
            }
        }
        // Ignore extra leading zeroes that were added during the calculation.
        while (outputStart < decoded.length && decoded[outputStart] == 0) {
            ++outputStart;
        }
        // Return decoded data (including original number of leading zeros).
        return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
    }

    public static BigInteger decodeToBigInteger(String input) throws IllegalArgumentException {
        return new BigInteger(1, decode(input));
    }

    /**
     * Divides a number, represented as an array of bytes each containing a single digit
     * in the specified base, by the given divisor. The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number     The number to divide.
     * @param firstDigit The index within the array of the first non-zero digit
     *                   (this is used for optimization by skipping the leading zeros).
     * @param base       The base in which the number's digits are represented (up to 256).
     * @param divisor    The number to divide by (up to 256).
     * @return The remainder of the division operation.
     */
    private static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
        // This is long division which accounts for the base of the input digits.
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = (int) number[i] & 0xFF;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }
}
