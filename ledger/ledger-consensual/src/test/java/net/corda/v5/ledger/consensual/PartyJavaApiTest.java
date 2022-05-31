package net.corda.v5.ledger.consensual;

import net.corda.v5.base.types.MemberX500Name;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.PublicKey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartyJavaApiTest {
    private final Party party = mock(Party.class);

    @Test
    public void getName() {
        MemberX500Name testMemberX500Name = new MemberX500Name("Bob Plc", "Rome", "IT");
        when(party.getName()).thenReturn(testMemberX500Name);

        MemberX500Name result = party.getName();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(testMemberX500Name);
    }

    @Test
    void owningKeyTest() {
        final PublicKey publicKey = mock(PublicKey.class);
        Mockito.when(party.getOwningKey()).thenReturn(publicKey);

        Assertions.assertThat(party.getOwningKey()).isNotNull();
        Assertions.assertThat(party.getOwningKey()).isEqualTo(publicKey);
    }
    
    @Test
    public void description() {
        String test = "test";
        when(party.description()).thenReturn(test);

        String result = party.description();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(test);
    }
}
