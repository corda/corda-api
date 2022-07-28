package net.corda.v5.ledger.utxo;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Set;

public class FungibleStateImpl implements FungibleState<BigDecimal> {

    @NotNull
    @Override
    public BigDecimal getQuantity() {
        return BigDecimal.ZERO;
    }

    @NotNull
    @Override
    public Set<PublicKey> getParticipants() {
        return Set.of();
    }
}
