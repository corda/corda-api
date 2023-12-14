package net.corda.v5.application.messaging;

import java.time.Duration;

/**
 * Flow session configuration. Instances should be created using {@link Builder}.
 */
public class FlowSessionConfiguration {
    private final boolean requireClose;
    private final Duration timeout;

    private FlowSessionConfiguration(Builder builder) {
        this.requireClose = builder.requireClose;
        this.timeout = builder.timeout;
    }

    public boolean isRequireClose() {
        return requireClose;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public static class Builder {
        private boolean requireClose = true;
        private Duration timeout;

        /**
         * When set to true, the initiated party will send a close message after calling FlowSession.close()
         * and the initiating party will suspend and wait to receive the message when they call FlowSession.close().
         * When set to false the session is marked as terminated immediately when close() is called.
         * Default value is true.
         * @param requireClose Flag that indicates whether close message is required.
         * @return {@link Builder}.
         */
        public Builder requireClose(boolean requireClose) {
            this.requireClose = requireClose;
            return this;
        }

        /**
         * The duration that Corda waits when no message has been received from a counterparty before
         * causing the session to error. If set to null, value set in Corda Configuration will be used.
         * Default value is null.
         * @param timeout Session timeout.
         * @return {@link Builder}.
         */
        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Builds a new instance of {@link FlowSessionConfiguration}.
         * @return a new instance of {@link FlowSessionConfiguration}.
         */
        public FlowSessionConfiguration build() {
            return new FlowSessionConfiguration(this);
        }
    }
}
