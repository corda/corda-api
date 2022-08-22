package net.corda.v5.ledger.utxo

import java.security.PublicKey

/**
 * Defines a verifiable command; that is, a command that implements its own contract verification logic.
 */
interface VerifiableCommand : Command {

    /**
     * Verifies the specified transaction associated with the current contract command.
     *
     * @param transaction The transaction to verify.
     * @param signingKeys The signing keys associated with the current contract command.
     */
    fun verify(transaction: UtxoLedgerTransaction, signingKeys: Iterable<PublicKey>)
}
