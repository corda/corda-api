package net.corda.v5.application.node

import net.corda.v5.base.annotations.CordaSerializable
import java.time.Instant

@CordaSerializable
interface GroupParameters: KeyValueStore {

    /**
     * Version number of the Group parameters. Starting from 1,
     * this will always increment on each new set of parameters.
     */
    val epoch: Int

    /**
     * Minimum version of Corda platform that is required for nodes to join the group.
     * This can be inferred dynamically through analysis of the given list of CPIs.
     */
    val minimumPlatformVersion: Int

    /**
     * List of trusted notary service information, like identities, historical service keys, notary type.
     */
    //val notaries : List<NotaryInfo>

    /**
     * Last modification time of Group Parameters set.
     */
    val modifiedTime: Instant

    /**
     * This is a map of additional key value pairs, that allows the MGM to define additional parameters
     * potentially unique to their network. Examples of what this could be a list of acceptable oracles.
     */
    val additionalFields: KeyValueStore
}