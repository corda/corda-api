package net.corda.v5.ledger.utxo.recovery;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

public interface UtxoLedgerRepairService {

    @Suspendable
    void repairTransactions(
            @NotNull Instant from,
            @NotNull Instant until,
            @NotNull Duration duration
    );
}
