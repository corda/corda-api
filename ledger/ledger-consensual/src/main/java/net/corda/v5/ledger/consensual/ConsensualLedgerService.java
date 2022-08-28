package net.corda.v5.ledger.consensual;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.ledger.consensual.transaction.ConsensualTransactionBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * It provides access to the different Consensual Ledger Services
 */
@DoNotImplement
public interface ConsensualLedgerService {
    /**
     * @return an empty {@link ConsensualTransactionBuilder} instance
     */
    @Suspendable
    @NotNull
    ConsensualTransactionBuilder getTransactionBuilder();

    /* TODO
    fun fetchTransaction(id: SecureHash)
    fun finality(sessions)
    fun receiveFinality( ()-> ... )
    ... Vault ...
    */
}
