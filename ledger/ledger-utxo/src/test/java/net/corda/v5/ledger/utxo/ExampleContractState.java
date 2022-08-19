package net.corda.v5.ledger.utxo;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Set;
import java.util.UUID;

@BelongsToContract(ExampleContract.class)
public class ExampleContractState implements ContractState, IdentifiableState, IssuableState, BearableState, FungibleState<BigDecimal> {

    private final UUID id;
    private final PublicKey issuer;
    private final PublicKey bearer;
    private final BigDecimal quantity;

    public ExampleContractState(UUID id, PublicKey issuer, PublicKey bearer, BigDecimal quantity) {
        this.id = id;
        this.issuer = issuer;
        this.bearer = bearer;
        this.quantity = quantity;
    }

    @NotNull
    @Override
    public Set<PublicKey> getParticipants() {
        return Set.of(issuer, bearer);
    }

    @NotNull
    @Override
    public UUID getId() {
        return id;
    }

    @NotNull
    @Override
    public PublicKey getIssuer() {
        return issuer;
    }

    @NotNull
    @Override
    public PublicKey getBearer() {
        return bearer;
    }

    @NotNull
    @Override
    public BigDecimal getQuantity() {
        return quantity;
    }
}
