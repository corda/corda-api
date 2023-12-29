package net.corda.v5.application.interop.evm;

import org.jetbrains.annotations.NotNull;
import net.corda.v5.base.annotations.CordaSerializable
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@CordaSerializable
public class TransactionReceipt {
    /**
     * The hash of the block where this transaction was in
     */
    @NotNull
    private final String blockHash;
    /**
     * The block number where this transaction was added
     */
    @NotNull
    private final BigInteger blockNumber;
    /**
     * The contract address created for contract creation, otherwise null
     */
    private final String contractAddress;
    /**
     * The total gas used when this transaction was executed in the block
     */
    @NotNull
    private final BigInteger cumulativeGasUsed;
    /**
     * The total base charge plus tip paid for each unit of gas
     */
    private final BigInteger effectiveGasPrice;
    /**
     * The address of the sender
     */
    @NotNull
    private final String from;
    /**
     * The amount of gas used by this specific transaction alone
     */
    @NotNull
    private final BigInteger gasUsed;
    /**
     * An array of log objects that generated this transaction
     */
    @NotNull
    private final List<Log> logs;
    /**
     * The bloom filter which is used to retrieve related logs
     */
    @NotNull
    private final String logsBloom;
    /**
     * It is either (success) or (failure)
     */
    private final boolean status;
    /**
     * The address of the receiver. Null when it's a contract creation transaction
     */
    private final String to;
    /**
     * The hash of the transaction
     */
    @NotNull
    private final String transactionHash;
    /**
     * The transaction's index position in the block
     */
    @NotNull
    private final BigInteger transactionIndex;
    /**
     * The type of value
     */
    @NotNull
    private final String type;

    public TransactionReceipt(@NotNull String blockHash,
                              @NotNull BigInteger blockNumber,
                              String contractAddress,
                              @NotNull BigInteger cumulativeGasUsed,
                              BigInteger effectiveGasPrice,
                              @NotNull String from,
                              @NotNull BigInteger gasUsed,
                              @NotNull List<Log> logs,
                              @NotNull String logsBloom,
                              boolean status,
                              String to,
                              @NotNull String transactionHash,
                              @NotNull BigInteger transactionIndex,
                              @NotNull String type) {
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.contractAddress = contractAddress;
        this.cumulativeGasUsed = cumulativeGasUsed;
        this.effectiveGasPrice = effectiveGasPrice;
        this.from = from;
        this.gasUsed = gasUsed;
        this.logs = logs;
        this.logsBloom = logsBloom;
        this.status = status;
        this.to = to;
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.type = type;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public BigInteger getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public BigInteger getGasUsed() {
        return gasUsed;
    }

    public BigInteger getEffectiveGasPrice() {
        return effectiveGasPrice;
    }

    public boolean getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    @NotNull
    public List<Log> getLogs() {
        return logs;
    }

    @NotNull
    public String getLogsBloom() {
        return logsBloom;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionReceipt that = (TransactionReceipt) o;
        return status == that.status && Objects.equals(blockHash, that.blockHash) && Objects.equals(blockNumber, that.blockNumber) && Objects.equals(contractAddress, that.contractAddress) && Objects.equals(cumulativeGasUsed, that.cumulativeGasUsed) && Objects.equals(effectiveGasPrice, that.effectiveGasPrice) && Objects.equals(from, that.from) && Objects.equals(gasUsed, that.gasUsed) && Objects.equals(logs, that.logs) && Objects.equals(logsBloom, that.logsBloom) && Objects.equals(to, that.to) && Objects.equals(transactionHash, that.transactionHash) && Objects.equals(transactionIndex, that.transactionIndex) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockHash, blockNumber, contractAddress, cumulativeGasUsed, effectiveGasPrice, from, gasUsed, logs, logsBloom, status, to, transactionHash, transactionIndex, type);
    }

    @Override
    public String toString() {
        return "TransactionReceipt{" +
                "blockHash='" + blockHash + '\'' +
                ", blockNumber=" + blockNumber +
                ", contractAddress='" + contractAddress + '\'' +
                ", cumulativeGasUsed=" + cumulativeGasUsed +
                ", effectiveGasPrice=" + effectiveGasPrice +
                ", from='" + from + '\'' +
                ", gasUsed=" + gasUsed +
                ", logs=" + logs +
                ", logsBloom='" + logsBloom + '\'' +
                ", status=" + status +
                ", to='" + to + '\'' +
                ", transactionHash='" + transactionHash + '\'' +
                ", transactionIndex=" + transactionIndex +
                ", type='" + type + '\'' +
                '}';
    }
}
