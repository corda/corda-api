package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import java.time.Duration
import java.time.Instant

/**
 * Defines an interval on a timeline; not a single, instantaneous point.
 *
 * There is no such thing as "exact" time in distributed systems, due to the underlying physics involved, and other
 * issues such as network latency. A time window represents an approximation of an instant with a margin of tolerance,
 * and may be fully bounded, or unbounded towards negative or positive infinity.
 *
 * @property from The boundary at which the time window begins, or null if the time window is unbounded towards negative infinity.
 * @property until The boundary at which the time window ends, or null if the time window is unbounded towards positive infinity.
 * @property midpoint The midpoint between a fully bounded time window, or null if the time window is unbounded towards positive or negative infinity.
 * @property duration The duration of a fully bounded time window, or null if the time window is unbounded towards positive or negative infinity.
 */
@CordaSerializable
interface TimeWindow {

    companion object {

        /**
         * Creates a fully bounded time window between the specified from and until instants.
         *
         * @param from The boundary at which the time window begins.
         * @param until The boundary at which the time window ends.
         * @return Returns a fully bounded time window between the specified from and until instants.
         */
        @JvmStatic
        fun between(from: Instant, until: Instant): TimeWindow {
            return Between(from, until)
        }

        /**
         * Creates a fully bounded time window between the specified from instant until the specified duration.
         *
         * @param from The boundary at which the time window begins.
         * @param duration The duration of the time window; effectively the boundary at which the time window ends.
         * @return Returns a fully bounded time window between the specified from instant until the specified duration.
         */
        @JvmStatic
        fun between(from: Instant, duration: Duration): TimeWindow {
            return Between(from, from + duration)
        }

        /**
         * Creates an unbounded time window from the specified instant tending towards positive infinity.
         *
         * @param from The boundary at which the time window begins.
         * @return Returns an unbounded time window from the specified instant tending towards positive infinity.
         */
        @JvmStatic
        fun from(from: Instant): TimeWindow {
            return From(from)
        }

        /**
         * Creates an unbounded time window until the specified instant tending towards negative infinity.
         *
         * @param until The boundary at which the time window ends.
         * @return Returns an unbounded time window until the specified instant tending towards negative infinity.
         */
        @JvmStatic
        fun until(until: Instant): TimeWindow {
            return Until(until)
        }

        /**
         * Creates a fully bounded time window between the specified midpoint, and with the specified tolerance
         * applied to left and to the right of the midpoint; i.e. (midpoint - tolerance to midpoint + tolerance).
         *
         * @param midpoint The midpoint of the bounded time window.
         * @param tolerance The tolerance that defines the boundaries of the time window.
         * @return Returns a fully bounded time window between the specified midpoint, and with the specified tolerance.
         */
        @JvmStatic
        fun within(midpoint: Instant, tolerance: Duration): TimeWindow {
            return Between(midpoint - tolerance, midpoint + tolerance)
        }
    }

    val from: Instant?
    val until: Instant?
    val midpoint: Instant?
    val duration: Duration? get() = if (from == null || until == null) null else Duration.between(from, until)

    /**
     * Determines whether the current [TimeWindow] contains the specified [Instant].
     *
     * @param instant The [Instant] to check is contained within the current [TimeWindow].
     * @return Returns true if the current [TimeWindow] contains the specified [Instant]; otherwise, false.
     */
    operator fun contains(instant: Instant): Boolean

    /**
     * Represents an unbounded time window that tends towards positive infinity.
     *
     * @constructor Creates a new instance of the [From] data class.
     * @property from The boundary at which the time window begins.
     * @property until Always null as this time window tends towards positive infinity.
     * @property midpoint Always null as this time window tends towards positive infinity.
     * @property duration Always null as this time window tends towards positive infinity.
     */
    // TODO : Move to corda-runtime-os
    private data class From(override val from: Instant) : TimeWindow {

        override val until: Instant? = null
        override val midpoint: Instant? = null

        /**
         * Determines whether the current [TimeWindow] contains the specified [Instant].
         *
         * @param instant The [Instant] to check is contained within the current [TimeWindow].
         * @return Returns true if the current [TimeWindow] contains the specified [Instant]; otherwise, false.
         */
        override fun contains(instant: Instant): Boolean {
            return instant >= from
        }

        /**
         * Returns a string that represents the current object.
         *
         * @return Returns a string that represents the current object.
         */
        override fun toString(): String {
            return "$from to infinity"
        }
    }

    /**
     * Represents an unbounded time window that tends towards negative infinity.
     *
     * @constructor Creates a new instance of the [Until] data class.
     * @property from Always null as this time window tends towards negative infinity.
     * @property until The boundary at which the time window ends.
     * @property midpoint Always null as this time window tends towards positive infinity.
     * @property duration Always null as this time window tends towards positive infinity.
     */
    // TODO : Move to corda-runtime-os
    private data class Until(override val until: Instant) : TimeWindow {

        override val from: Instant? = null
        override val midpoint: Instant? = null

        /**
         * Determines whether the current [TimeWindow] contains the specified [Instant].
         *
         * @param instant The [Instant] to check is contained within the current [TimeWindow].
         * @return Returns true if the current [TimeWindow] contains the specified [Instant]; otherwise, false.
         */
        override fun contains(instant: Instant): Boolean {
            return instant <= until
        }

        /**
         * Returns a string that represents the current object.
         *
         * @return Returns a string that represents the current object.
         */
        override fun toString(): String {
            return "infinity to $until"
        }
    }

    /**
     * Represents a fully bounded time window.
     *
     * @property from The boundary at which the time window begins.
     * @property until The boundary at which the time window ends.
     * @property midpoint The midpoint between the current, fully bounded time window.
     * @property duration The duration of the current, fully bounded time window.
     */
    // TODO : Move to corda-runtime-os
    private data class Between(override val from: Instant, override val until: Instant) : TimeWindow {

        init {
            require(from < until) { "from must be earlier than until." }
        }

        override val midpoint: Instant get() = from + Duration.between(from, until).dividedBy(2)

        override fun contains(instant: Instant): Boolean {
            return instant >= from && instant < until
        }

        override fun toString(): String {
            return "$from to $until"
        }
    }
}
