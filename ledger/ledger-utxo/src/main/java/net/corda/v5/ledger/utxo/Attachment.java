package net.corda.v5.ledger.utxo;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Defines a data attachment that can be referenced within a transaction.
 * <p>
 * Attachments are ZIP, or optionally a signed JAR file that contains one or more files of public, static data that
 * can be referenced from a transaction, and utilised within a contract. Examples of attachments include:
 * <p>
 * - Calendar data
 * - Fixes (e.g. LIBOR)
 * - Smart contract code
 * - Legal documents
 * - Facts generated by oracles.
 */
@DoNotImplement
@CordaSerializable
public interface Attachment {

    /**
     * Gets the id The unique hash of the attachment.
     *
     * @return Returns the id The unique hash of the attachment.
     */
    @NotNull
    SecureHash getId();

    /**
     * Gets the size of the attachment in bytes.
     *
     * @return Returns the size of the attachment in bytes.
     */
    int getSize();

    /**
     * Gets the keys that have been used to sign the attachment, or empty if the attachment does not require signing.
     *
     * @return Returns the keys that have been used to sign the attachment, or empty if the attachment does not require signing.
     */
    @NotNull
    List<PublicKey> getSignatories();

    /**
     * Finds the specified file (case insensitively) within the attachment and copies it to the specified output stream.
     *
     * @param path   The path of the file to find within the attachment.
     * @param output The output stream where the file should be copied to.
     * @throws FileNotFoundException if the specified path does not exist in the attachment.
     */
    void extractFile(@NotNull String path, @NotNull OutputStream output) throws FileNotFoundException;

    /**
     * Opens the current {@link Attachment}.
     *
     * @return Returns the {@link InputStream} for the current {@link Attachment}.
     * @throws IOException if the file could not be opened correctly.
     */
    @NotNull
    InputStream open() throws IOException;

    /**
     * Opens the current {@link Attachment} as a ZIP
     *
     * @return Returns the {@link ZipInputStream} for the current {@link Attachment}.
     */
    @NotNull
    default ZipInputStream openAsZip() throws IOException {
        return new ZipInputStream(open());
    }
}
