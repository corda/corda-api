package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

/**
 * Defines a contract verification failure reason.
 *
 * @property command The contract [Command] that caused the verification failure.
 * @property cause The underlying cause of the verification failure.
 */
@CordaSerializable
@DoNotImplement
interface ContractVerificationFailureReason {
    val command: Class<out Command>
    val cause: Exception?
}
