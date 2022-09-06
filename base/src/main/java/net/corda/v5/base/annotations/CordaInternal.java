package net.corda.v5.base.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * These methods and annotations are not part of Corda's API compatibility guarantee and applications should not use them.
 *
 * These are only meant to be used by Corda internally, and are not intended to be part of the public API.
 */
@Target({ ANNOTATION_TYPE, METHOD, FIELD, TYPE })
@Retention(CLASS)
@Documented
public @interface CordaInternal {
}
