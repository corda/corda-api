package net.corda.v5.ledger.utxo;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.crypto.SecureHash;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


public class StateRefParseTest {
    private static final String DELIMITER = ":";
    private final DigestService digestService = mock(DigestService.class);

    @Test
    void parseValidValue() {
        final String value = "SHA-256D:ED87C7285E1E34BF5E46302086F76317ACE9B17AEF7BD086EE09A5ACBD17CEA4:0";
        final int lastIndexOfDelimiter = value.lastIndexOf(DELIMITER);
        final String subStringBeforeDelimiter = value.substring(0, lastIndexOfDelimiter);
        final SecureHash secureHash = mock(SecureHash.class);

        doReturn(secureHash).when(digestService).parseSecureHash(subStringBeforeDelimiter);

        final StateRef stateRef = new StateRef(secureHash, Integer.parseUnsignedInt(value.substring(lastIndexOfDelimiter + 1)));

        assertEquals(StateRef.parse(value, digestService).getTransactionId().toString(), stateRef.getTransactionId().toString());
    }

    @Test
    void parseMalformedWithZeroDelimiter() {
        final String value = "XXX";
        final String errorMessage = assertThrows(IllegalArgumentException.class, () -> StateRef.parse(value, digestService)).getMessage();
        assertEquals(String.format("Failed to parse a StateRef from the specified value. At least one delimiter (%s) is expected in value: %s.", DELIMITER, value), errorMessage);
    }

    @Test
    void parseMalformedIndex() {
        final String value = ":asdf:a";
        final String errorMessage = assertThrows(IllegalArgumentException.class, () -> StateRef.parse(value, digestService)).getMessage();
        assertEquals(String.format("Failed to parse a StateRef from the specified value. The index is malformed: %s.", value), errorMessage);
    }

    @Test
    void parseMalformedTransactionId() {
        final String value = "SHA-256D:asdf:0";
        final String valueBeforeDelimiter = value.substring(0, value.lastIndexOf(DELIMITER));
        final String digestName = "SHA-256D";
        final String hexString = valueBeforeDelimiter.substring(digestName.length() + 1);
        final Integer digestHexStringLength = 64;

        doThrow(new IllegalArgumentException(String.format("Digest algorithm's: \"%s\" required hex string length: %s " +
                "is not met by hex string: \"%s\"", digestName, digestHexStringLength, hexString))).when(digestService).parseSecureHash(valueBeforeDelimiter);


        final String errorMessage = assertThrows(IllegalArgumentException.class, () -> StateRef.parse(value, digestService)).getMessage();

        assertEquals(String.format("Failed to parse a StateRef from the specified value. The transaction ID is malformed: %s.", value), errorMessage);
    }

    @ParameterizedTest(name = "Parse large state ref index and reparse into state ref {0}")
    @MethodSource("stateRefIndexes")
    void parseLargeValueAndReparse(int index) {
        final String value = "SHA-256D:ED87C7285E1E34BF5E46302086F76317ACE9B17AEF7BD086EE09A5ACBD17CEA4:" + index;
        final int lastIndexOfDelimiter = value.lastIndexOf(DELIMITER);
        final String subStringBeforeDelimiter = value.substring(0, lastIndexOfDelimiter);
        final SecureHash secureHash = mock(SecureHash.class);

        doReturn(secureHash).when(digestService).parseSecureHash(subStringBeforeDelimiter);
        doReturn(subStringBeforeDelimiter).when(secureHash).toString();

        final StateRef stateRef = StateRef.parse(value, digestService);

        assertEquals(stateRef, StateRef.parse(stateRef.toString(), digestService));
    }

    public static Stream<Arguments> stateRefIndexes() {
        return Stream.of(
                Arguments.of(1000),
                Arguments.of(1001),
                Arguments.of(99999999),
                Arguments.of(Integer.MAX_VALUE)
        );
    }
}
