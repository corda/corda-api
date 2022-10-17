package net.corda.v5.base.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is a marker to indicate that a class is permitted and intended to be serialized as part of Corda messaging.
 *
 * Use this annotation on any class that needs to be serialized to be sent to a peer or to be stored in a database.
 * Corda will only serialize and deserialize classes that are explicitly annotated or have custom serializers provided
 * in order to avoid issues (and potential attacks) where data could be deserialized to a class that happens to be on
 * the class path.
 *
 * It also makes it possible for a code reviewer to clearly identify the classes that can be passed on the wire.
 */
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface CordaSerializable {
}
