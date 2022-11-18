package net.corda.v5.ledger.utxo

import net.corda.v5.application.messaging.FlowSession
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction
import net.corda.v5.ledger.utxo.transaction.UtxoTransactionValidator
import net.corda.v5.ledger.utxo.transaction.UtxoTransactionBuilder

/**
 * Defines UTXO ledger services.
 */
@DoNotImplement
interface UtxoLedgerService {

    /**
     * Gets a UTXO transaction builder
     *
     * @return Returns a new [UtxoTransactionBuilder] instance.
     */
    @Suspendable
    fun getTransactionBuilder(): UtxoTransactionBuilder

    /**
     * Resolves the specified [StateRef] instances into [StateAndRef] instances of the specified [ContractState] type.
     *
     * @param T The underlying [ContractState] type.
     * @param stateRefs The [StateRef] instances to resolve.
     * @return Returns a [List] of [StateAndRef] of the specified [ContractState] type.
     */
    @Suspendable
    fun <T : ContractState> resolve(stateRefs: Iterable<StateRef>): List<StateAndRef<T>>

    /**
     * Resolves the specified [StateRef] instance into a [StateAndRef] instance of the specified [ContractState] type.
     *
     * @param T The underlying [ContractState] type.
     * @param stateRef The [StateRef] instances to resolve.
     * @return Returns a [StateAndRef] of the specified [ContractState] type.
     */
    @Suspendable
    fun <T : ContractState> resolve(stateRef: StateRef): StateAndRef<T>

    /**
     * Finds a transaction by id in the vault and returns it
     *
     * @param id The id of the transaction to find
     *
     * @return The signed transaction, if it has been recorded previously. Null if not found.
     */
    @Suspendable
    fun findSignedTransaction(id: SecureHash): UtxoSignedTransaction?

    /**
     * Finds a transaction by id in the vault, resolves it to a ledger transaction and returns it
     *
     * @param id The id of the transaction to find
     *
     * @return The ledger transaction, if it has been recorded previously. Null if not found.
     */
    @Suspendable
    fun findLedgerTransaction(id: SecureHash): UtxoLedgerTransaction?

    /**
     * Collects signatures, records and broadcasts to involved peers a [UtxoSignedTransaction].
     *
     * @param signedTransaction The [UtxoSignedTransaction] to finalise and recorded locally and with peer [sessions].
     * @param sessions The [FlowSession]s of the peers involved in the transaction.
     *
     * @return The fully signed [UtxoSignedTransaction] that was recorded.
     */
    @Suspendable
    fun finalize(
        signedTransaction: UtxoSignedTransaction,
        sessions: List<FlowSession>
    ): UtxoSignedTransaction

    /**
     * Verifies, signs and records a [UtxoSignedTransaction].
     *
     * @param session The [FlowSession] to receive the [UtxoSignedTransaction] from.
     * @param validator Verifies the received [UtxoSignedTransaction].
     *
     * @return The fully signed [UtxoSignedTransaction] that was received and recorded.
     */
    @Suspendable
    fun receiveFinality(
        session: FlowSession,
        validator: UtxoTransactionValidator
    ): UtxoSignedTransaction


    // TODO CORE-7327 Add verify(signedTx) verify(ledgerTx)
}
