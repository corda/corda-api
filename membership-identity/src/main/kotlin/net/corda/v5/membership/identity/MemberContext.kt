package net.corda.v5.membership.identity

/**
 * Part of [MemberInfo], MemberContext part is provided by the member as part of the initial MemberInfo proposal.
 * Contains information such as the node's endpoints, party's name.
 */
interface MemberContext: KeyValueStore