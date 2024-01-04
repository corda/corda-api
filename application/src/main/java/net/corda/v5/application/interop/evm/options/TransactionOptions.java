package net.corda.v5.application.interop.evm.options;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * The options available when making a transaction on a contract
 */
public class TransactionOptions extends EvmOptions {

    /**
     * The maximum amount of gas units that can be used.
     */
    @NotNull
    public BigInteger gasLimit;

    /**
     * The value transferred in wei to the recipient address.
     */
    @NotNull
    public BigInteger value;

    /**
     * The maximum amount of gas to be included as a tip to the miner.
     */
    @NotNull
    public BigInteger maxPriorityFeePerGas;

    /**
     * The maximum amount of gas willing to be paid for the transaction (including baseFeePerGas and maxPriorityFeePerGas).
     */
    @NotNull
    public BigInteger maxFeePerGas;

    /**
     * The Private Key of the sender
     */
    public String privateKey;

    /**
     * @param options
     * @param gasLimit
     * @param value
     * @param maxPriorityFeePerGas
     * @param maxFeePerGas
     * @param privateKey
     */

    public TransactionOptions(@NotNull EvmOptions options,
                              @NotNull BigInteger gasLimit,
                              @NotNull BigInteger value,
                              @NotNull BigInteger maxPriorityFeePerGas,
                              @NotNull BigInteger maxFeePerGas,
                              String privateKey) {
        this(gasLimit, value, maxPriorityFeePerGas, maxFeePerGas, options.getRpcUrl(), options.getFrom(), privateKey);
    }

    public TransactionOptions(@NotNull BigInteger gasLimit,
                              @NotNull BigInteger value,
                              @NotNull BigInteger maxPriorityFeePerGas,
                              @NotNull BigInteger maxFeePerGas,
                              @NotNull String rpcUrl,
                              String from,
                              String privateKey) {
        super(rpcUrl, from);
        this.gasLimit = gasLimit;
        this.value = value;
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
        this.maxFeePerGas = maxFeePerGas;
        this.privateKey = privateKey;
    }
}
