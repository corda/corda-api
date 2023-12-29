package net.corda.v5.application.interop.evm;


import org.jetbrains.annotations.NotNull;
import net.corda.v5.base.annotations.CordaSerializable
import java.math.BigInteger;
import java.util.Objects;

@CordaSerializable
public class TransactionObject {

    /**
     * The hash of the block where this transaction was in.
     */
    @NotNull
    private final String blockHash;

    /**
     * The block number where this transaction was in.
     */
    @NotNull
    private final BigInteger blockNumber;

    /**
     * The address of the sender.
     */
    @NotNull
    private final String from;

    /**
     * The gas provided by the sender, encoded as hexadecimal.
     */
    @NotNull
    private final BigInteger gas;

    /**
     * The gas price provided by the sender in wei encoded as hexadecimal.
     */
    @NotNull
    private final BigInteger gasPrice;

    /**
     * The maximum fee per gas set in the transaction.
     */
    @NotNull
    private final BigInteger maxFeePerGas;

    /**
     * The maximum priority gas fee set in the transaction.
     */
    @NotNull
    private final BigInteger maxPriorityFeePerGas;

    /**
     * The hash of the transaction.
     */
    @NotNull
    private final String hash;

    /**
     * The data sent along with the transaction.
     */
    @NotNull
    private final String input;

    /**
     * The number of transactions made by the sender prior to this one encoded as hexadecimal.
     */
    @NotNull
    private final BigInteger nonce;

    /**
     * The address of the receiver. Null when it's a contract creation transaction.
     */
    @NotNull
    private final String to;

    /**
     * The integer of the transaction's index position that the log was created from. Null when it's a pending log.
     */
    @NotNull
    private final BigInteger transactionIndex;

    /**
     * The value transferred in wei encoded as hexadecimal.
     */
    @NotNull
    private final BigInteger value;

    /**
     * The transaction type.
     */
    @NotNull
    private final String type;

    /**
     * The standardized V field of the signature.
     */
    @NotNull
    private final String v;

    /**
     * The R field of the signature.
     */
    @NotNull
    private final String r;

    /**
     * The S field of the signature.
     */
    @NotNull
    private final String s;


    public TransactionObject(
            @NotNull String blockHash,
            @NotNull BigInteger blockNumber,
            @NotNull String from,
            @NotNull BigInteger gas,
            @NotNull BigInteger gasPrice,
            @NotNull BigInteger maxFeePerGas,
            @NotNull BigInteger maxPriorityFeePerGas,
            @NotNull String hash,
            @NotNull String input,
            @NotNull BigInteger nonce,
            @NotNull String to,
            @NotNull BigInteger transactionIndex,
            @NotNull BigInteger value,
            @NotNull String type,
            @NotNull String v,
            @NotNull String r,
            @NotNull String s

    ) {
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.from = from;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.maxFeePerGas = maxFeePerGas;
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
        this.hash = hash;
        this.input = input;
        this.nonce = nonce;
        this.to = to;
        this.transactionIndex = transactionIndex;
        this.value = value;
        this.type = type;
        this.v = v;
        this.r = r;
        this.s = s;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public String getFrom() {
        return from;
    }

    public BigInteger getGas() {
        return gas;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }


    public BigInteger getMaxFeePerGas() {
        return maxFeePerGas;
    }

    public BigInteger getMaxPriorityFeePerGas() {
        return maxPriorityFeePerGas;
    }

    public String getHash() {
        return hash;
    }

    public String getInput() {
        return input;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getV() {
        return v;
    }

    public String getR() {
        return r;
    }

    public String getS() {
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionObject)) return false;
        TransactionObject that = (TransactionObject) o;
        return getBlockHash().equals(that.getBlockHash()) &&
                getBlockNumber().equals(that.getBlockNumber()) &&
                getFrom().equals(that.getFrom()) &&
                getGas().equals(that.getGas()) &&
                getGasPrice().equals(that.getGasPrice()) &&
                getMaxFeePerGas().equals(that.getMaxFeePerGas()) &&
                getMaxPriorityFeePerGas().equals(that.getMaxPriorityFeePerGas()) &&
                getHash().equals(that.getHash()) &&
                getInput().equals(that.getInput()) &&
                getNonce().equals(that.getNonce()) &&
                getTo().equals(that.getTo()) &&
                getTransactionIndex().equals(that.getTransactionIndex()) &&
                getValue().equals(that.getValue()) &&
                getType().equals(that.getType()) &&
                getV().equals(that.getV()) &&
                getR().equals(that.getR()) &&
                getS().equals(that.getS());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlockHash(), getBlockNumber(), getFrom(), getGas(), getGasPrice(), getMaxFeePerGas(), getMaxPriorityFeePerGas(), getHash(), getInput(), getNonce(), getTo(), getTransactionIndex(), getValue(), getType(), getV(), getR(), getS());
    }

    @Override
    public String toString() {
        return "TransactionObject{" +
                "blockHash='" + blockHash + '\'' +
                ", blockNumber=" + blockNumber +
                ", from='" + from + '\'' +
                ", gas=" + gas +
                ", gasPrice=" + gasPrice +
                ", maxFeePerGas=" + maxFeePerGas +
                ", maxPriorityFeePerGas=" + maxPriorityFeePerGas +
                ", hash='" + hash + '\'' +
                ", input='" + input + '\'' +
                ", nonce=" + nonce +
                ", to='" + to + '\'' +
                ", transactionIndex=" + transactionIndex +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", v='" + v + '\'' +
                ", r='" + r + '\'' +
                ", s='" + s + '\'' +
                '}';
    }

}
