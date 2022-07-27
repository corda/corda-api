package net.corda.v5.ledger.common

import net.corda.v5.base.annotations.CordaSerializable
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Currency

/**
 * Represents an amount, which can be described as a positive quantity of an item.
 *
 * The quantity of an amount is represented as an integral value, as opposed to a decimal value.
 *
 * @param T The underlying type of the item being quantified by the current amount.
 * @property quantity The quantity of the item of the current amount.
 * @property item The item being quantified by the current amount.
 * @property isDivisible Determines whether the current amount can be subdivided into smaller amounts.
 */
@CordaSerializable
data class Amount<T : Any>(val quantity: BigInteger, val item: T) : Comparable<Amount<T>> {

    /**
     * Creates an instance of the [Amount] class.
     *
     * @param quantity The quantity of the item of the current amount.
     * @param item The item being quantified by the current amount.
     */
    constructor(quantity: Int, item: T) : this(quantity.toBigInteger(), item)

    /**
     * Creates an instance of the [Amount] class.
     *
     * @param quantity The quantity of the item of the current amount.
     * @param item The item being quantified by the current amount.
     */
    constructor(quantity: Long, item: T) : this(quantity.toBigInteger(), item)

    /**
     * Creates an instance of the [Amount] class.
     *
     * Note that [BigDecimal] is converted to an unscaled [BigInteger]
     *
     * @param quantity The quantity of the item of the current amount.
     * @param item The item being quantified by the current amount.
     */
    constructor(quantity: BigDecimal, item: T) : this(quantity.unscaledValue(), item)

    companion object {

        /**
         * Creates an [Amount] of zero of the specified item.
         *
         * @param T The underlying type of the item.
         * @property item The item being quantified.
         * @return Returns an [Amount] of zero of the item.
         */
        @JvmStatic
        fun <T : Any> zero(item: T): Amount<T> {
            return Amount(BigInteger.ZERO, item)
        }

        /**
         * Creates an [Amount] of one of the specified item.
         *
         * @param T The underlying type of the item.
         * @property item The item being quantified.
         * @return Returns an [Amount] of one of the item.
         */
        @JvmStatic
        fun <T : Any> one(item: T): Amount<T> {
            return Amount(BigInteger.ONE, item)
        }

        @JvmStatic
        fun fromCurrency(quantity: Int, currency: Currency): Amount<Currency> {
            return fromCurrency(quantity.toBigInteger(), currency)
        }

        @JvmStatic
        fun fromCurrency(quantity: Long, currency: Currency): Amount<Currency> {
            return fromCurrency(quantity.toBigInteger(), currency)
        }

        @JvmStatic
        fun fromCurrency(quantity: BigInteger, currency: Currency): Amount<Currency> {
            return fromCurrency(quantity.toBigDecimal(currency.defaultFractionDigits), currency)
        }

        @JvmStatic
        fun fromCurrency(quantity: BigDecimal, currency: Currency): Amount<Currency> {
            val quantityScale = quantity.scale()
            val currencyScale = currency.defaultFractionDigits

            check(quantityScale <= currencyScale) {
                "The scale of the quantity ($quantityScale) must not exceed the scale of the currency ($currencyScale)."
            }

            return Amount(quantity.setScale(currencyScale).unscaledValue(), currency)
        }
    }

    init {
        checkQuantity()
    }

    val isDivisible: Boolean get() = quantity > BigInteger.ONE

    /**
     * Adds the specified amount to the current [Amount].
     *
     * @param value The amount to add to the current [Amount].
     * @return Returns an [Amount] representing the sum of the specified quantity added to the current quantity.
     */
    operator fun plus(value: BigInteger): Amount<T> {
        return Amount(quantity + value, item)
    }

    /**
     * Adds the specified amount to the current [Amount].
     *
     * @param value The amount to add to the current [Amount].
     * @return Returns an [Amount] representing the sum of the specified quantity added to the current quantity.
     */
    operator fun plus(value: Int): Amount<T> {
        return plus(value.toBigInteger())
    }

    /**
     * Adds the specified amount to the current [Amount].
     *
     * @param value The amount to add to the current [Amount].
     * @return Returns an [Amount] representing the sum of the specified quantity added to the current quantity.
     */
    operator fun plus(value: Long): Amount<T> {
        return plus(value.toBigInteger())
    }

    /**
     * Adds the specified amount to the current [Amount].
     *
     * @param value The amount to add to the current [Amount].
     * @return Returns an [Amount] representing the sum of the specified quantity added to the current quantity.
     */
    operator fun plus(value: Amount<T>): Amount<T> {
        checkItem(value)
        return plus(value.quantity)
    }

    /**
     * Subtracts the specified amount from the current [Amount].
     *
     * @param value The amount to subtract from the current [Amount].
     * @return Returns an [Amount] representing the difference between the specified quantity subtracted from the current quantity.
     */
    operator fun minus(value: BigInteger): Amount<T> {
        return Amount(quantity - value, item)
    }

    /**
     * Subtracts the specified amount from the current [Amount].
     *
     * @param value The amount to subtract from the current [Amount].
     * @return Returns an [Amount] representing the difference between the specified quantity subtracted from the current quantity.
     */
    operator fun minus(value: Int): Amount<T> {
        return minus(value.toBigInteger())
    }

    /**
     * Subtracts the specified amount from the current [Amount].
     *
     * @param value The amount to subtract from the current [Amount].
     * @return Returns an [Amount] representing the difference between the specified quantity subtracted from the current quantity.
     */
    operator fun minus(value: Long): Amount<T> {
        return minus(value.toBigInteger())
    }

    /**
     * Subtracts the specified amount from the current [Amount].
     *
     * @param value The amount to subtract from the current [Amount].
     * @return Returns an [Amount] representing the difference between the specified quantity subtracted from the current quantity.
     */
    operator fun minus(value: Amount<T>): Amount<T> {
        checkItem(value)
        return minus(value.quantity)
    }

    /**
     * Multiplies the specified amount by the current [Amount].
     *
     * @param value The amount to multiply by the current [Amount].
     * @return Returns an [Amount] representing the product of the specified quantity multiplied by the current quantity.
     */
    operator fun times(value: BigInteger): Amount<T> {
        return Amount(quantity * value, item)
    }

    /**
     * Multiplies the specified amount by the current [Amount].
     *
     * @param value The amount to multiply by the current [Amount].
     * @return Returns an [Amount] representing the product of the specified quantity multiplied by the current quantity.
     */
    operator fun times(value: Int): Amount<T> {
        return times(value.toBigInteger())
    }

    /**
     * Multiplies the specified amount by the current [Amount].
     *
     * @param value The amount to multiply by the current [Amount].
     * @return Returns an [Amount] representing the product of the specified quantity multiplied by the current quantity.
     */
    operator fun times(value: Long): Amount<T> {
        return times(value.toBigInteger())
    }

    /**
     * Multiplies the specified amount by the current [Amount].
     *
     * @param value The amount to multiply by the current [Amount].
     * @return Returns an [Amount] representing the product of the specified quantity multiplied by the current quantity.
     */
    operator fun times(value: Amount<T>): Amount<T> {
        checkItem(value)
        return times(value.quantity)
    }

    /**
     * Divides the current [Amount] by the specified amount.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the quotient of the current quantity divided by the specified quantity.
     */
    operator fun div(value: BigInteger): Amount<T> {
        return Amount(quantity / value, item)
    }

    /**
     * Divides the current [Amount] by the specified amount.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the quotient of the current quantity divided by the specified quantity.
     */
    operator fun div(value: Int): Amount<T> {
        return div(value.toBigInteger())
    }

    /**
     * Divides the current [Amount] by the specified amount.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the quotient of the current quantity divided by the specified quantity.
     */
    operator fun div(value: Long): Amount<T> {
        return div(value.toBigInteger())
    }

    /**
     * Divides the current [Amount] by the specified amount.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the quotient of the current quantity divided by the specified quantity.
     */
    operator fun div(value: Amount<T>): Amount<T> {
        checkItem(value)
        return div(value.quantity)
    }

    /**
     * Divides the current [Amount] by the specified amount and obtains the remainder.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the remainder of the current quantity divided by the specified quantity.
     */
    operator fun rem(value: BigInteger): Amount<T> {
        return Amount(quantity % value, item)
    }

    /**
     * Divides the current [Amount] by the specified amount and obtains the remainder.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the remainder of the current quantity divided by the specified quantity.
     */
    operator fun rem(value: Int): Amount<T> {
        return rem(value.toBigInteger())
    }

    /**
     * Divides the current [Amount] by the specified amount and obtains the remainder.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the remainder of the current quantity divided by the specified quantity.
     */
    operator fun rem(value: Long): Amount<T> {
        return rem(value.toBigInteger())
    }

    /**
     * Divides the current [Amount] by the specified amount and obtains the remainder.
     *
     * @param value The amount to divide the current [Amount] by.
     * @return Returns an [Amount] representing the remainder of the current quantity divided by the specified quantity.
     */
    operator fun rem(value: Amount<T>): Amount<T> {
        checkItem(value)
        return rem(value.quantity)
    }

    /**
     * Compares the current [Amount] with another object of the same type and returns an integer that indicates whether the
     * current [Amount] precedes, follows, or occurs in the same position in the sort order as the other object.
     *
     * @param other The other [Amount] to compare with the current [Amount].
     * @return A value that indicates the relative order of the objects being compared.
     *
     * The return value has these meanings:
     * - Less than zero: This instance precedes the other instance in the sort order.
     * - Zero: This instance occurs in the same position in the sort order.
     * - Greater than zero: This instance follows the other instance in the sort order.
     */
    override fun compareTo(other: Amount<T>): Int {
        checkItem(other)
        return quantity.compareTo(other.quantity)
    }

    /**
     * Returns a [String] that represents the current [Amount].
     *
     * @return Returns a [String] that represents the current [Amount].
     */
    override fun toString(): String {
        return "$quantity of $item"
    }

    /**
     * Returns a [BigDecimal] that represents the current [Amount].
     *
     * @param scale The scale to apply to the [BigDecimal] value.
     * @return Returns a [BigDecimal] that represents the current [Amount].
     */
    fun toBigDecimal(scale: Int = 0): BigDecimal {
        return quantity.toBigDecimal(scale)
    }

    /**
     * Checks that the current item is equal to the other item.
     *
     * @param amount The amount containing the other item to check.
     * @throws IllegalArgumentException if the current item is not equal to the other item.
     */
    private fun checkItem(amount: Amount<T>) {
        require(item == amount.item) { "Amount items are mismatched. Current values are $item and ${amount.item}." }
    }

    /**
     * Checks that the current quantity is greater than, or equal to zero.
     *
     * @throws IllegalArgumentException if the amount is less than zero.
     */
    private fun checkQuantity() {
        require(quantity >= BigInteger.ZERO) { "Amount quantity must be greater than or equal to zero. Current value is: $quantity." }
    }
}
