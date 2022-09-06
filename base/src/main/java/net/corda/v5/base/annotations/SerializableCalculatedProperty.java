package net.corda.v5.base.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to annotate methods which expose calculated values that we want to be serialized for use by the class carpenter.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface SerializableCalculatedProperty {
}
