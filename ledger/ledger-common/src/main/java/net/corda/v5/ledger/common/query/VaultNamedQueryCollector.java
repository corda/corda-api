package net.corda.v5.ledger.common.query;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * A collector that is applied to the result set returned after executing the named query.
 * @param <R> Type of the end result
 * @param <T> Type of the original result set
 */
public interface VaultNamedQueryCollector<R, T> {

    /**
     * @param resultSet The original result set that was returned by the named query execution
     * @param parameters Parameters that were present in the query
     * @return A result that the original result set was collected into
     */
    @Suspendable
    @NotNull
    Result<R> collect(@NotNull List<T> resultSet, @NotNull Map<String, Object> parameters);

    /**
     * Representation of a "collected" result set that also contains a flag that shows whether the result set is finished
     * or there are still elements in the original result set.
     * @param <R> Type of the records stored inside this result set
     */
    class Result<R> {
        private List<R> results;
        private Boolean isDone;

        /**
         * @return The records in the result set
         */
        @NotNull
        public List<R> getResults() {
            return results;
        }

        /**
         * @return Whether the result set has finished or not
         */
        @NotNull
        public Boolean getDone() {
            return isDone;
        }

        public Result(@NotNull List<R> results, @NotNull Boolean isDone) {
            this.results = results;
            this.isDone = isDone;
        }
    }
}
