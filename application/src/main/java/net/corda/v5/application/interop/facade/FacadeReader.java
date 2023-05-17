package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;
import java.io.*;

public interface FacadeReader {
    /**
     * Read a {@link Facade} from the given {@link Reader}.
     * @param reader The reader to read from.
     */
    @NotNull
    Facade read(@NotNull Reader reader);

    /**
     * Read a {@link Facade} from the given {@link InputStream}.
     * @param input The input stream to read from.
     */
    @NotNull
    default Facade read(@NotNull InputStream input) throws IOException {
        return this.read(new InputStreamReader(input));
    }

    /**
     * Read a {@link Facade} from the given {@link String}.
     * @param input The string to read from.
     */
    @NotNull
    default Facade read(@NotNull String input) throws IOException {
        return this.read((InputStream) (new ByteArrayInputStream(input.getBytes())));
    }
}
