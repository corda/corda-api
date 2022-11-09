package net.corda.v5.membership

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.types.LayeredPropertyMap
import net.corda.v5.ledger.common.NotaryInfo
import java.time.Instant

/**
 * This interface represents a set of group parameters under which all members of a group are expected to abide by.
 * Parameters are stored as a [LayeredPropertyMap] and exposed via interface properties.
 *
 * Note: any values in the group parameters values map need to be
 * a.) serializable for P2P (AMQP) and checkpoints (Kryo)
 * b.) comparable with .equals()
 *
 * Example usages:
 *
 * ```java
 * GroupParameters groupParameters = fullTransaction.getMembershipParameters();
 * int minimumPlatformVersion = groupParameters.getMinimumPlatformVersion();
 * Instant modifiedTime = groupParameters.getModifiedTime();
 * int epoch = groupParameters.getEpoch();
 * LinkedList<NotaryInfo> notaries = new LinkedList(groupParameters.getNotaries());
 * ```
 *
 * ```kotlin
 * val groupParameters = fullTransaction.membershipParameters
 * val minimumPlatformVersion = groupParameters?.minimumPlatformVersion
 * val modifiedTime = groupParameters?.modifiedTime
 * val epoch = groupParameters?.epoch
 * val notaries = groupParameters?.notaries
 * ```
 *
 * @property minimumPlatformVersion The minimum platform version required to be running on in order to transact within a group.
 * @property modifiedTime The [Instant] representing the last time the group parameters were modified.
 * @property epoch An [Int] representing the version of the group parameters. This is incremented on each modification to the
 * group parameters.
 * @property notaries List of registered notaries in the membership group.
 */
@CordaSerializable
interface GroupParameters : LayeredPropertyMap {
    val minimumPlatformVersion: Int
    val modifiedTime: Instant
    val epoch: Int
    val notaries: List<NotaryInfo>
}