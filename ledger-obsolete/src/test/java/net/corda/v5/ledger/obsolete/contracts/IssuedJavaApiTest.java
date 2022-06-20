package net.corda.v5.ledger.obsolete.contracts;

import net.corda.v5.base.types.OpaqueBytes;
import net.corda.v5.ledger.obsolete.identity.AbstractParty;
import net.corda.v5.ledger.obsolete.identity.PartyAndReference;
import net.corda.v5.ledger.obsolete.contracts.Amount;
import net.corda.v5.ledger.obsolete.contracts.Issued;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.mockito.Mockito.mock;

public class IssuedJavaApiTest {
    private final AbstractParty abstractParty = mock(AbstractParty.class);
    private final OpaqueBytes reference = new OpaqueBytes("bytes".getBytes());
    private final PartyAndReference issuer = new PartyAndReference(abstractParty, reference);
    private final Amount<Currency> currencyAmount = Amount.parseCurrency("£25000000");
    private final Issued<Amount<Currency>> amountIssued = new Issued<>(issuer, currencyAmount);

    @Test
    public void partyAndReference() {
        final PartyAndReference partyAndReference = amountIssued.getIssuer();

        Assertions.assertThat(partyAndReference).isEqualTo(issuer);
    }

    @Test
    public void product() {
        final Amount<Currency> amountIssued1 = amountIssued.getProduct();

        Assertions.assertThat(amountIssued1).isEqualTo(currencyAmount);
    }
}
