package net.corda.v5.ledger.transactions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrivacySaltJavaApiTest {

    private final PrivacySalt privacySaltA = new PrivacySalt();
    private final PrivacySalt privacySaltB = new PrivacySalt(55);
    private final byte[] bytes = "6D1687C143DF792A011A1E80670A4E4E".getBytes();
    private final PrivacySalt privacySaltC = new PrivacySalt(bytes);

    @Test
    public void getOffset() {
        int result = privacySaltA.getOffset();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void getSize() {
        int result = privacySaltB.getSize();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(55);
    }

    @Test
    public void getBytes() {
        byte[] result = privacySaltC.getBytes();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(bytes);
    }
}
