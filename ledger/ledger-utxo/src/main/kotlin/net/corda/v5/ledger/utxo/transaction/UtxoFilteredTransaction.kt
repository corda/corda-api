package net.corda.v5.ledger.utxo.transaction

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.common.Party
import net.corda.v5.ledger.common.transaction.TransactionVerificationException
import net.corda.v5.ledger.utxo.Command
import net.corda.v5.ledger.utxo.StateAndRef
import net.corda.v5.ledger.utxo.StateRef
import net.corda.v5.ledger.utxo.TimeWindow
import java.security.PublicKey

@DoNotImplement
interface UtxoFilteredTransaction {
    /**
     * @property id The ID of the transaction.
     */
    val id: SecureHash

    /**
     * @param timeWindow The validity time window for finalizing/notarising this transaction or null if filtered
     */
    val timeWindow: TimeWindow?

    /**
     * @param notary The notary party for this transaction or null if filtered
     */
    val notary: Party?

    /**
     * @param signatories Potentially filtered list of required signers
     */
    val signatories: UtxoFilteredData<PublicKey>

    /**
     * @param inputStateRefs Potentially filtered list of input state refs
     */
    val inputStateRefs: UtxoFilteredData<StateRef>

    /**
     * @param referenceInputStateRefs Potentially filtered list of reference state refs
     */
    val referenceInputStateRefs: UtxoFilteredData<StateRef>

    /**
     * @param outputStateRefs Potentially filtered list of outputs
     */
    val outputStateAndRefs: UtxoFilteredData<StateAndRef<*>>

    /**
     * @param commands Potentially filtered list of commands
     */
    val commands: UtxoFilteredData<Command>

    /**
     * Verifies the current [UtxoFilteredTransaction].
     *
     * @throws TransactionVerificationException if the current [UtxoSignedTransaction] fails to verify correctly.
     */
    fun verify()
}