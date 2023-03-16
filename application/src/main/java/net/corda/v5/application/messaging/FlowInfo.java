package net.corda.v5.application.messaging;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;

@CordaSerializable
public interface FlowInfo {
    /**
     * @return The protocol name the flow is running.
     */
    @NotNull
    String protocol();

    /**
     * @return The protocol version the flow is running. Can be null if no version associated
     */
    Integer protocolVersion();
}
