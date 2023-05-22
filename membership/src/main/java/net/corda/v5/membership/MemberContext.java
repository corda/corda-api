package net.corda.v5.membership;

import net.corda.v5.base.types.LayeredPropertyMap;

/**
 * <p>Part of {@link MemberInfo}, the MemberContext part is provided by the member as part of the initial MemberInfo proposal
 * (i.e. group registration).</p>
 *
 * <p>Contains information such as the node's endpoints, X.500 name, key information, etc.</p>
 *
 * <p>Example usages:</p>
 *
 * <ul>
 * <li>Java:<pre>{@code
 * Set<Map.Entry<String, String>> memberContextEntries = memberContext.getEntries();
 * String groupId = memberContext.parse("corda.groupId", String.class);
 * Instant modifiedTime = memberContext.parseOrNull("corda.modifiedTime", Instant.class);
 * Set<String> additionalInformation = memberContext.parseSet("additional.names", String.class);
 * List<EndpointInfo> endpoints = memberContext.parseList("corda.endpoints", EndpointInfo.class);
 * }</pre></li>
 * <li>Kotlin:<pre>{@code
 * val entries = memberContext.entries
 * val groupId = memberContext.parse("corda.groupId", String::class.java)
 * val modifiedTime = memberContext.parseOrNull("corda.modifiedTime", Instant::class.java)
 * val additionalInformation = memberContext.parseSet("additional.names", String::class.java)
 * val endpoints = memberContext.parseList("corda.endpoints", EndpointInfo::class.java)
 * }</pre></li>
 * </ul>
 *
 * <p>Properties are exposed either through methods on interfaces in the public APIs, or internally through extension
 * properties.</p>
 *
 * @see LayeredPropertyMap For further information on the properties and functions.
 */
public interface MemberContext extends LayeredPropertyMap {}
