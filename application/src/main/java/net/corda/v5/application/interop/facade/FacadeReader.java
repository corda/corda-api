package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;
import java.io.*;

public interface FacadeReader {
    /**
     * Read a [Facade] from the given [Reader].
     *
     * @param reader The reader to read from.
     */
    @NotNull
    Facade read(@NotNull Reader reader);

    /**
     * Read a [Facade] from the given [InputStream].
     *
     * @param input The input stream to read from.
     */
    @NotNull
    default Facade read(@NotNull InputStream input) throws IOException {
        return this.read(new InputStreamReader(input));
    }

    /**
     * Read a [Facade] from the given [String].
     *
     * @param input The string to read from.
     */
    @NotNull
    default Facade read(@NotNull String input) throws IOException {
        return this.read((InputStream) (new ByteArrayInputStream(input.getBytes())));
    }
}
