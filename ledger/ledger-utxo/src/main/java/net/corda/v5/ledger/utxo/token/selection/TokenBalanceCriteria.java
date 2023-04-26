package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.ledger.utxo.observer.UtxoTokenPoolKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Objects;

public class TokenBalanceCriteria {

    private UtxoTokenPoolKey utxoTokenPoolKey;
    private MemberX500Name notaryX500Name;

    public TokenBalanceCriteria(
            @NotNull final UtxoTokenPoolKey utxoTokenPoolKey,
            @NotNull final MemberX500Name notaryX500Name
    ) {
        this.utxoTokenPoolKey = utxoTokenPoolKey;
        this.notaryX500Name = notaryX500Name;
    }

    @NotNull
    public UtxoTokenPoolKey getUtxoTokenPoolKey() {
        return utxoTokenPoolKey;
    }

    /**
     * Gets the {@link MemberX500Name} of the notary of the tokens to be selected.
     *
     * @return Returns the {@link MemberX500Name} of the notary of the tokens to be selected.
     */
    @NotNull
    public MemberX500Name getNotaryX500Name() {
        return notaryX500Name;
    }

    public void setUtxoTokenPoolKey(@Nullable final UtxoTokenPoolKey utxoTokenPoolKey) {
        this.utxoTokenPoolKey = utxoTokenPoolKey;
    }

    @NotNull
    public void setNotaryX500Name(MemberX500Name notaryX500Name) {
        this.notaryX500Name = notaryX500Name;
    }


    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param obj The object to compare with the current object.
     * @return Returns true if the specified object is equal to the current object; otherwise, false.
     */
    @Override
    public boolean equals(@Nullable final Object obj) {
        return this == obj || obj instanceof TokenBalanceCriteria && equals((TokenBalanceCriteria) obj);
    }

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param other The Party to compare with the current object.
     * @return Returns true if the specified Party is equal to the current object; otherwise, false.
     */
    public boolean equals(@NotNull final TokenBalanceCriteria other) {
        return Objects.equals(other.utxoTokenPoolKey, utxoTokenPoolKey)
                && Objects.equals(other.notaryX500Name, notaryX500Name);
    }

    /**
     * Serves as the default hash function.
     *
     * @return Returns a hash code for the current object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(utxoTokenPoolKey, notaryX500Name);
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "TokenBalanceCriteria(utxoTokenPoolKey={0}, notaryX500Name={1})",
                utxoTokenPoolKey, notaryX500Name
        );
    }
}
