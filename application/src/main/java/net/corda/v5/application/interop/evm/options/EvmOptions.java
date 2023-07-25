package net.corda.v5.application.interop.evm.options;

import org.jetbrains.annotations.NotNull;

public class EvmOptions {

    /**
     * The rpc URL of the Evm node to connect to.
     */
    @NotNull private final String rpcUrl;

    /**
     * The address of the sender
     */
    private final String from;

    public EvmOptions(@NotNull String rpcUrl, String from) {
        this.rpcUrl = rpcUrl;
        this.from = from;
    }

    @NotNull
    public String getRpcUrl() {
        return rpcUrl;
    }

    public String getFrom() {
        return from;
    }
}
