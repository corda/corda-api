package net.corda.v5.application.messaging;

public interface FlowInfo {
    /**
     * @return The protocol name the flow is running.
     */
    String protocol();

    /**
     * @return The protocol version the flow is running.
     */
    Integer protocolVersion();
}
