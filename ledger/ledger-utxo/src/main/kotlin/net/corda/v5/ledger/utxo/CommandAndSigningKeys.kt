package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.toStringShort
import java.security.PublicKey

/**
 * Represents a command, and the signing keys associated with the specified command.
 *
 * @constructor Creates a new instance of the [CommandAndSigningKeys] data class.
 * @property command The command to verify.
 * @property signingKeys The signing keys associated with the specified command.
 */
@CordaSerializable
data class CommandAndSigningKeys<out T : Command>(val command: T, val signingKeys: Set<PublicKey>) {

    /**
     * Creates a new instance of the [CommandAndSigningKeys] data class.
     *
     * @param command The command to verify.
     * @param signingKeys The signing keys associated with the specified command.
     */
    constructor(command: T, vararg signingKeys: PublicKey) : this(command, signingKeys.toSet())

    init {
        require(signingKeys.isNotEmpty()) { "The set of signing keys must not be empty." }
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    override fun toString(): String {
        val command = command.toString().let { if ('@' in it) it.replace('$', '.').split('@')[0] else it }
        val keys = signingKeys.joinToString { it.toStringShort() }
        return "$command with signing keys $keys"
    }
}
