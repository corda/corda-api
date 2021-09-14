package net.corda.v5.serialization

/**
 * This interface should be implemented by classes that want to substitute a singleton token representation of
 * themselves upon serialization.  This may be because of internal state that does not serialize well.
 *
 * This interface should only be used on singleton classes, meaning that only one should exist during runtime.
 */
interface SingletonSerializeAsToken {

    /**
     * Gets the name of the singleton's token.
     *
     * Provide an implementation of this method to manually specify the name of this singleton's token. The token's
     * name will be set to the singleton's class name if the method is not overridden.
     *
     * @return The [String] name of the singleton's token.
     */
    val tokenName: String get() = javaClass.name
}
