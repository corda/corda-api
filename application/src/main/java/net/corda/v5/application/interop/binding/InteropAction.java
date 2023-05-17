package net.corda.v5.application.interop.binding;

import net.corda.v5.application.interop.facade.FacadeRequest;
import net.corda.v5.application.interop.facade.FacadeResponse;

/**
 * InteropAction represents a procedure that is communicated through the help of facades.
 * The same type, {@link InteropAction}, is returned by both facade clients and facade servers.
 * Clients will return a {@link ClientAction}, representing an interop request to be performed; when {@link #getResult result} is called, the
 * request is carried out and the result obtained.
 * Servers will return a {@link ServerResponse}, wrapping the result value directly.
 */
public abstract class InteropAction<T> {
    /**
     * @return The result of carrying out the interop action.
     */
    public abstract T getResult();

    public static final class ClientAction<T> extends InteropAction<T> {
        private final FacadeRequest request;
        private final Processable processor;
        private final Interpretable<T> responseInterpreter;

        /**
         * An interop action that has not yet been carried out, but will be when the caller requests the {@link #getResult result}.
         * @param request The {@link FacadeRequest} to send to the server
         * @param processor An object that knows how to send a {@link FacadeRequest} to the server and obtain a {@link FacadeResponse}
         * @param responseInterpreter An object that knows how to translate a {@link FacadeResponse} into the {@link #getResult result} type
         */
        public ClientAction(FacadeRequest request, Processable processor, Interpretable<T> responseInterpreter) {
            this.request = request;
            this.processor = processor;
            this.responseInterpreter = responseInterpreter;
        }

        @Override
        public T getResult() {
            return responseInterpreter.interpret(processor.process(request));
        }
    }

    public static final class ServerResponse<T> extends InteropAction<T> {
        private final T result;

        public T getResult() {
            return this.result;
        }

        /**
         * The result of an {@link InteropAction} that has been performed by the server.
         * @param result The {@link #result} value that the server returned
         */
        public ServerResponse(T result) {
            this.result = result;
        }
    }
}
