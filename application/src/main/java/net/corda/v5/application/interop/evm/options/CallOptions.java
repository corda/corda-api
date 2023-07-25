package net.corda.v5.application.interop.evm.options;

import org.jetbrains.annotations.NotNull;

/**
 * The options available when making a function call on a contract
 */
public class CallOptions extends EvmOptions {

    /**
     * The block number in hexadecimal format or the string "latest", "earliest", "pending"
     */
    @NotNull private final String blockNumber;

    public CallOptions(EvmOptions options) {
        this("latest", options.getRpcUrl(), options.getFrom());
    }

    public CallOptions(@NotNull String blockNumber, EvmOptions options) {
        this(blockNumber, options.getRpcUrl(), options.getFrom());
    }

    public CallOptions(@NotNull String blockNumber, String rpcUrl, String from) {
        super(rpcUrl, from);
        this.blockNumber = blockNumber;
    }

    @NotNull
    public String getBlockNumber() {
        return blockNumber;
    }
}
