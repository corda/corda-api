package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;

import java.io.*;

//TODO add implementation for this interface in corda-runtime-os
public interface FacadeReader {
    /**
     * Read a [KotlinFacade] from the given [Reader].
     *
     * @param reader The reader to read from.
     */
    @NotNull
    Facade read(@NotNull Reader reader);

    /**
     * Read a [KotlinFacade] from the given [InputStream].
     *
     * @param input The input stream to read from.
     */
    @NotNull
    default Facade read(@NotNull InputStream input) throws IOException {
        return this.read(new InputStreamReader(input));
    }

    /**
     * Read a [KotlinFacade] from the given [String].
     *
     * @param input The string to read from.
     */
    @NotNull
    default Facade read(@NotNull String input) throws IOException {
        return this.read((InputStream) (new ByteArrayInputStream(input.getBytes())));
    }
}
