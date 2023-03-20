package net.corda.v5.ledger.utxo.query;

import net.corda.v5.application.persistence.ParameterizedQuery;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public interface VaultNamedParameterizedQuery<R> extends ParameterizedQuery<R> {

    @NotNull
    VaultNamedParameterizedQuery<R> setCreatedTimestampLimit(@NotNull Instant timestampLimit);
}
