package net.corda.v5.ledger.utxo;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.crypto.SecureHash;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Defines a reference to a {@link ContractState}.
 */
@CordaSerializable
public final class StateRef {

    /**
     * The id of the transaction in which the referenced state was created.
     */
    @NotNull
    private final SecureHash transactionId;

    /**
     * The index of the state in the transaction's outputs in which the referenced state was created.
     */
    private final int index;

    /**
     * Creates a new instance of the {@link StateRef} class.
     *
     * @param transactionId The id of the transaction in which the referenced state was created.
     * @param index         The index of the state in the transaction's outputs in which the referenced state was created.
     */
    public StateRef(@NotNull final SecureHash transactionId, final int index) {
        this.transactionId = transactionId;
        this.index = index;
    }

    /**
     * Gets the id of the transaction in which the referenced state was created.
     *
     * @return Returns the id of the transaction in which the referenced state was created.
     */
    @NotNull
    public SecureHash getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the index of the state in the transaction's outputs in which the referenced state was created.
     *
     * @return Returns the index of the state in the transaction's outputs in which the referenced state was created.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Parses the specified {@link String} value into a {@link StateRef}.
     *
     * @param value The value to parse into a {@link StateRef}.
     * @return Returns a {@link StateRef} parsed from the specified {@link String} value.
     * @throws IllegalArgumentException if the specified value cannot be parsed.
     */
    public static StateRef parse(@NotNull final String value) throws IllegalArgumentException {
        try {
            // TODO : Magic string ":" should be replaced with DELIMITER
            final int lastIndexOfDelimiter = value.lastIndexOf(":");
            final String subStringBeforeDelimiter = value.substring(0, lastIndexOfDelimiter);
            final String subStringAfterDelimiter = value.substring(lastIndexOfDelimiter + 1);
            final SecureHash transactionId = SecureHash.parse(subStringBeforeDelimiter);
            final int index = Integer.parseInt(subStringAfterDelimiter);
            return new StateRef(transactionId, index);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Failed to parse a StateRef from the specified value. The index is malformed: {0}.", value),
                    numberFormatException
            );
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Failed to parse a StateRef from the specified value. The transaction ID is malformed: {0}.", value),
                    illegalArgumentException
            );
        }
    }

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param obj The object to compare with the current object.
     * @return Returns true if the specified object is equal to the current object; otherwise, false.
     */
    @Override
    public boolean equals(@Nullable final Object obj) {
        return this == obj || obj instanceof StateRef && equals((StateRef) obj);
    }

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param other The Party to compare with the current object.
     * @return Returns true if the specified Party is equal to the current object; otherwise, false.
     */
    public boolean equals(@NotNull final StateRef other) {
        return Objects.equals(other.transactionId, transactionId)
                && Objects.equals(other.index, index);
    }

    /**
     * Serves as the default hash function.
     *
     * @return Returns a hash code for the current object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactionId, index);
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    @Override
    public String toString() {
        return MessageFormat.format("{0}:{2}", transactionId, index);
    }
}
