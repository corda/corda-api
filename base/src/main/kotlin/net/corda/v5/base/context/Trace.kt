package net.corda.v5.base.context

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.util.UuidGenerator
import java.time.Instant

/**
 * Contextual tracing information, including invocation and session id.
 */
@CordaSerializable
data class Trace(val invocationId: InvocationId, val sessionId: SessionId) {

    companion object {

        /**
         * Creates a [Trace] using the passed in [invocationId] and [sessionId].
         *
         * @param invocationId The [InvocationId].
         * @param sessionId The [SessionId].
         */
        @JvmStatic
        fun newInstance(invocationId: InvocationId, sessionId: SessionId): Trace = Trace(invocationId, sessionId)

        /**
         * Creates a [Trace] using the passed in [invocationId].
         *
         * The [Trace]'s [SessionId] will be generated from this [invocationId].
         *
         * @param invocationId The [InvocationId].
         */
        @JvmStatic
        fun newInstance(invocationId: InvocationId): Trace =
            Trace(invocationId, SessionId(invocationId.value, invocationId.timestamp))

        /**
         * Creates a [Trace] using the passed in [sessionId].
         *
         * The [Trace]'s [InvocationId] will be randomly generated.
         *
         * @param sessionId The [SessionId]
         */
        @JvmStatic
        fun newInstance(sessionId: SessionId): Trace = Trace(InvocationId.newInstance(), sessionId)

        /**
         * Creates a [Trace] with a random [InvocationId] and a [SessionId] generated from its value.
         */
        @JvmStatic
        fun newInstance(): Trace {
            val invocationId = InvocationId.newInstance()
            return Trace(invocationId, SessionId(invocationId.value, invocationId.timestamp))
        }
    }

    /**
     * Represents id and timestamp of an invocation.
     */
    @CordaSerializable
    class InvocationId(value: String, timestamp: Instant) : Id<String>(value, TYPE, timestamp) {

        companion object {
            private const val TYPE = "Invocation"

            /**
             * Creates an [InvocationId].
             *
             * @param value The value of the created [InvocationId].
             * @param timestamp The timestamp.
             */
            @JvmStatic
            fun newInstance(value: String, timestamp: Instant): InvocationId = InvocationId(value, timestamp)

            /**
             * Creates an [InvocationId].
             *
             * @param value The value of the created [InvocationId].
             */
            @JvmStatic
            fun newInstance(value: String): InvocationId = InvocationId(value, Instant.now())

            /**
             * Creates an [InvocationId].
             *
             * @param timestamp The timestamp.
             */
            @JvmStatic
            fun newInstance(timestamp: Instant): InvocationId = InvocationId(UuidGenerator.next().toString(), timestamp)

            /**
             * Creates an [InvocationId].
             */
            @JvmStatic
            fun newInstance(): InvocationId = InvocationId(UuidGenerator.next().toString(), Instant.now())
        }
    }

    /**
     * Represents id and timestamp of a session.
     */
    @CordaSerializable
    class SessionId(value: String, timestamp: Instant) : Id<String>(value, TYPE, timestamp) {

        companion object {
            private const val TYPE = "Session"

            /**
             * Creates a [SessionId].
             *
             * @param value The value of the created [SessionId].
             * @param timestamp The timestamp.
             */
            @JvmStatic
            fun newInstance(value: String, timestamp: Instant): SessionId = SessionId(value, timestamp)

            /**
             * Creates a [SessionId].
             *
             * @param value The value of the created [SessionId].
             */
            @JvmStatic
            fun newInstance(value: String): SessionId = SessionId(value, Instant.now())

            /**
             * Creates a [SessionId].
             *
             * @param timestamp The timestamp.
             */
            @JvmStatic
            fun newInstance(timestamp: Instant): SessionId = SessionId(UuidGenerator.next().toString(), timestamp)

            /**
             * Creates a [SessionId].
             */
            @JvmStatic
            fun newInstance(): SessionId = SessionId(UuidGenerator.next().toString(), Instant.now())
        }
    }
}