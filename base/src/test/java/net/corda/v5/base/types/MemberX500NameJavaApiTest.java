package net.corda.v5.base.types;

import org.junit.jupiter.api.Test;

import javax.security.auth.x500.X500Principal;
import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberX500NameJavaApiTest {

    private final String commonName = "commonName";
    private final String organizationUnit = "organizationUnit";
    private final String organization = "organization";
    private final String locality = "London";
    private final String state = "state";
    private final String country = "GB";

    private final String name = "O=organization,L=London,C=GB";

    @Test
    public void requiredArguments() {
        final MemberX500Name cordaX500Name = new MemberX500Name(organization, locality, country);

        assertThat(cordaX500Name.getCommonName()).isNull();
        assertThat(cordaX500Name.getOrganizationUnit()).isNull();
        assertThat(cordaX500Name.getOrganization()).isEqualTo(organization);
        assertThat(cordaX500Name.getLocality()).isEqualTo(locality);
        assertThat(cordaX500Name.getState()).isNull();
        assertThat(cordaX500Name.getCountry()).isEqualTo(country);
    }

    @Test
    public void allArguments() {
        final MemberX500Name cordaX500Name = new MemberX500Name(
                commonName, organizationUnit, organization, locality, state, country
        );

        assertThat(cordaX500Name.getCommonName()).isEqualTo(commonName);
        assertThat(cordaX500Name.getOrganizationUnit()).isEqualTo(organizationUnit);
        assertThat(cordaX500Name.getOrganization()).isEqualTo(organization);
        assertThat(cordaX500Name.getLocality()).isEqualTo(locality);
        assertThat(cordaX500Name.getState()).isEqualTo(state);
        assertThat(cordaX500Name.getCountry()).isEqualTo(country);

        assertThat(cordaX500Name.getX500Principal()).isNotNull();
    }

    @Test
    public void constructor_withCommonNameOrganizationLocalityAndCountry() {
        final MemberX500Name cordaX500Name = new MemberX500Name(
                commonName, organization, locality, country
        );

        assertThat(cordaX500Name.getCommonName()).isEqualTo(commonName);
        assertThat(cordaX500Name.getOrganizationUnit()).isNull();
        assertThat(cordaX500Name.getOrganization()).isEqualTo(organization);
        assertThat(cordaX500Name.getLocality()).isEqualTo(locality);
        assertThat(cordaX500Name.getState()).isNull();
        assertThat(cordaX500Name.getCountry()).isEqualTo(country);
    }

    @Test
    public void build() {
        final X500Principal principal = new X500Principal(name);
        final MemberX500Name cordaX500Name = MemberX500Name.build(principal);

        assertThat(cordaX500Name.getOrganization()).isEqualTo(organization);
        assertThat(cordaX500Name.getLocality()).isEqualTo(locality);
        assertThat(cordaX500Name.getCountry()).isEqualTo(country);
    }

    @Test
    public void parse() {
        final MemberX500Name cordaX500Name = MemberX500Name.parse(name);

        assertThat(cordaX500Name.getOrganization()).isEqualTo(organization);
        assertThat(cordaX500Name.getLocality()).isEqualTo(locality);
        assertThat(cordaX500Name.getCountry()).isEqualTo(country);
    }

    @Test
    public void x500Principal() {
        final MemberX500Name cordaX500Name = new MemberX500Name(organization, locality, country);

        final X500Principal principal = cordaX500Name.getX500Principal();

        assertThat(principal).isNotNull();
    }

    @Test
    public void toAttributesMap() {
        final Map<String, String > map = MemberX500Name.toAttributesMap(
                "CN=alice, OU=Accounting, O=R3, L=Seattle, ST=Washington, C=US"
        );
        assertThat(map).contains(entry("CN", "alice"));
        assertThat(map).contains(entry("OU", "Accounting"));
        assertThat(map).contains(entry("O", "R3"));
        assertThat(map).contains(entry("L", "Seattle"));
        assertThat(map).contains(entry("ST", "Washington"));
        assertThat(map).contains(entry("C", "US"));
    }

    @Test
    public void parseFailesWithBadName() {
        assertThatThrownBy( ()-> {
                    MemberX500Name.parse("B00CA01FD5DC");
                }
        ).hasMessageContaining("B00CA01FD5DC")
                .isInstanceOf(IllegalArgumentException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }
}