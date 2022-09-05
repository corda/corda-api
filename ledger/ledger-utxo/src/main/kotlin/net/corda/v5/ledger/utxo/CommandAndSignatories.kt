package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.crypto.toStringShort
import java.security.PublicKey

/**
 * Represents a command, and the signatories associated with the specified command.
 *
 * @constructor Creates a new instance of the [CommandAndSignatories] data class.
 * @property command The command to verify.
 * @property signatories The signatory signing keys associated with the specified command.
 */
@CordaSerializable
data class CommandAndSignatories<out T : Command>(val command: T, val signatories: Set<PublicKey>) {

    /**
     * Creates a new instance of the [CommandAndSignatories] data class.
     *
     * @param command The command to verify.
     * @param signatories The signatory signing keys associated with the specified command.
     */
    constructor(command: T, vararg signatories: PublicKey) : this(command, signatories.toSet())

    init {
        require(signatories.isNotEmpty()) { "The set of signatories must not be empty." }
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    override fun toString(): String {
        val command = command.toString().let { if ('@' in it) it.replace('$', '.').split('@')[0] else it }
        val keys = signatories.joinToString { it.toStringShort() }
        return "$command with signatories $keys"
    }
}

/**
 * Casts the current [CommandAndSignatories] to the specified type.
 *
 * @param T The underlying type of the [Command].
 * @param type The type of the [Command] to cast.
 * @return Returns a new [CommandAndSignatories] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [Command] cannot be cast to the specified type.
 */
fun <T : Command> CommandAndSignatories<*>.cast(type: Class<T>): CommandAndSignatories<T> {
    return if (!command.javaClass.isAssignableFrom(type)) {
        throw IllegalArgumentException("Command of type ${command.javaClass.canonicalName} cannot be cast to type ${type.canonicalName}.")
    } else CommandAndSignatories(type.cast(command), signatories)
}

/**
 * Casts the current [CommandAndSignatories] to the specified type.
 *
 * @param T The underlying type of the [Command].
 * @return Returns a new [CommandAndSignatories] instance cast to the specified type.
 * @throws IllegalArgumentException if the current [Command] cannot be cast to the specified type.
 */
inline fun <reified T : Command> CommandAndSignatories<*>.cast(): CommandAndSignatories<T> {
    return cast(T::class.java)
}
