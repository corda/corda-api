package net.corda.v5.base.annotations

import java.lang.annotation.Inherited

/**
 * This annotation is a marker to indicate that a class is permitted and intended to be serialized as part of Node messaging.
 *
 * Strictly speaking, it is critical to identifying that a class is intended to be deserialized by the node, to avoid
 * a security compromise later when a vulnerability is discovered in the deserialisation of a class that just happens to
 * be on the classpath, perhaps from a 3rd party library, as has been witnessed elsewhere.
 *
 * It also makes it possible for a code reviewer to clearly identify the classes that can be passed on the wire.
 *
 * Do NOT include [AnnotationTarget.EXPRESSION] as one of the @Target parameters, as this would allow any Lambda to
 * be serialised. This would be a security hole.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class CordaSerializable
