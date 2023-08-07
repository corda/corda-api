package net.corda.v5.ledger.utxo.uniqueness.client;

import net.corda.v5.application.crypto.DigestService;
import net.corda.v5.crypto.SecureHash;
import net.corda.v5.ledger.utxo.StateRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        final String subStringAfterDelimiter = value.substring(lastIndexOfDelimiter + 1);
        final int index = Integer.parseUnsignedInt(subStringAfterDelimiter);
        final SecureHash secureHash = mock(SecureHash.class);
        final String digestName = "SHA-256D";
        final String hexString = subStringBeforeDelimiter.substring(digestName.length() + 1);

        doReturn(digestName).when(secureHash).getAlgorithm();
        doReturn(hexString).when(secureHash).toHexString();
        doReturn(subStringBeforeDelimiter).when(secureHash).toString();
        doReturn(secureHash).when(digestService).parseSecureHash(subStringBeforeDelimiter);

        final StateRef stateRef = new StateRef(secureHash, index);

        Assertions.assertEquals(StateRef.parse(value, digestService).getTransactionId().toString(), stateRef.getTransactionId().toString());
    }
    @Test
    void parseMalformedWithZeroDelimiter() {
        final String value = "XXX";
        final String errorMessage = assertThrows(IllegalArgumentException.class, () -> {
            StateRef.parse(value, digestService);
        }).getMessage();
        Assertions.assertEquals(String.format("Failed to parse a StateRef from the specified value. At least one delimiter (%s) is expected in value: %s.", DELIMITER, value), errorMessage);
    }

    @Test
    void parseMalformedIndex() {
        final String value = ":asdf:a";
        final String errorMessage = assertThrows(IllegalArgumentException.class, () -> {
            StateRef.parse(value, digestService);
        }).getMessage();
        Assertions.assertEquals(String.format("Failed to parse a StateRef from the specified value. The index is malformed: %s.", value), errorMessage);
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


        final String errorMessage = assertThrows(IllegalArgumentException.class, () -> {
            StateRef.parse(value, digestService);
        }).getMessage();

        Assertions.assertEquals(String.format("Failed to parse a StateRef from the specified value. The transaction ID is malformed: %s.", value), errorMessage);
    }
}
