package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;

@DoNotImplement
public interface TokenBalance {

    @NotNull
    BigDecimal getBalance();

    @NotNull
    BigDecimal getBalanceIncludingClaimedTokens();
}
