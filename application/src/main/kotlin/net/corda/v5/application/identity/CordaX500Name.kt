package net.corda.v5.application.identity

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.membership.identity.MemberX500Name

@Suppress("LongParameterList")
@CordaSerializable
class CordaX500Name(
    commonName: String?,
    organisationUnit: String?,
    organisation: String,
    locality: String,
    state: String?,
    country: String
) : MemberX500Name(commonName, organisationUnit, organisation, locality, state, country) {
    constructor(commonName: String, organisation: String, locality: String, country: String) :
            this(
                commonName = commonName,
                organisationUnit = null,
                organisation = organisation,
                locality = locality,
                state = null,
                country = country
            )

    /**
     * @param organisation name of the organisation.
     * @param locality locality of the organisation, typically nearest major city.
     * @param country country the organisation is in, as an ISO 3166-1 2-letter country code.
     */
    constructor(organisation: String, locality: String, country: String)
            : this(null, null, organisation, locality, null, country)

    /**
     * @param memberX500Name the [MemberX500Name] we want to copy as [CordaX500Name]
     */
    constructor(memberX500Name: MemberX500Name) :
            this(
                memberX500Name.commonName,
                memberX500Name.organisationUnit,
                memberX500Name.organisation,
                memberX500Name.locality,
                memberX500Name.state,
                memberX500Name.country
            )
}