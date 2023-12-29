package net.corda.v5.application.interop.evm;

import org.jetbrains.annotations.NotNull;
import net.corda.v5.base.annotations.CordaSerializable
import java.math.BigInteger;
import java.util.List;

@SuppressWarnings("unused")
@CordaSerializable
public class Block {
    private final BigInteger number;
    private final String hash;
    @NotNull
    private final String parentHash;
    private final BigInteger nonce;
    @NotNull
    private final String sha3Uncles;
    private final String logsBloom;
    @NotNull
    private final String transactionsRoot;
    @NotNull
    private final String stateRoot;
    @NotNull
    private final String receiptsRoot;
    @NotNull
    private final String miner;
    @NotNull
    private final String mixHash;
    @NotNull
    private final BigInteger difficulty;
    @NotNull
    private final BigInteger totalDifficulty;
    @NotNull
    private final String extraData;
    @NotNull
    private final BigInteger size;
    @NotNull
    private final BigInteger gasLimit;
    @NotNull
    private final BigInteger gasUsed;
    @NotNull
    private final BigInteger timestamp;
    @NotNull
    private final List<String> transactions;
    @NotNull
    private final List<String> uncles;
    @NotNull
    private final BigInteger baseFeePerGas;

    private final BigInteger maxFeePerGas;
    private final BigInteger maxPriorityFeePerGas;

    public Block(BigInteger number,
                 String hash,
                 @NotNull String parentHash,
                 BigInteger nonce,
                 @NotNull String sha3Uncles,
                 String logsBloom,
                 @NotNull String transactionsRoot,
                 @NotNull String stateRoot,
                 @NotNull String receiptsRoot,
                 @NotNull String miner,
                 @NotNull String mixHash,
                 @NotNull BigInteger difficulty,
                 @NotNull BigInteger totalDifficulty,
                 @NotNull String extraData,
                 @NotNull BigInteger size,
                 @NotNull BigInteger gasLimit,
                 @NotNull BigInteger gasUsed,
                 @NotNull BigInteger timestamp,
                 @NotNull List<String> transactions,
                 @NotNull List<String> uncles,
                 @NotNull BigInteger baseFeePerGas,
                 BigInteger maxFeePerGas,
                 BigInteger maxPriorityFeePerGas) {
        this.number = number;
        this.hash = hash;
        this.parentHash = parentHash;
        this.nonce = nonce;
        this.sha3Uncles = sha3Uncles;
        this.logsBloom = logsBloom;
        this.transactionsRoot = transactionsRoot;
        this.stateRoot = stateRoot;
        this.receiptsRoot = receiptsRoot;
        this.miner = miner;
        this.mixHash = mixHash;
        this.difficulty = difficulty;
        this.totalDifficulty = totalDifficulty;
        this.extraData = extraData;
        this.size = size;
        this.gasLimit = gasLimit;
        this.gasUsed = gasUsed;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.uncles = uncles;
        this.baseFeePerGas = baseFeePerGas;
        this.maxFeePerGas = maxFeePerGas;
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
    }

    public BigInteger getNumber() {
        return number;
    }

    public String getHash() {
        return hash;
    }

    @NotNull
    public String getParentHash() {
        return parentHash;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    @NotNull
    public String getSha3Uncles() {
        return sha3Uncles;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    @NotNull
    public String getTransactionsRoot() {
        return transactionsRoot;
    }

    @NotNull
    public String getStateRoot() {
        return stateRoot;
    }

    @NotNull
    public String getReceiptsRoot() {
        return receiptsRoot;
    }

    @NotNull
    public String getMiner() {
        return miner;
    }

    @NotNull
    public String getMixHash() {
        return mixHash;
    }

    @NotNull
    public BigInteger getDifficulty() {
        return difficulty;
    }

    @NotNull
    public BigInteger getTotalDifficulty() {
        return totalDifficulty;
    }

    @NotNull
    public String getExtraData() {
        return extraData;
    }

    @NotNull
    public BigInteger getSize() {
        return size;
    }

    @NotNull
    public BigInteger getGasLimit() {
        return gasLimit;
    }

    @NotNull
    public BigInteger getGasUsed() {
        return gasUsed;
    }

    @NotNull
    public BigInteger getTimestamp() {
        return timestamp;
    }

    @NotNull
    public List<String> getTransactions() {
        return transactions;
    }

    @NotNull
    public List<String> getUncles() {
        return uncles;
    }

    @NotNull
    public BigInteger getBaseFeePerGas() {
        return baseFeePerGas;
    }

    public BigInteger getMaxFeePerGas() {
        return maxFeePerGas;
    }

    public BigInteger getMaxPriorityFeePerGas() {
        return maxPriorityFeePerGas;
    }
}
