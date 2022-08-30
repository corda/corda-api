package net.corda.v5.base.annotations

/** The annotated object would have a more restricted visibility were it not needed in tests.
 *
 * This annotation has technically no effect on the code - it is purely a marker that the visibility of
 * the annotated entity is chosen for testing purposes.
 * */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPEALIAS
)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class VisibleForTesting