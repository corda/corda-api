package net.corda.v5.membership.identity

import net.corda.v5.membership.properties.LayeredPropertyMap

/**
 * Part of [MemberInfo], information is provided and added by MGM as part of member acceptance and upon updates
 * (eg. membership group status updates).
 */
interface MGMContext: LayeredPropertyMap