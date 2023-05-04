package net.corda.v5.application.persistence;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Used to build a Query that supports limit and offset.
 *
 * @param <R> The type of the results.
 */
public interface PagedQuery<R> {

    /**
     * Sets the maximum number of results to return.
     * <p>
     * If no limit is set, all records will be returned.
     *
     * @param limit The maximum number of results to return.
     *
     * @return The same {@link PagedQuery} instance.
     *
     * @throws IllegalArgumentException If {@code limit} is negative.
     */
    @NotNull
    PagedQuery<R> setLimit(int limit);

    /**
     * Sets the index of the first result in the query to return.
     * <p>
     * A default of `0` will be used if it is not set.
     *
     * @param offset The index of the first result in the query to return.
     *
     * @return The same {@link PagedQuery} instance.
     *
     * @throws IllegalArgumentException If {@code offset} is negative.
     */
    @NotNull
    PagedQuery<R> setOffset(int offset);

    /**
     * Executes the {@link PagedQuery}.
     *
     * @return A {@link ResultSet} containing the results of executing the query.
     *
     * @throws CordaPersistenceException If there is an error executing the query.
     */
    @Suspendable
    @NotNull
    ResultSet<R> execute();

    /**
     * A result set containing the results of calling {@link PagedQuery#execute()}.
     *
     * @param <R> The type of the results contained in the result set.
     */
    interface ResultSet<R> {

        /**
         * Extracts the results of a {@link ResultSet} from a previously executed query.
         * <p>
         * This method does not execute a query itself, call {@link PagedQuery#execute()} to execute a query and
         * generate a {@link ResultSet}.
         *
         * @return The results contained in the result set.
         */
        @NotNull
        List<R> getResults();

        /**
         * Returns {@code true} if the {@link ResultSet} has more results to retrieve.
         * <p>
         * Modifying the {@link PagedQuery} linked to the {@link ResultSet} does not affect {@link ResultSet}'s
         * behaviour when calling {@link ResultSet#next()}.
         *
         * @return {@code true} if the {@link ResultSet} has more results to retrieve.
         *
         * @return
         */
        boolean hasNext();

        /**
         * Executes the query linked to the {@link ResultSet} to retrieve the next page of results.
         * <p>
         * The offset of the executed query increments each time {@link ResultSet#next()} is called, increasing by the
         * value of the query's limit.
         * <p>
         * Modifying the {@link PagedQuery} linked to the {@link ResultSet} does not affect {@link ResultSet}'s
         * behaviour when calling {@link ResultSet#next()}.
         *
         * @return The next page of results.
         * @throws NoSuchElementException If the {@link ResultSet} has no more results to retrieve.
         */
        @Suspendable
        @NotNull
        List<R> next();
    }
}
