package net.corda.v5.ledger.utxo.recovery;

import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

/**
 * A service that provides functionality to repair the ledger in the scenario that it becomes inconsistent.
 */
public interface UtxoLedgerRepairService {

    /**
     * Repairs transactions that are stored in the vault as UNVERIFIED and might have been notarized but have not been
     * updated to VERIFIED. The suitable transactions are re-notarized and stored as VERIFIED.
     *
     * @param from How far in the past to repair transactions from.
     * @param until How recent in the past to repair transactions until.
     * @param duration How long the repair process should run for.
     */
    @Suspendable
    void repairTransactions(
            @NotNull Instant from,
            @NotNull Instant until,
            @NotNull Duration duration
    );
}
