package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;

/**
 * Defines a balance of a set of tokens returned by a call to {@link TokenSelection#queryBalance(TokenBalanceCriteria)}.
 * <p>
 * The balance is defined by a pair of values. One of the values represents the available balance. While the other one
 * represents the total balance, i.e., it is the available balance plus the tokens that have been claimed
 * but which have not been spent yet.
 */
@DoNotImplement
public interface TokenBalance {

    /**
     * Gets the available balance.
     *
     * @return Returns the balance of tokens that have not been spent nor claimed.
     */
    @NotNull
    BigDecimal getAvailableBalance();

    /**
     * Gets the total balance.
     *
     * @return Returns the available balance plus the balance of claimed tokens.
     */
    @NotNull
    BigDecimal getTotalBalance();
}
