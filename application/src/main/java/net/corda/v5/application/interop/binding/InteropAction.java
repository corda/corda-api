package net.corda.v5.application.interop.binding;

import net.corda.v5.application.interop.facade.FacadeRequest;

public abstract class InteropAction {
    public abstract Object getResult();

    public static final class ClientAction<T> extends InteropAction {
        private final FacadeRequest request;
        private final Processable processor;
        private final Interpretable<T> responseInterpreter;

        public ClientAction(FacadeRequest request, Processable processor, Interpretable<T> responseInterpreter) {
            this.request = request;
            this.processor = processor;
            this.responseInterpreter = responseInterpreter;
        }

        @Override
        public Object getResult() {
            return responseInterpreter.interpret(processor.process(request));
        }
    }

    public static final class ServerResponse<T> extends InteropAction {
        private final Object result;

        public Object getResult() {
            return this.result;
        }

        public ServerResponse(Object result) {
            this.result = result;
        }
    }
}
