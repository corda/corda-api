@file:JvmName("KotlinUtils")
package net.corda.v5.base.util

import net.corda.v5.base.types.LayeredPropertyMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

//
// READ ME FIRST:
// This is a collection of public utilities useful only for Kotlin code. Think carefully before adding anything here and
// make sure it's tested and documented. If you're looking to add a public utility that is also relevant to Java then
// don't put it here but in a separate file called Utils.kt
//

/** Like the + operator but throws [ArithmeticException] in case of integer overflow. */
infix fun Int.exactAdd(b: Int): Int = Math.addExact(this, b)

/** Like the + operator but throws [ArithmeticException] in case of integer overflow. */
infix fun Long.exactAdd(b: Long): Long = Math.addExact(this, b)

/**
 * Usually you won't need this method:
 * * If you're in a companion object, use [contextLogger]
 * * If you're in an object singleton, use [LoggerFactory.getLogger] directly on javaClass
 *
 * Otherwise, this gets the [Logger] for a class using the syntax
 *
 * `private val log = loggerFor<MyClass>()`
 */
inline fun <reified T : Any> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)

/** Returns the logger used for detailed logging. */
fun detailedLogger(): Logger = LoggerFactory.getLogger("DetailedInfo")

/** When called from a companion object, returns the logger for the enclosing class. */
fun Any.contextLogger(): Logger = LoggerFactory.getLogger(javaClass.enclosingClass)

/** Log a TRACE level message produced by evaluating the given lamdba, but only if TRACE logging is enabled. */
inline fun Logger.trace(msg: () -> String) {
    if (isTraceEnabled) trace(msg())
}

/** Log a DEBUG level message produced by evaluating the given lamdba, but only if DEBUG logging is enabled. */
inline fun Logger.debug(msg: () -> String) {
    if (isDebugEnabled) debug(msg())
}

/**
 * Extension method for easier construction of [Duration]s in terms of integer days: `val twoDays = 2.days`.
 * @see Duration.ofDays
 */
val Int.days: Duration get() = Duration.ofDays(toLong())

/**
 * Extension method for easier construction of [Duration]s in terms of integer hours: `val twoHours = 2.hours`.
 * @see Duration.ofHours
 */
val Int.hours: Duration get() = Duration.ofHours(toLong())

/**
 * Extension method for easier construction of [Duration]s in terms of integer minutes: `val twoMinutes = 2.minutes`.
 * @see Duration.ofMinutes
 */
val Int.minutes: Duration get() = Duration.ofMinutes(toLong())

/**
 * Extension method for easier construction of [Duration]s in terms of integer seconds: `val twoSeconds = 2.seconds`.
 * @see Duration.ofSeconds
 */
val Int.seconds: Duration get() = Duration.ofSeconds(toLong())

/**
 * Extension method for easier construction of [Duration]s in terms of integer milliseconds: `val twoMillis = 2.millis`.
 * @see Duration.ofMillis
 */
val Int.millis: Duration get() = Duration.ofMillis(toLong())

/**
 * A simple wrapper that enables the use of Kotlin's `val x by transient { ... }` syntax. Such a property
 * will not be serialized, and if it's missing (or the first time it's accessed), the initializer will be
 * used to set it up.
 */
fun <T> transient(initializer: Supplier<T>): PropertyDelegate<T> = TransientProperty(initializer)

/**
 * Simple interface encapsulating the implicit Kotlin contract for immutable property delegates.
 */
interface PropertyDelegate<out T> {
    /**
     * Invoked as part of Kotlin delegated properties construct.
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

/**
 * Simple interface encapsulating the implicit Kotlin contract for mutable property delegates.
 */
interface VariablePropertyDelegate<T> : PropertyDelegate<T> {
    /**
     * Invoked as part of Kotlin delegated properties construct.
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

@CordaSerializable
private class TransientProperty<out T>(private val initializer: Supplier<T>) : PropertyDelegate<T> {
    @Transient
    private var initialized = false

    @Transient
    private var value: T? = null

    @Synchronized
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (!initialized) {
            value = initializer.get()
            initialized = true
        }
        return uncheckedCast(value)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, U : T> uncheckedCast(obj: T) = obj as U

/**
 * Function for reading and parsing the String values stored in the values to actual objects.
 *
 * @param key The key we are looking for in the store.
 */
inline fun <reified T> LayeredPropertyMap.parse(key: String): T {
    return parse(key, T::class.java)
}

/**
 * Function for reading and parsing the String values stored in the values to actual objects or return null.
 *
 * @param key The key we are looking for in the store.
 */
inline fun <reified T> LayeredPropertyMap.parseOrNull(key: String): T? {
    return parseOrNull(key, T::class.java)
}

/**
 * Function for reading and parsing the String values stored in the values to an actual list of objects.
 *
 * @param itemKeyPrefix The key prefix we are looking for in the store.
 */
inline fun <reified T> LayeredPropertyMap.parseList(itemKeyPrefix: String): List<T> {
    return parseList(itemKeyPrefix, T::class.java)
}

/**
 * Function for reading and parsing the String values stored in the values to an actual set of objects.
 *
 * @param itemKeyPrefix The key prefix we are looking for in the store.
 */
inline fun <reified T> LayeredPropertyMap.parseSet(itemKeyPrefix: String): Set<T> {
    return parseSet(itemKeyPrefix, T::class.java)
}
