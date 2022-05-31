package net.corda.v5.ledger.consensual

import net.corda.v5.base.annotations.CordaSerializable

/**
 * A consensual state (or just "state") contains opaque data used by a consensual ledger. It can be thought of as a disk
 * file that the program can use to persist data across transactions. ConsensualState.kt are immutable: once created they are never
 * updated, instead, any changes must generate a new successor state.
 * Consensual states cannot be updated (consumed).
 */
@CordaSerializable
interface ConsensualState {
    /**
     * A _participant_ is any party whose consent is needed to make a Consensual State valid and final.
     *
     * Participants are the main and only verification points for Consensual ConsensualState.kt since they do not have contract code.
     * Every participant has to be involved and approve the transaction
     * so that they receive the updated state, and don't end up in a situation where they can no longer use a state
     * they possess.
     *
     * The participants list should normally be derived from the contents of the state.
     */
    val participants: List<Party>
}
