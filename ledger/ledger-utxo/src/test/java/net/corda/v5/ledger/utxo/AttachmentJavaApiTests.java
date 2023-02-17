package net.corda.v5.ledger.utxo;

import net.corda.v5.crypto.SecureHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.List;
import java.util.zip.ZipInputStream;

public final class AttachmentJavaApiTests extends AbstractMockTestHarness {

    public AttachmentJavaApiTests() throws IOException {
    }

    @Test
    public void getIdShouldReturnTheExpectedValue() {
        SecureHash value = attachment.getId();
        Assertions.assertEquals(hash, value);
    }

    @Test
    public void getSizeShouldReturnTheExpectedValue() {
        int value = attachment.getSize();
        Assertions.assertEquals(0, value);
    }

    @Test
    public void getSignatoriesShouldReturnTheExpectedValue() {
        List<PublicKey> value = attachment.getSignatories();
        Assertions.assertEquals(keys, value);
    }

    @Test
    public void extractFileShouldBeCallable() throws FileNotFoundException {
        attachment.extractFile("", outputStream);
    }

    @Test
    public void openShouldReturnTheExpectedValue() throws IOException {
        InputStream value = attachment.open();
        Assertions.assertEquals(inputStream, value);
    }

    @Test
    public void openAsJarShouldReturnTheExpectedValue() throws IOException {
        ZipInputStream value = attachment.openAsZip();
        Assertions.assertEquals(zipInputStream, value);
    }
}
