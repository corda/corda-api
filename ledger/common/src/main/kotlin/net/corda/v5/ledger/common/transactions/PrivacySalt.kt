package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable

/**
 * A privacy salt is required to compute nonces per transaction component in order to ensure that an adversary cannot
 * use brute force techniques and reveal the content of a Merkle-leaf hashed value.
 */
@CordaSerializable
interface PrivacySalt{
    val bytes: ByteArray
}
