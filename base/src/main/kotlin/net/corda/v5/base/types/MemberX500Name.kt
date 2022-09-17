package net.corda.v5.base.types

import net.corda.v5.base.annotations.CordaSerializable
import java.util.Locale
import java.util.Objects
import javax.naming.InvalidNameException
import javax.naming.directory.BasicAttributes
import javax.naming.ldap.LdapName
import javax.naming.ldap.Rdn
import javax.security.auth.x500.X500Principal

/**
 * X.500 distinguished name data type customised to how Corda uses names. This restricts the attributes to those Corda
 * supports, and requires that organization, locality and country attributes are specified. See also RFC 4519 for
 * the underlying attribute type definitions.
 *
 * The class also guaranties the reliable equality comparison regardless which order the attributes are specified when
 * parsing from the string or X500principal as well outputs the attributes to string in predictable order.
 *
 * @property commonName Optional name by the which the entity is usually known. Used only for services (for
 * organizations, the [organization] property is the name). Corresponds to the "CN" attribute type.
 * @property organizationUnit Optional name of a unit within the [organization]. Corresponds to the "OU" attribute type.
 * @property organization Name of the organization. Corresponds to the "O" attribute type.
 * @property locality Locality of the organization, typically the nearest major city. For distributed services this would be
 * where one of the organizations is based. Corresponds to the "L" attribute type.
 * @property state The full name of the state or province the organization is based in. Corresponds to the "ST"
 * attribute type.
 * @property country Country the organization is in, as an ISO 3166-1 2-letter country code. Corresponds to the "C"
 * attribute type.
*/
@Suppress("LongParameterList")
@CordaSerializable
class MemberX500Name(
    val commonName: String?,
    val organizationUnit: String?,
    val organization: String,
    val locality: String,
    val state: String?,
    val country: String
) : Comparable<MemberX500Name> {
    companion object {
        const val MAX_LENGTH_ORGANIZATION = 128
        const val MAX_LENGTH_LOCALITY = 64
        const val MAX_LENGTH_STATE = 64
        const val MAX_LENGTH_ORGANIZATION_UNIT = 64
        const val MAX_LENGTH_COMMON_NAME = 64

        private const val ATTRIBUTE_COMMON_NAME = "CN"
        private const val ATTRIBUTE_ORGANIZATION_UNIT = "OU"
        private const val ATTRIBUTE_ORGANIZATION = "O"
        private const val ATTRIBUTE_LOCALITY = "L"
        private const val ATTRIBUTE_STATE = "ST"
        private const val ATTRIBUTE_COUNTRY = "C"

        private const val UNSPECIFIED_COUNTRY = "ZZ"

        private val supportedAttributes = setOf(
            ATTRIBUTE_COMMON_NAME,
            ATTRIBUTE_ORGANIZATION_UNIT,
            ATTRIBUTE_ORGANIZATION,
            ATTRIBUTE_LOCALITY,
            ATTRIBUTE_STATE,
            ATTRIBUTE_COUNTRY
        )

        private val countryCodes: Set<String> = Locale.getISOCountries().toSet() + UNSPECIFIED_COUNTRY

        private val comparator by lazy {
            compareBy<MemberX500Name>(
                { it.commonName },
                { it.organizationUnit },
                { it.organization },
                { it.locality },
                { it.state },
                { it.country },
            )
        }

        /**
         * Creates an instance of [MemberX500Name] from specified [X500Principal]
         *
         * @throws [IllegalArgumentException] if required attributes are missing, constrains are not satisfied.
         */
        @JvmStatic
        fun build(principal: X500Principal): MemberX500Name = parse(toAttributesMap(principal))

        /**
         * Creates an instance of [MemberX500Name] by parsing the string representation of X500 name, like
         * "CN=Alice, OU=Engineering, O=R3, L=London, C=GB".
         * Constrains are the same as for [toAttributesMap] plus some additional constrains:
         * - O, L, C are required attributes
         *
         * @throws [IllegalArgumentException] if required attributes are missing, constrains are not satisfied or
         * the name is improperly specified.
         */
        @JvmStatic
        fun parse(name: String): MemberX500Name = parse(toAttributesMap(name))

        /**
         * Parses the string representation of X500 name and builds the attribute map where the key is the
         * attributes keys, like CN, O, etc.
         * Constrains:
         * - the RDNs cannot be multivalued
         * - the attributes must have single value
         * - the only supported attributes are C, ST, L, O, OU, CN
         * - attributes cannot be duplicated
         *
         * @throws [IllegalArgumentException] if required attributes are missing, constrains are not satisfied or
         * the name is improperly specified.
         */
        @JvmStatic
        fun toAttributesMap(name: String): Map<String, String> {
            // X500Principal is used to sanitise the syntax as the LdapName will let through such string as
            // "O=VALID, L=IN,VALID, C=DE, OU=VALID, CN=VALID" where the (L) have to be escaped
            return toAttributesMap(X500Principal(name))
        }

        private fun parse(attrsMap: Map<String, String>): MemberX500Name {
            val cn = attrsMap[ATTRIBUTE_COMMON_NAME]
            val ou = attrsMap[ATTRIBUTE_ORGANIZATION_UNIT]
            val o = requireNotNull(attrsMap[ATTRIBUTE_ORGANIZATION]) { "Member X.500 names must include an O attribute" }
            val l = requireNotNull(attrsMap[ATTRIBUTE_LOCALITY]) { "Member X.500 names must include an L attribute" }
            val st = attrsMap[ATTRIBUTE_STATE]
            val c = requireNotNull(attrsMap[ATTRIBUTE_COUNTRY]) { "Member X.500 names must include an C attribute" }
            return MemberX500Name(cn, ou, o, l, st, c)
        }

        private fun toAttributesMap(principal: X500Principal): Map<String, String> {
            val result = mutableMapOf<String, String>()
            try {
                LdapName(principal.toString()).rdns.forEach { rdn ->
                    require(rdn.size() == 1) {
                        "The RDN '$rdn' must not be multi-valued."
                    }
                    rdn.toAttributes().all.asSequence().forEach {
                        require(it.size() == 1) {
                            "Attribute '${it.id}' have to contain only single value."
                        }
                        val value = it.get(0)
                        require(value is String) {
                            "Attribute's '${it.id}' value must be a string"
                        }
                        require(!result.containsKey(it.id)) {
                            "Duplicate attribute ${it.id}"
                        }
                        result[it.id] = value
                    }
                }
            } catch (e: InvalidNameException) {
                throw IllegalArgumentException(e.message, e)
            }
            if (supportedAttributes.isNotEmpty()) {
                (result.keys - supportedAttributes).let { unsupported ->
                    require(unsupported.isEmpty()) {
                        "The following attribute${if (unsupported.size > 1) "s are" else " is"} not supported in Corda: " +
                                unsupported.map { it }
                    }
                }
            }
            return result
        }
    }

    /**
     * @param commonName Optional name by the which the entity is usually known. Used only for services (for
     * organizations, the [organization] property is the name). Corresponds to the "CN" attribute type.
     * @param organization Name of the organization.
     * @param locality Locality of the organization, typically the nearest major city.
     * @param country Country the organization is in, as an ISO 3166-1 2-letter country code.
     */
    constructor(commonName: String, organization: String, locality: String, country: String) :
            this(
                commonName = commonName,
                organizationUnit = null,
                organization = organization,
                locality = locality,
                state = null,
                country = country
            )

    /**
     * @param organization Name of the organization.
     * @param locality Locality of the organization, typically nearest major city.
     * @param country Country the organization is in, as an ISO 3166-1 2-letter country code.
     */
    constructor(organization: String, locality: String, country: String) :
            this(null, null, organization, locality, null, country)

    init {
        require(country in countryCodes) { "Invalid country code $country" }

        state?.let {
            require(it.isNotBlank()) {
                "State attribute (ST) if specified then it must be not blank."
            }
            require(it.length < MAX_LENGTH_STATE) { "State attribute (ST) must contain less then $MAX_LENGTH_STATE characters." }
        }

        require(locality.isNotBlank()) {
            "Locality attribute (L) must not be blank."
        }
        require(locality.length < MAX_LENGTH_LOCALITY) { "Locality attribute (L) must contain less then $MAX_LENGTH_LOCALITY characters." }

        require(organization.isNotBlank()) {
            "Organization attribute (O) if specified then it must be not blank."
        }
        require(organization.length < MAX_LENGTH_ORGANIZATION) {
            "Organization attribute (O) must contain less then $MAX_LENGTH_ORGANIZATION characters."
        }

        organizationUnit?.let {
            require(it.isNotBlank()) {
                "Organization unit attribute (OU) if specified then it must be not blank."
            }
            require(it.length < MAX_LENGTH_ORGANIZATION_UNIT) {
                "Organization Unit attribute (OU) must contain less then $MAX_LENGTH_ORGANIZATION_UNIT characters."
            }
        }

        commonName?.let {
            require(it.isNotBlank()) {
                "Common name attribute (CN) must not be blank."
            }
            require(it.length < MAX_LENGTH_COMMON_NAME) {
                "Common Name attribute (CN) must contain less then $MAX_LENGTH_COMMON_NAME characters."
            }
        }
    }

    /**
     * Returns the [X500Principal] equivalent of this name where the order of RDNs is
     * C, ST, L, O, OU, CN (the printing order would be reversed)
     *
     * @throws IllegalArgumentException If a valid RDN cannot be constructed using the given attributes.
     */
    val x500Principal: X500Principal by lazy(LazyThreadSafetyMode.PUBLICATION) {
        try {
            val rdns = mutableListOf<Rdn>().apply {
                add(Rdn(BasicAttributes(ATTRIBUTE_COUNTRY, country)))
                state?.let {
                    add(Rdn(BasicAttributes(ATTRIBUTE_STATE, it)))
                }
                add(Rdn(BasicAttributes(ATTRIBUTE_LOCALITY, locality)))
                add(Rdn(BasicAttributes(ATTRIBUTE_ORGANIZATION, organization)))
                organizationUnit?.let {
                    add(Rdn(BasicAttributes(ATTRIBUTE_ORGANIZATION_UNIT, it)))
                }
                commonName?.let {
                    add(Rdn(BasicAttributes(ATTRIBUTE_COMMON_NAME, it)))
                }
            }
            X500Principal(LdapName(rdns).toString())
        } catch (e: InvalidNameException) {
            throw IllegalArgumentException(e.message, e)
        }
    }

    /**
     * Returns the string equivalent of this name where the order of RDNs is CN, OU, O, L, ST, C
     */
    override fun toString(): String = x500Principal.toString()
    override fun compareTo(other: MemberX500Name): Int {
        return comparator.compare(this, other)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberX500Name

        if (commonName != other.commonName) return false
        if (organizationUnit != other.organizationUnit) return false
        if (organization != other.organization) return false
        if (locality != other.locality) return false
        if (state != other.state) return false
        if (country != other.country) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(
            commonName,
            organizationUnit,
            organization,
            locality,
            state,
            country
        )
    }
}
