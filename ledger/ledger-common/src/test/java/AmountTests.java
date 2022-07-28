import net.corda.v5.ledger.common.Amount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;

public class AmountTests {

    @Test
    @DisplayName("Amount should be constructable from BigInteger")
    public void amountShouldBeConstructableFromBigInteger() {
        Amount<String> amount = new Amount<>(BigInteger.ONE, "test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.ONE);
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount should be constructable from Integer")
    public void amountShouldBeConstructableFromInteger() {
        Amount<String> amount = new Amount<>(1, "test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.ONE);
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount should be constructable from Long")
    public void amountShouldBeConstructableFromLong() {
        Amount<String> amount = new Amount<>(1L, "test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.ONE);
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount should be constructable from BigDecimal")
    public void amountShouldBeConstructableFromBigDecimal() {
        Amount<String> amount = new Amount<>(BigDecimal.ONE, "test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.ONE);
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount should be constructable from BigDecimal with preserved fractional component")
    public void amountShouldBeConstructableFromBigDecimalPreserved() {
        Amount<String> amount = new Amount<>(BigDecimal.valueOf(123.45), "test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.valueOf(12345));
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount.zero should construct a zero-quantity amount")
    public void amountZeroShouldConstructZeroQuantityAmount() {
        Amount<String> amount = Amount.zero("test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.ZERO);
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount.one should construct a one-quantity amount")
    public void amountZeroShouldConstructOneQuantityAmount() {
        Amount<String> amount = Amount.one("test");

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.ONE);
        Assertions.assertThat(amount.getItem()).isEqualTo("test");
    }

    @Test
    @DisplayName("Amount.fromCurrency should construct an amount from an Integer and the specified currency")
    public void amountFromCurrencyShouldConstructFromIntegerAndCurrency() {
        Currency currency = Currency.getInstance("GBP");
        Amount<Currency> amount = Amount.fromCurrency(10, currency);

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.TEN);
        Assertions.assertThat(amount.getItem()).isEqualTo(currency);
    }

    @Test
    @DisplayName("Amount.fromCurrency should construct an amount from a Long and the specified currency")
    public void amountFromCurrencyShouldConstructFromLongAndCurrency() {
        Currency currency = Currency.getInstance("GBP");
        Amount<Currency> amount = Amount.fromCurrency(10L, currency);

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.TEN);
        Assertions.assertThat(amount.getItem()).isEqualTo(currency);
    }

    @Test
    @DisplayName("Amount.fromCurrency should construct an amount from a BigInteger and the specified currency")
    public void amountFromCurrencyShouldConstructFromBigIntegerAndCurrency() {
        Currency currency = Currency.getInstance("GBP");
        Amount<Currency> amount = Amount.fromCurrency(BigInteger.TEN, currency);

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.TEN);
        Assertions.assertThat(amount.getItem()).isEqualTo(currency);
    }

    @Test
    @DisplayName("Amount.fromCurrency should construct an amount from a BigDecimal and the specified currency")
    public void amountFromCurrencyShouldConstructFromBigDecimalAndCurrency() {
        Currency currency = Currency.getInstance("GBP");
        Amount<Currency> amount = Amount.fromCurrency(BigDecimal.TEN, currency);

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.TEN);
        Assertions.assertThat(amount.getItem()).isEqualTo(currency);
    }

    @Test
    @DisplayName("Amount.fromCurrency should construct an amount from a BigDecimal and the specified currency with preserved fractional component")
    public void amountFromCurrencyShouldConstructFromBigDecimalAndCurrencyPreserved() {
        Currency currency = Currency.getInstance("GBP");
        Amount<Currency> amount = Amount.fromCurrency(BigDecimal.valueOf(123.45), currency);

        Assertions.assertThat(amount.getQuantity()).isEqualTo(BigInteger.valueOf(12345));
        Assertions.assertThat(amount.getItem()).isEqualTo(currency);
    }

    public void blazh() {
        Currency currency = Currency.getInstance("GBP");
        Amount<Currency> amount = Amount.fromCurrency(BigDecimal.valueOf(123.45), currency);

        amount.toBigDecimal();
    }
}

