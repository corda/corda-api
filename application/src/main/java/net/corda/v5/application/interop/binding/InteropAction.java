package net.corda.v5.application.interop.binding;

import net.corda.v5.application.interop.facade.FacadeRequest;

/**
 * The same type, [InteropAction], is returned by both Facade clients and Facade servers.
 * Clients will return a [ClientAction], representing an interop request to be performed; when [result] is called, the
 * request is carried out and the result obtained.
 * Servers will return a [ServerResponse], wrapping the result value directly.
 */
public abstract class InteropAction<T> {
    public abstract T getResult();

    public static final class ClientAction<T> extends InteropAction<T> {
        private final FacadeRequest request;
        private final Processable processor;
        private final Interpretable<T> responseInterpreter;

        /**
         * An interop action that has not yet been carried out, but will be when the caller requests the [result].
         * @param request The [FacadeRequest] to send to the server
         * @param processor An object that knows how to send a [FacadeRequest] to the server and obtain a [FacadeResponse]
         * @param responseInterpreter An object that knows how to translate a [FacadeResponse] into the [result] type
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

        public ServerResponse(T result) {
            this.result = result;
        }
    }
}
