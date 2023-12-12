package net.corda.v5.application.interop.evm;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

public class Log {
    /**
     * The address from which this log was generated
     */
    @NotNull
    final String address;

    /**
     * An array of zero to four 32 Bytes DATA of indexed log arguments
     */
    @NotNull
    final List<String> topics;

    /**
     * The 32 byte non-indexed argument of the log
     */
    @NotNull
    final String data;

    /**
     * The block number where this log was in
     */
    @NotNull
    final BigInteger blockNumber;

    /**
     * The hash of the transaction from which this log was created from. null if the log is pending
     */
    final String transactionHash;

    /**
     * The transaction index from which this log was created from. null if the log is pending
     */
    final BigInteger transactionIndex;

    /**
     * The hash of the block this log in
     */
    @NotNull
    final String blockHash;

    /**
     * The integer of the log position in the block. null if the log is pending
     */
    final int logIndex;

    /**
     * True if this log was removed, due to a chain reorganization and false if it's a valid log
     */
    boolean removed;

    public Log(@NotNull String address,
               @NotNull List<String> topics,
               @NotNull String data,
               @NotNull BigInteger blockNumber,
               String transactionHash,
               BigInteger transactionIndex,
               @NotNull String blockHash,
               int logIndex,
               boolean removed) {
        this.address = address;
        this.topics = topics;
        this.data = data;
        this.blockNumber = blockNumber;
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.blockHash = blockHash;
        this.logIndex = logIndex;
        this.removed = removed;
    }

    @NotNull
    public String getAddress() {
        return address;
    }

    @NotNull
    public List<String> getTopics() {
        return topics;
    }

    @NotNull
    public String getData() {
        return data;
    }

    @NotNull
    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    @NotNull
    public String getBlockHash() {
        return blockHash;
    }

    public int getLogIndex() {
        return logIndex;
    }

    public boolean isRemoved() {
        return removed;
    }
}
