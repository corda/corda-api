package net.corda.v5.application.time

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.serialization.SingletonSerializeAsToken
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

/** A [Clock] that tokenizes itself when serialized, and delegates to an underlying [Clock] implementation. */
@DoNotImplement
interface CordaClock : SingletonSerializeAsToken, CordaFlowInjectable, CordaServiceInjectable {
    /**
     * Get the current timestamp.
     *
     * Equivalent to Clock.instant in the Java standard library. Note that the precision of this instant will depend on
     * the precision of the underlying system clock.
     *
     * @return The timestamp at the point of invocation.
     */
    fun instant(): Instant

    /**
     * Get the current zone.
     *
     * Equivalent to Clock.getZone in the Java standard library.
     *
     * @return The zone ID of the current time zone.
     */
    fun zone(): ZoneId
}
