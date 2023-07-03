package net.corda.v5.application.interop.binding;

/**
 * InteropAction represents a procedure that is communicated through the help of facades.
 * The same type, {@link InteropAction}, is returned by both facade clients and facade servers.
 * Servers return a {@link ServerResponse} wrapping the result value directly.
 */
public abstract class InteropAction<T> {
    /**
     * @return The result of carrying out the interop action.
     */
    public abstract T getResult();

    public static final class ServerResponse<T> extends InteropAction<T> {
        private final T result;

        public T getResult() {
            return this.result;
        }

        /**
         * The result of an {@link InteropAction} that has been performed by the server.
         * @param result The {@link #result} value that the server returned.
         */
        public ServerResponse(T result) {
            this.result = result;
        }
    }
}
