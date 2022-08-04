package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.security.PublicKey

/**
 * Represents a command, and the signing keys associated with the specified command.
 *
 * @property command The command to verify.
 * @property signatories The signing keys associated with the specified command.
 */
@CordaSerializable
data class CommandAndSignatories(val command: Command, val signatories: Set<PublicKey>)
