package net.corda.v5.application.time

import net.corda.v5.base.annotations.DoNotImplement
import java.time.Clock
import java.time.Instant

/** A [Clock] that tokenizes itself when serialized, and delegates to an underlying [Clock] implementation. */
@DoNotImplement
interface CordaClock {
    /**
     * Get the current timestamp.
     *
     * Equivalent to Clock.instant in the Java standard library. Note that the precision of this instant will depend on
     * the precision of the underlying system clock.
     *
     * The instant will always use UTC.
     *
     * @return The timestamp at the point of invocation.
     */
    fun instant(): Instant
}
